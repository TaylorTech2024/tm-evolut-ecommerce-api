package com.tmevolut.ecommerce.api.dto;
import jakarta.validation.constraints.*;
public record ClienteRequest(@NotBlank String nome, @Email @NotBlank String email) {}
