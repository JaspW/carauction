package com.carauction.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuctionStatus {
    ACTIVE("Активен"),
    END("Закончен"),
    WAITING("Ожидание"),
    ;
    private final String name;

}
