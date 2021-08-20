package com.simplegram.services;

import com.simplegram.repository.MessageStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageStatusService {
    private final MessageStatusRepository messageStatusRepository;

}
