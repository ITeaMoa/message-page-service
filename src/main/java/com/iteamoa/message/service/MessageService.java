package com.iteamoa.message.service;

import com.iteamoa.message.dto.MessageDto;
import com.iteamoa.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public void saveMessage(MessageDto messageDto) {
        messageRepository.saveMessage(messageDto);
    }

}
