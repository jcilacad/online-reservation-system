package com.system.reservation.online.service;

import com.system.reservation.online.dto.ItemDto;
import com.system.reservation.online.entity.Item;
import com.system.reservation.online.entity.User;
import com.system.reservation.online.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    private ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Long saveItem(ItemDto itemDto, String fileName) {

        Item item = new Item(
                fileName,
                itemDto.getName(),
                itemDto.getSize(),
                itemDto.getPrice(),
                itemDto.getQuantity(),
                itemDto.getDescription()
        );


        itemRepository.save(item);

        return item.getId();
    }

    @Override
    public void updateItem(Long itemId, ItemDto itemDto, String fileName) {

        // Find item by id, if not exists throw an exception
        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(()-> new RuntimeException("Did not found id " + itemId));


        // Set the new value/s of item from itemDto
        item.setPhotos(fileName);
        item.setName(itemDto.getName());
        item.setSize(itemDto.getSize());
        item.setPrice(itemDto.getPrice());
        item.setQuantity(itemDto.getQuantity());
        item.setDescription(itemDto.getDescription());

        // update it in DB
        itemRepository.save(item);
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Page<Item> findAllPaginated(Integer currentPage, Integer pageSize) {
        return itemRepository.findAll(PageRequest.of(currentPage, pageSize));
    }

    @Override
    public Page<Item> findItemByNameContaining(String name, Integer currentPage, Integer pageSize) {
        List<Item> userList = itemRepository.findByNameContainingIgnoreCase(name);

        Page<Item> page = new PageImpl<>(userList, PageRequest.of(currentPage, pageSize), userList.size());

        return page;
    }

    @Override
    public Item findById(Long id) {

        return itemRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(
                        "Id - " + id + " does not exists"
                ));
    }


    @Override
    public void deleteById(Long id) {

        itemRepository.deleteById(id);
    }
}
