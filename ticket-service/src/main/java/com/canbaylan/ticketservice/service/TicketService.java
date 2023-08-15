package com.canbaylan.ticketservice.service;

import com.canbaylan.ticketservice.dto.TicketDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService {
    TicketDto save(TicketDto ticketDto);
    TicketDto update(String id,TicketDto ticketDto);
    Page<TicketDto> getPagination(Pageable pageable);
    TicketDto getById(String id);
}
