package com.assignment.orderservice.Service.Implementation;

import com.assignment.orderservice.Dto.OrderItemDto;
import com.assignment.orderservice.Dto.OrderRequestDto;
import com.assignment.orderservice.Dto.OrderResponseDto;
import com.assignment.orderservice.Model.*;
import com.assignment.orderservice.Model.Exceptions.ImproperItemDataException;
import com.assignment.orderservice.Repository.*;
import com.assignment.orderservice.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OfferRepository offerRepository;

    @Override
    public List<OrderResponseDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        for(Order order: orders){
            orderResponseDtoList.add(convertEntityToResponseDto(order));
        }
        return orderResponseDtoList;
    }

    private OrderResponseDto convertEntityToResponseDto(Order order) {
        List<OrderItem> orderItems = order.getItems();
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for(OrderItem orderItem: orderItems){
            OrderItemDto orderItemDto = OrderItemDto.builder()
                                                    .itemId(orderItem.getId())
                                                    .quantity(orderItem.getQuantity())
                                                    .build();
            orderItemDtos.add(orderItemDto);
        }
        return OrderResponseDto.builder()
                                .items(orderItemDtos)
                                .totalPrice(order.getTotalPrice())
                                .createdAt(order.getCreatedAt())
                                .customerId(order.getCustomer().getId())
                                .id(order.getId())
                                .build();
    }

    @Override
    public OrderResponseDto getOrderById(int id) {
        Optional<Order> orderObj = orderRepository.findById(id);
        if (orderObj.isPresent()) {
            return convertEntityToResponseDto(orderObj.get());
        } else {
            throw new EntityNotFoundException("Order not found with ID: " + id);
        }
    }

    @Override
    public Order createOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        Optional<Customer> customerObj = customerRepository.findById(orderRequestDto.getCustomer_id());
        if (customerObj.isPresent()) {
            Customer customer = customerObj.get();
            order.setCustomer(customer);
        } else {
            throw new EntityNotFoundException("Customer not found with ID: " + orderRequestDto.getCustomer_id());
        }

        // Set Applicable offer list to the order
        List<Offer> offersApplicable = getOffersApplicable(customerObj.get());
        order.setOffers(offersApplicable);

        // Set total amount value after discount to the order
        List<OrderItem> orderItems = createOrderItems(orderRequestDto.getItems());
        double amount = calculateTotalCharge(orderItems, offersApplicable);
        order.setTotalPrice(amount);

        //save the order
        orderRepository.save(order);

        //Set order to orderItems
        for(OrderItem orderItem: orderItems){
            orderItem.setOrder(order);
        }

        //Persist all orderItems
        orderItemRepository.saveAll(orderItems);

        //Updating offers_orders table
        for(Offer offer: offersApplicable){
            offer.getOrders().add(order);
            offerRepository.save(offer);
        }

        return order;
    }

    private List<Offer> getOffersApplicable(Customer customer) {
        List<Offer> offerList = offerRepository.findAll();
        List<Offer> applicableOffers = new ArrayList<>();
        for(Offer offer: offerList){
            if(offer.isEnabled() && validateOffer(offer, customer))
                applicableOffers.add(offer);
        }
        return applicableOffers;
    }

    private boolean validateOffer(Offer offer, Customer customer) {

        boolean validateDate = (offer.getStartDate().isBefore(LocalDate.now())
                                    && offer.getEndDate().isAfter(LocalDate.now()))
                            || (offer.getEndDate().isEqual(LocalDate.now())
                                    && (offer.getEndDate().isEqual(LocalDate.now())));

        List<Order> ordersByCustomerId = orderRepository.findByCustomer(customer);
        boolean validateNumberOfItems = ordersByCustomerId.size() > offer.getMinimumOrders()
                                        && ordersByCustomerId.size() <= offer.getMaximumOrders();

        return validateDate && validateNumberOfItems;
    }

    private double calculateTotalCharge(List<OrderItem> orderItems, List<Offer> offersApplicable) {
        double totalPrice = 0;
        for(OrderItem orderItem: orderItems){
            totalPrice += orderItem.getPrice();
        }
        for(Offer offer: offersApplicable){
            totalPrice *= (1 - (offer.getDiscountPercentage()/100));
        }
        return totalPrice;
    }

    private List<OrderItem> createOrderItems(List<OrderItemDto> orderItems) {

        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderItemDto orderItemDto : orderItems) {
            if (itemRepository.existsById(orderItemDto.getItemId()) && orderItemDto.getQuantity() > 0) {
                Item item = itemRepository.findById(orderItemDto.getItemId()).get();
                double price = item.getRate() * orderItemDto.getQuantity();
                OrderItem orderItem = OrderItem.builder()
                                                .quantity(orderItemDto.getQuantity())
                                                .price(price)
                                                .item(item)
                                                .build();
                orderItemList.add(orderItem);
            } else {
                throw new ImproperItemDataException("Item Id or Quantity entered is wrong");
            }
        }
        return orderItemList;
    }
}
