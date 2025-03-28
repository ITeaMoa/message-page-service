package com.iteamoa.message.controller;

import com.iteamoa.message.dto.MessageDto;
import com.iteamoa.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;

    @PostMapping()
    public void sendMessage(@RequestBody MessageDto messageDto) {
        messageService.saveMessage(messageDto);
    }
}
