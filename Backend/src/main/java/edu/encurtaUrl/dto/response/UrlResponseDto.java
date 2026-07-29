package edu.encurtaUrl.dto.response;

import edu.encurtaUrl.model.UrlBa;

import java.time.LocalDateTime;

public record UrlResponseDto(String originalUrl, String shortUrl, LocalDateTime createdAt, LocalDateTime expiresAt) {

    public UrlResponseDto(UrlBa urlBa) {
        this(urlBa.getOriginalUri(), urlBa.getShortUri(), urlBa.getCreatedAt(), urlBa.getExpiresAt());
    }

}
