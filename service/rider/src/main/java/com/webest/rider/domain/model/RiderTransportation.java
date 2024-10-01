package com.webest.rider.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RiderTransportation {
    WALK("Walk"),
    BICYCLE("Bicycle"),
    MOTORCYCLE("Motorcycle");;

    private final String type;

}
