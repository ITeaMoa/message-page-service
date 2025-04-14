package com.iteamoa.message.controller;

import com.iteamoa.message.dto.MessageDto;
import com.iteamoa.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;

    @PostMapping()
    public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto) {
        try{
            messageService.saveMessage(messageDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteMessage(@RequestBody MessageDto messageDto) {
        messageService.deleteMessage(messageDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping()
    public ResponseEntity<?> getAllMessages(@RequestParam String pk) {
        return ResponseEntity.ok(messageService.getAllMessage(pk));
    }
}
