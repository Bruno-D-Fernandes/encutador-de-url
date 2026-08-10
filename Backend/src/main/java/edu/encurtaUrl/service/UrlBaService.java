package edu.encurtaUrl.service;

import edu.encurtaUrl.dto.response.UrlResponseDto;
import edu.encurtaUrl.model.UrlBa;
import edu.encurtaUrl.model.UserBa;
import edu.encurtaUrl.repository.UrlBaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UrlBaService {

    private UrlBaRepository urlBaRepository;
    public static final SecureRandom secureRandom = new SecureRandom();

    @Autowired
    public UrlBaService(UrlBaRepository urlBaRepository) {
        this.urlBaRepository = urlBaRepository;
    }


    public URI redirectMeUri(String urlEncurted){
        UrlBa urlEntity = urlBaRepository.findByShortUri(urlEncurted)
                .orElseThrow(() -> new RuntimeException("Error, original uri not find"));

        // verify if it is valid
        Instant expiresAt = urlEntity.getExpiresAt();
        if(expiresAt.isBefore(Instant.now())){
            throw new RuntimeException("Short url expired");
        }

        // unshift http protocol at the start
        String fullUri = urlEntity.getOriginalUri();
        if(!fullUri.startsWith("https://") || !fullUri.startsWith("http://")){
            fullUri = "https://" + fullUri;
        }

        //URI .create faz uma parse a partir de uma url completa
        URI originalUri = URI.create(fullUri);

        return originalUri;
    }

    @Transactional
    public Object createShortUri(String originalUri, UserBa userBa){
        String shortUri = generateRandomUri();

        // For now, it is only valid for 2 hours
        UrlBa urlBa = new UrlBa(null, originalUri, shortUri, userBa, Instant.now(), Instant.now().plus(Duration.ofHours(2)));

        return "";
    }

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

    public List<UrlResponseDto> getAllUrlByUser(UserBa userBa){
        List<UrlBa> urlsByOwner = urlBaRepository.findByOwner(userBa);

        List<UrlResponseDto> response = urlsByOwner.stream()
                .map(UrlResponseDto::new)
                .toList();

        return response;
    }

}
