package com.simplegram.security.services;

import com.simplegram.models.User;
import com.simplegram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    //ищем не по username а по login
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("error.UserLoginNotFound",
                        null, Locale.ENGLISH)+": " + login));

        return UserDetailsImpl.build(user);
    }

}
