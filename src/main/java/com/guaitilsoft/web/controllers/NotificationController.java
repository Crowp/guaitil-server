package com.guaitilsoft.web.controllers;

import com.guaitilsoft.models.Notification;
import com.guaitilsoft.services.NotificationService;
import com.guaitilsoft.web.models.notification.LoadNotification;
import com.guaitilsoft.web.models.notification.NotificationView;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/notification")
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
    public ResponseEntity<List<NotificationView>> get(){
        Type listType = new TypeToken<List<NotificationView>>(){}.getType();
        List<NotificationView> notificationViews = modelMapper.map(notificationService.list(), listType);
        return ResponseEntity.ok().body(notificationViews);
    }

    @GetMapping("{id}")
    public ResponseEntity<NotificationView> getById(@PathVariable Long id){
        NotificationView notificationView = modelMapper.map(notificationService.get(id), NotificationView.class);
        logger.info("Fetching Activity with id {}", id);
        return ResponseEntity.ok().body(notificationView);
    }

    @GetMapping("/active")
    public ResponseEntity<List<NotificationView>> getAllNotificationActive(){
        Type listType = new TypeToken<List<NotificationView>>(){}.getType();
        List<NotificationView> notificationViews = modelMapper.map(notificationService.getAllNotificationActive(), listType);

        return ResponseEntity.ok().body(notificationViews);
    }

    @GetMapping("/active/memberId/{id}")
    public ResponseEntity<List<LoadNotification>> getAllNotificationActiveByMemberId(@PathVariable Long id){
        Type listType = new TypeToken<List<LoadNotification>>(){}.getType();
        List<LoadNotification> notificationResponse = modelMapper.map(notificationService.getAllActiveByMemberId(id), listType);

        return ResponseEntity.ok().body(notificationResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<NotificationView> put(@PathVariable Long id) {
        Notification notification = notificationService.get(id);
        logger.info("Updating Activity with id {}", id);

        notification.setIsActive(false);
        notificationService.update(id, notification);
        NotificationView notificationResponse = modelMapper.map(notification, NotificationView.class);

        logger.info("Updated Activity with id {}", id);
        return ResponseEntity.ok().body(notificationResponse);
    }
}
