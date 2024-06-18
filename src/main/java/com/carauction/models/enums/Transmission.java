package com.carauction.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Transmission {
    AUTO("Автоматическая"),
    MECHANIC("Механика"),
    ;
    private final String name;

}
