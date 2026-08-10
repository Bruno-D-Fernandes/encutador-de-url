package edu.encurtaUrl.infra.security;

import edu.encurtaUrl.repository.UserBaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserBaRepository userBaRepository;

    public UserDetailsServiceImpl(UserBaRepository userBaRepository) {
        this.userBaRepository = userBaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userBaRepository.findByEmail(email);
    }


}
