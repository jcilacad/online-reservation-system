package com.system.reservation.online.service;

import com.system.reservation.online.dto.ItemDto;
import com.system.reservation.online.entity.Item;
import com.system.reservation.online.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
