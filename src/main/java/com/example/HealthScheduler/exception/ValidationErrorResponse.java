package com.example.HealthScheduler.exception;

import java.time.Instant;
import java.util.Map;

public record ValidationErrorResponse(
        Instant timestamp,
        Integer status,
        String error,

        // Chave: nome do campo que falhou na validação.
        // Valor: mensagem descritiva do erro associado ao campo.
        Map<String, String> errors
) {
}
