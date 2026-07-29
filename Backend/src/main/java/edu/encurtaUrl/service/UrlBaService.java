package edu.encurtaUrl.service;

import edu.encurtaUrl.dto.response.UrlResponseDto;
import edu.encurtaUrl.model.UrlBa;
import edu.encurtaUrl.model.UserBa;
import edu.encurtaUrl.repository.UrlBaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlBaService {

    private UrlBaRepository urlBaRepository;

    @Autowired
    public UrlBaService(UrlBaRepository urlBaRepository) {
        this.urlBaRepository = urlBaRepository;
    }

    public List<UrlResponseDto> getUrlByUser(UserBa userBa){
        List<UrlBa> urlsByOwner = urlBaRepository.findByOwner(userBa);

        List<UrlResponseDto> response = urlsByOwner.stream()
                .map(UrlResponseDto::new)
                .toList();

        return response;
    }
}
