package com.guaitilsoft.services.notification;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Notification;
import com.guaitilsoft.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("NotificationRepositoryServiceBasic")
public class NotificationRepositoryServiceImp implements NotificationRepositoryService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationRepositoryServiceImp(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> list() {
        Iterable<Notification> iterable = notificationRepository.findAll();
        List<Notification> notifications = new ArrayList<>();
        iterable.forEach(notifications::add);
        return notifications;
    }

    @Override
    public Notification get(Long id) {
        assert id != null;
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public void save(String description, List<Member> members) {
        Notification notification = new Notification();
        notification.setDescription(description);
        notification.setIsActive(true);
        notification.setMembers(members);

        notificationRepository.save(notification);
    }

    @Override
    public Notification update(Notification entity) {
        return notificationRepository.save(entity);
    }
}
