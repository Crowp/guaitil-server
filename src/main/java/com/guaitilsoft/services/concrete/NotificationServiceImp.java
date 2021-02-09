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
        notification.setState(true);
        notification.setMembers(members);
        notification.setDate(new Date());

        notificationRepository.save(notification);
    }

    @Override
    public void update(Long id) {
        assert id != null;

        Notification notification = this.get(id);
        notification.setState(false);

        notificationRepository.save(notification);
    }
}
