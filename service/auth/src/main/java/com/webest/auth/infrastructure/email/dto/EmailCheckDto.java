package com.webest.auth.infrastructure.email.dto;


public record EmailCheckDto(
        String email,
        String authNum
) {
}