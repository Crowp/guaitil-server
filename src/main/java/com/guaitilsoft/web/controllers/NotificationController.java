package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Notification;
import com.guaitilsoft.services.NotificationService;
import com.guaitilsoft.web.models.notification.NotificationLazyResponse;
import com.guaitilsoft.web.models.notification.NotificationResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/notifications")
public class NotificationController {

    public static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    public final NotificationService notificationService;
    private final ModelMapper modelMapper;

    @Autowired
    public NotificationController(NotificationService notificationService, ModelMapper modelMapper) {
        this.notificationService = notificationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> get(){
        Type listType = new TypeToken<List<NotificationResponse>>(){}.getType();
        List<NotificationResponse> notificationResponse = modelMapper.map(notificationService.list(), listType);
        return ResponseEntity.ok().body(notificationResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<NotificationResponse> getById(@PathVariable Long id){
        NotificationResponse notificationResponse = modelMapper.map(notificationService.get(id), NotificationResponse.class);
        logger.info("Fetching Activity with id {}", id);
        return ResponseEntity.ok().body(notificationResponse);
    }

    @GetMapping("/active")
    public ResponseEntity<List<NotificationResponse>> getAllNotificationActive(){
        Type listType = new TypeToken<List<NotificationResponse>>(){}.getType();
        List<NotificationResponse> notificationResponse = modelMapper.map(notificationService.getAllNotificationActive(), listType);

        return ResponseEntity.ok().body(notificationResponse);
    }

    @GetMapping("/active/memberId/{id}")
    public ResponseEntity<List<NotificationLazyResponse>> getAllNotificationActiveByMemberId(@PathVariable Long id){
        Type listType = new TypeToken<List<NotificationLazyResponse>>(){}.getType();
        List<NotificationLazyResponse> notificationResponse = modelMapper.map(notificationService.getAllActiveByMemberId(id), listType);

        return ResponseEntity.ok().body(notificationResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<NotificationResponse> put(@PathVariable Long id) {
        Notification notification = notificationService.get(id);
        logger.info("Updating Activity with id {}", id);

        notification.setIsActive(false);
        notificationService.update(id, notification);
        NotificationResponse notificationResponse = modelMapper.map(notification, NotificationResponse.class);

        logger.info("Updated Activity with id {}", id);
        return ResponseEntity.ok().body(notificationResponse);
    }
}
