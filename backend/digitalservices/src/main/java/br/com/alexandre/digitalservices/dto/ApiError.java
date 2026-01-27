package br.com.alexandre.digitalservices.dto;

import java.time.Instant;

public record ApiError(
    int status,
    String error,
    String message,
    Object details,
    Instant timestamp,
    String path
) {}
