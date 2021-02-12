package com.guaitilsoft.services;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Notification;

import java.util.List;

public interface NotificationService {

    List<Notification> list();

    Notification get(Long id);

    void save(String description, List<Member> members);

    void createAdminNotification(String description);

    void update(Long id, Notification entity);

    List<Notification> getAllNotificationActive();

    List<Notification> getAllActiveByMemberId(Long id);
}
