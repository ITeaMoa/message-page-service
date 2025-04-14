package com.iteamoa.message.service;

import com.iteamoa.message.dto.MessageDto;
import com.iteamoa.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public void saveMessage(MessageDto messageDto) {
        Objects.requireNonNull(messageDto.getPk(), "Pk cannot be null");
        Objects.requireNonNull(messageDto.getSk(), "Sk cannot be null");

        messageRepository.saveMessage(messageDto);
    }

    public void getConversationPartner(String pk, String receiverId) {

    }
}
