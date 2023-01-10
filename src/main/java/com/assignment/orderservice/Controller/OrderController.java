package com.assignment.orderservice.Controller;

import com.assignment.orderservice.Dto.OrderRequestDto;
import com.assignment.orderservice.Dto.OrderResponseDto;
import com.assignment.orderservice.Model.Exceptions.OrderEntityException;
import com.assignment.orderservice.Model.Order;
import com.assignment.orderservice.Service.Implementation.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @PostMapping()
    public ResponseEntity<String> createOrder(@RequestBody OrderRequestDto orderRequestDto) throws OrderEntityException {
        try{
            Order order = orderService.createOrder(orderRequestDto);
            return new ResponseEntity<>("Order no. " + order.getId() + " is created", HttpStatus.CREATED);
        } catch (Exception e){
            throw new OrderEntityException(e);
        }

    }

    @GetMapping()
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() throws OrderEntityException {
        try{
            List<OrderResponseDto> orderList = orderService.getAllOrders();
            return new ResponseEntity<>(orderList, HttpStatus.FOUND);
        }catch (Exception e){
            throw new OrderEntityException(e);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(int id) throws OrderEntityException {
        try{
            OrderResponseDto order = orderService.getOrderById(id);
            return new ResponseEntity<>(order, HttpStatus.FOUND);
        }catch (Exception e){
            throw new OrderEntityException(e);
        }

    }

}
