package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Notification;
import com.guaitilsoft.repositories.NotificationRepository;
import com.guaitilsoft.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImp implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImp(NotificationRepository notificationRepository) {
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
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null){
            return notification;
        }

        throw new EntityNotFoundException("No se encontro la notificación con el id: "+ id);
    }

    @Override
    public void save(String description, List<Member> members) {
        Notification notification = new Notification();
        notification.setDescription(description);
        notification.setReadNotification(true);
        notification.setMembers(members);
        notification.setDate(new Date());

        notificationRepository.save(notification);
    }

    @Override
    public void update(Long id, Notification  entity) {
        assert id != null;

        Notification notification = this.get(id);

        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotificationActive() {
        return this.list().stream().filter(Notification::getReadNotification).collect(Collectors.toList());
    }

    @Override
    public List<Notification> getAllActiveByMemberId(Long id) {
        List<Notification> notifications = new ArrayList<>();
        this.list().forEach(n -> {
            n.getMembers().forEach(m -> {
                if (m.getId().equals(id)){
                    if (n.getReadNotification()){
                        notifications.add(n);
                    }
                }
            });
        });

        return notifications;
    }
}
