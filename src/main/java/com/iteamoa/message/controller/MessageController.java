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

    @GetMapping("/list")
    public ResponseEntity<?> getUserList(@RequestParam String pk) {
        return ResponseEntity.ok(messageService.getUserList(pk));
    }

    @GetMapping("/count")
    public ResponseEntity<?> getMessageCount(@RequestParam String pk) {
        return ResponseEntity.ok(messageService.getMessageCount(pk));
    }

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
    public ResponseEntity<?> getAllMessages(@RequestParam String userId, @RequestParam String recipientId) {
        return ResponseEntity.ok(messageService.getAllMessage(userId, recipientId));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Connected successfully");
    }
}
