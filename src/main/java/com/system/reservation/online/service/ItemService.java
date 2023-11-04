package com.system.reservation.online.service;

import com.system.reservation.online.dto.ItemDto;

public interface ItemService {

    Long saveItem(ItemDto itemDto, String fileName);
}
