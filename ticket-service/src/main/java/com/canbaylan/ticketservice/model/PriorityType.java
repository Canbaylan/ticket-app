package com.canbaylan.ticketservice.model;

import lombok.Getter;

@Getter
public enum PriorityType {

    URGENT("Acil"),
    LOW("Dusuk"),
    HIGH("Yuksek");

    private String label;

    PriorityType(String label){
        this.label = label;
    }

}
