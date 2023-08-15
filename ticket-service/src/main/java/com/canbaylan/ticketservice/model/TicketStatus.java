package com.canbaylan.ticketservice.model;

import lombok.Getter;

@Getter
public enum TicketStatus {
    OPEN("Acik"),
    IN_PROGRESS("Calisiyor"),
    RESOLVED("Cozuldu"),
    CLOSED("Kapali");

    private String label;
    TicketStatus(String label){
        this.label = label;
    }
}
