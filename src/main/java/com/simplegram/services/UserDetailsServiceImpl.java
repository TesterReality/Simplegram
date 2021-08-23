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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException
                (messageSource.getMessage("error.UserLoginNotFound", null, Locale.getDefault()) + ": " + login));

        return UserDetailsImpl.build(user);
    }

    public boolean existsByLogin(String login)
    {
        return userRepository.existsByLogin(login);
    }

    public Optional<User> findByLogin(String login)
    {
        return userRepository.findByLogin(login);
    }

    public User findById(String userId)
    {
        return userRepository.findById(userId);
    }

}
