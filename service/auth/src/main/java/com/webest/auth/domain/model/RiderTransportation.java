package com.webest.auth.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RiderTransportation {
    WALK("Walk"),
    BICYCLE("Bicycle"),
    MOTORCYCLE("Motorcycle");

    private final String type;

}
