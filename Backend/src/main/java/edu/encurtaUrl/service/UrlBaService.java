package edu.encurtaUrl.service;

import edu.encurtaUrl.dto.response.UrlResponseDto;
import edu.encurtaUrl.exception.urlRoutine.ExpiredUrl;
import edu.encurtaUrl.exception.urlRoutine.InvalidUrlException;
import edu.encurtaUrl.model.UrlBa;
import edu.encurtaUrl.model.UserBa;
import edu.encurtaUrl.repository.UrlBaRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UrlBaService {

    private final UrlBaRepository urlBaRepository;
    public static final SecureRandom secureRandom = new SecureRandom();
    private final EntityManager entityManager;
    private final Path root = Paths.get("uploads/images");

    @Autowired
    public UrlBaService(UrlBaRepository urlBaRepository, EntityManager entityManager) {
        this.urlBaRepository = urlBaRepository;
        this.entityManager = entityManager;
    }

    public URI redirectMeUri(String urlEncurted){
        UrlBa urlEntity = urlBaRepository.findByShortUri(urlEncurted)
                .orElseThrow(() -> new RuntimeException("Error, original uri not found"));
                // todo fazer esse unit test

        // verify if it is valid
        Instant expiresAt = urlEntity.getExpiresAt();
        if(expiresAt.isBefore(Instant.now())){
            throw new ExpiredUrl();
        }

        String fullUri = urlEntity.getOriginalUri();
        String ValidatedfullUri = validateUrl(fullUri);

        //URI .create faz uma parse a partir de uma url completa
        URI originalUri = URI.create(ValidatedfullUri);

        return originalUri;
    }

    @Transactional
    public String createShortUri(String originalUri, UserBa userBa){
        // make userBa change to managed lifecycle
        UserBa user = entityManager.merge(userBa);

        String validatedFullUrl = validateUrl(originalUri);

        String shortUri = generateRandomUri();

        // For now, it is only valid for 2 hours
        UrlBa urlBa = new UrlBa(null, validatedFullUrl, shortUri, user, Instant.now(), Instant.now().plus(Duration.ofHours(2)));

        urlBaRepository.save(urlBa);

        // todo return the complete uri
        return shortUri;
    }

    @Transactional
    public void createShortFile(MultipartFile file, UserBa userBa){

        String shortUrl = generateRandomUri();
        String originalFileName = file.getOriginalFilename();
        Instant createdAt = Instant.now();
        Instant expiresAt = createdAt.plus(Duration.ofHours(2));

        if(file.isEmpty()) throw new RuntimeException("temp, UrlService");

        String contentType = file.getContentType();
        if(contentType.isEmpty() || !contentType.startsWith("image/")) throw new RuntimeException("temp, UrlService");

        // saves the image
        try {
            Files.createDirectory(root);
            String fileName = UUID.randomUUID() + "_" + originalFileName;
            Files.copy(file.getInputStream(), root.resolve(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UrlBa urlBa = new UrlBa(null, originalFileName, shortUrl, userBa, createdAt, expiresAt);
        urlBaRepository.save(urlBa);

    }

    public List<UrlResponseDto> getAllUrlByUser(UserBa userBa){
        List<UrlBa> urlsByOwner = urlBaRepository.findByOwner(userBa);

        List<UrlResponseDto> response = urlsByOwner.stream()
                .map(UrlResponseDto::new)
                .toList();

        return response;
    }

    private String validateUrl(String url){

        if(url.startsWith("/")) throw new InvalidUrlException();

        // unshift http protocol at the start
        if(!url.startsWith("https://") && !url.startsWith("http://")){
            url = "https://" + url;
        }

        // Validation
        try {
            URL urlValidation = URI.create(url).toURL();
            System.out.println(urlValidation);
        } catch (Exception e) {
            throw new InvalidUrlException();
        }

        return url;
    }

    // private
    private String generateRandomUri(){
        String charactersString = "abcdefghijklmnopqrstuvwXyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder randomUrl = new StringBuilder();
        final int LENGTH = 7;

        do{
            char randomChar = charactersString.charAt(secureRandom.nextInt(charactersString.length()));
            randomUrl.append(randomChar);
        }while(randomUrl.length() != LENGTH);

        // race condition?
        if(urlBaRepository.existsByShortUri(randomUrl.toString())){
            return generateRandomUri();
        }

        return randomUrl.toString();
    }



}
