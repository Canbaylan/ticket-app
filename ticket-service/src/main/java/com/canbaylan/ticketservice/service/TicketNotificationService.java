package com.canbaylan.ticketservice.service;

import com.canbaylan.ticketservice.model.Ticket;

public interface TicketNotificationService {
    void sendToQueue(Ticket ticket);


}
