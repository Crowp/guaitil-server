package com.guaitilsoft.services.notification;

import com.guaitilsoft.models.Notification;

import java.util.List;

public interface NotificationRepServ extends NotificationRepositoryService {

    void createAdminNotification(String description);

    List<Notification> getAllNotificationActive();

    List<Notification> getAllActiveByMemberId(Long id);
}
