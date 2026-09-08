package edu.encurtaUrl.controller;

import edu.encurtaUrl.model.UserBa;
import edu.encurtaUrl.service.UrlBaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/encUrl")
public class UrlController {

    private UrlBaService urlBaService;

    @Autowired
    public UrlController(UrlBaService urlBaService) {
        this.urlBaService = urlBaService;
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity redirectMe(
            @PathVariable String shortUrl
    ){
        URI uri = urlBaService.redirectMeUri(shortUrl);

        // Redirect with status 3xx
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(uri)
                .build();
    }

    @PostMapping
    public ResponseEntity createShortUri(
            @RequestBody Map<String, String> originalUri,
            @AuthenticationPrincipal UserBa user
            ){

        String shortUri = urlBaService.createShortUri(originalUri.get("originalUri"), user);

        return ResponseEntity.ok(Map.of("ShortenedURL", shortUri));
    }


//    @GetMapping("file/{shortUrl}")
//    public ResponseEntity createShortUriToFile(
//
//    ){
//
//    }


}
