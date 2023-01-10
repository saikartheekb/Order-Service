package com.assignment.orderservice.Service.Implementation;

import com.assignment.orderservice.Dto.ItemDto;
import com.assignment.orderservice.Model.Item;
import com.assignment.orderservice.Repository.ItemRepository;
import com.assignment.orderservice.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public Item createItem(ItemDto itemDto) {
        Item item = Item.builder()
                .name(itemDto.getName())
                .rate(itemDto.getRate())
                .build();
        item = itemRepository.save(item);
        return item;
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(int id) {
        Optional<Item> itemObj = itemRepository.findById(id);
        if (itemObj.isPresent()) {
            return itemObj.get();
        } else {
            throw new EntityNotFoundException("Item not found with ID: " + id);
        }
    }

    @Override
    public Item updateItem(int id, ItemDto updatedItemDto) {
        Optional<Item> itemObj = itemRepository.findById(id);
        if (itemObj.isPresent()) {
            Item item = itemObj.get();
            item.setName(updatedItemDto.getName());
            item.setRate(updatedItemDto.getRate());
            itemRepository.save(item);
            return item;
        } else {
            throw new EntityNotFoundException("Item not found with ID: " + id);
        }
    }

    @Override
    public Item deleteItem(int id) {
        Optional<Item> itemObj = itemRepository.findById(id);
        if (itemObj.isPresent()) {
            Item item = itemObj.get();
            itemRepository.delete(item);
            return item;
        } else {
            throw new EntityNotFoundException("Item not found with ID: " + id);
        }
    }
}
