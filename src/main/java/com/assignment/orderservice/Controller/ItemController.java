package com.assignment.orderservice.Controller;

import com.assignment.orderservice.Dto.ItemDto;
import com.assignment.orderservice.Model.Exceptions.ItemEntityException;
import com.assignment.orderservice.Model.Item;
import com.assignment.orderservice.Service.Implementation.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    ItemServiceImpl itemService;

    @PostMapping()
    public ResponseEntity<String> createItem(@RequestBody ItemDto itemDto) throws ItemEntityException {
        try{
            Item item = itemService.createItem(itemDto);
            return new ResponseEntity<>(item.getName() + " is created", HttpStatus.CREATED);
        } catch (Exception e){
            throw new ItemEntityException(e);
        }
    }

    @GetMapping()
    public ResponseEntity<List<Item>> getAllItems() throws ItemEntityException {
        try{
            List<Item> itemList = itemService.getAllItems();
            return new ResponseEntity<>(itemList, HttpStatus.FOUND);
        } catch (Exception e){
            throw new ItemEntityException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable int id) throws ItemEntityException {
        try{
            Item item = itemService.getItemById(id);
            return new ResponseEntity<>(item, HttpStatus.FOUND);
        } catch (Exception e){
            throw new ItemEntityException(e);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable int id, @RequestBody ItemDto updatedItemDto) throws ItemEntityException {
        try{
            return new ResponseEntity<>(itemService.updateItem(id, updatedItemDto),HttpStatus.FOUND);
        } catch (Exception e){
            throw new ItemEntityException(e);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) throws ItemEntityException {
        try{
            itemService.deleteItem(id);
            return new ResponseEntity<>("Item with id " + id + "is deleted", HttpStatus.ACCEPTED);
        }catch (Exception e){
            throw new ItemEntityException(e);
        }

    }

}
