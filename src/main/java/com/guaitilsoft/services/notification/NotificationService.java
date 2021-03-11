package com.guaitilsoft.services.notification;

import com.guaitilsoft.web.models.notification.NotificationLazyResponse;
import com.guaitilsoft.web.models.notification.NotificationResponse;

import java.util.List;

public interface NotificationService {
    List<NotificationResponse> list();

    NotificationResponse get(Long id);

    NotificationResponse update(Long id);

    List<NotificationResponse> getAllNotificationActive();

    List<NotificationLazyResponse> getAllActiveByMemberId(Long id);
}
