package com.system.reservation.online.service;

import com.system.reservation.online.dto.ItemDto;
import com.system.reservation.online.entity.Item;

public interface ItemService {

    Long saveItem(ItemDto itemDto, String fileName);

    List<Item> findAll();
}
