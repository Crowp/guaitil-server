package com.guaitilsoft.services;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Notification;
import org.aspectj.weaver.ast.Not;

import java.util.List;

public interface NotificationService {

    List<Notification> list();

    Notification get(Long id);

    void save(String description, List<Member> members);

    void update(Long id, Notification entity);

    List<Notification> getAllNotificationActive();

    List<Notification> getAllActiveByMemberId(Long id);
}
