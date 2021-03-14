package com.guaitilsoft.services.notification;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Notification;

import java.util.List;

public interface NotificationRepositoryService {
    List<Notification> list();

    Notification get(Long id);

    void save(String description, List<Member> members);

    Notification update(Notification entity);
}
