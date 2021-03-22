package com.guaitilsoft.web.controllers;

import com.guaitilsoft.services.notification.NotificationService;
import com.guaitilsoft.web.models.notification.NotificationLazyResponse;
import com.guaitilsoft.web.models.notification.NotificationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/notifications")
public class NotificationController {

    public static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    public final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> get(){
        List<NotificationResponse> notificationResponse = notificationService.list();
        return ResponseEntity.ok().body(notificationResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<NotificationResponse> getById(@PathVariable Long id){
        NotificationResponse notificationResponse = notificationService.get(id);
        logger.info("Fetching Activity with id {}", id);
        return ResponseEntity.ok().body(notificationResponse);
    }

    @GetMapping("/active")
    public ResponseEntity<List<NotificationResponse>> getAllNotificationActive(){
        List<NotificationResponse> notificationResponse = notificationService.getAllNotificationActive();
        return ResponseEntity.ok().body(notificationResponse);
    }

    @GetMapping("/active/memberId/{id}")
    public ResponseEntity<List<NotificationLazyResponse>> getAllNotificationActiveByMemberId(@PathVariable Long id){
        List<NotificationLazyResponse> notificationResponse = notificationService.getAllActiveByMemberId(id);
        return ResponseEntity.ok().body(notificationResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<NotificationResponse> put(@PathVariable Long id) {
        logger.info("Updating Activity with id {}", id);
        NotificationResponse notificationResponse = notificationService.update(id);
        logger.info("Updated Activity with id {}", id);
        return ResponseEntity.ok().body(notificationResponse);
    }
}
