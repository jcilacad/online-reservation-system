package com.system.reservation.online.service;

import com.system.reservation.online.dto.ItemDto;
import com.system.reservation.online.entity.Item;
import com.system.reservation.online.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemService {

    Long saveItem(ItemDto itemDto, String fileName);

    List<Item> findAll();

    Page<Item> findAllPaginated(Integer currentPage, Integer pageSize);

    Page<Item> findItemByNameContaining(String name, Integer currentPage, Integer pageSize);
}
