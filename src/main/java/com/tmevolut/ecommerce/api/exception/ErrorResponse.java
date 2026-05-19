package com.tmevolut.ecommerce.api.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(

        LocalDateTime timestamp,
        int status,
        String erro,
        List<String> mensagens

) {}