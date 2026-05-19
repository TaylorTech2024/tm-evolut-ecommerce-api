package com.tmevolut.ecommerce.api.dto;

public record ClienteResponse(
        Long id,
        String nome,
        String email
) {}