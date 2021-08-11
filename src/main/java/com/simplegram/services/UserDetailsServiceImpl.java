package com.simplegram.services;

import com.simplegram.entity.User;
import com.simplegram.repository.UserRepository;
import com.simplegram.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    @Transactional
    //using login
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException
                (messageSource.getMessage("error.UserLoginNotFound", null, Locale.getDefault()) + ": " + login));

        return UserDetailsImpl.build(user);
    }
}
