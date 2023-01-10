package com.assignment.orderservice.Service;

import com.assignment.orderservice.Dto.ItemDto;
import com.assignment.orderservice.Model.Item;

import java.util.List;

public interface ItemService {
    Item createItem(ItemDto itemDto);

    List<Item> getAllItems();

    Item getItemById(int id);

    Item updateItem(int id, ItemDto updatedItemDto);

    Item deleteItem(int id);
}
