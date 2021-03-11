package com.guaitilsoft.services.notification;

import com.guaitilsoft.models.Notification;
import com.guaitilsoft.web.models.notification.NotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> list();

    NotificationResponse get(Long id);

    NotificationResponse update(Long id, Notification entity);

    List<NotificationResponse> getAllNotificationActive();

    List<NotificationResponse> getAllActiveByMemberId(Long id);
}
