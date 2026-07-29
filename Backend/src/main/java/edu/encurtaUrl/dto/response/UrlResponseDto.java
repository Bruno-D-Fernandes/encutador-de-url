package edu.encurtaUrl.dto.response;

import edu.encurtaUrl.model.UrlBa;

import java.time.Instant;

public record UrlResponseDto(String originalUrl, String shortUrl, Instant createdAt, Instant expiresAt) {

    public UrlResponseDto(UrlBa urlBa) {
        this(urlBa.getOriginalUri(), urlBa.getShortUri(), urlBa.getCreatedAt(), urlBa.getExpiresAt());
    }

}
