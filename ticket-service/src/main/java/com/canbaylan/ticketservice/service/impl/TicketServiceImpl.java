package com.canbaylan.ticketservice.service.impl;

import com.canbaylan.client.AccountServiceClient;
import com.canbaylan.contract.AccountDto;
import com.canbaylan.ticketservice.dto.TicketDto;
import com.canbaylan.ticketservice.model.PriorityType;
import com.canbaylan.ticketservice.model.Ticket;
import com.canbaylan.ticketservice.model.TicketStatus;
import com.canbaylan.ticketservice.model.es.TicketModel;
import com.canbaylan.ticketservice.repository.TicketRepository;
import com.canbaylan.ticketservice.repository.es.TicketElasticRepository;
import com.canbaylan.ticketservice.service.TicketNotificationService;
import com.canbaylan.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private TicketElasticRepository ticketElasticRepository;
    private TicketRepository ticketRepository;
    private TicketNotificationService ticketNotificationService;
    private AccountServiceClient accountServiceClient;

    @Override
    @Transactional
    public TicketDto save(TicketDto ticketDto) {
        // Ticket Entity
        Ticket ticket = new Ticket();
        //  TODO Account api'den dogrula
        //  ticket.setAssignee();
        ResponseEntity<AccountDto> accountDtoResponseEntity = accountServiceClient.get(ticketDto.getAssignee());

        if(ticketDto.getDescription()==null)
            throw new IllegalArgumentException("Description is null");
        ticket.setDescription(ticketDto.getDescription());
        ticket.setNotes(ticketDto.getNotes());
        ticket.setTicketDate(ticketDto.getTicketDate());
        ticket.setTicketStatus(TicketStatus.valueOf(ticketDto.getTicketStatus()));
        ticket.setPriorityType(PriorityType.valueOf(ticketDto.getPriorityType()));
        ticket.setAssignee(accountDtoResponseEntity.getBody().getId());

        //  mysql kaydet
        ticket = ticketRepository.save(ticket);

        // TicketModel nesnesi yarat
        TicketModel ticketModel = TicketModel.builder()
                .description(ticket.getDescription())
                .notes(ticket.getNotes())
                .id(ticket.getId())
                .assignee(accountDtoResponseEntity.getBody().getNameSurname())
                .priorityType(ticket.getPriorityType().getLabel())
                .ticketStatus(ticket.getTicketStatus().getLabel())
                .ticketDate(ticket.getTicketDate()).build();
        //  Elastic kaydet
        ticketElasticRepository.save(ticketModel);

        //  Olusan nesneyi dondur
        ticketDto.setId(ticket.getId());

        //  Kuyruga notif yaz
        ticketNotificationService.sendToQueue(ticket);
        return ticketDto;
    }

    @Override
    public TicketDto update(String id, TicketDto ticketDto) {
        return null;
    }

    @Override
    public Page<TicketDto> getPagination(Pageable pageable) {
        return null;
    }

    @Override
    public TicketDto getById(String id) {
        return null;
    }
}
