package com.guaitilsoft.services.notification;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Notification;
import com.guaitilsoft.services.user.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Primary
@Service("NotificationRepositoryServiceValidation")
public class NotificationRepValidationRepositoryServiceImp implements NotificationRepServ {

    private final NotificationRepositoryService notificationRepositoryService;
    private final UserRepositoryService userRepositoryService;

    @Autowired
    public NotificationRepValidationRepositoryServiceImp(NotificationRepositoryService notificationRepositoryService,
                                                         UserRepositoryService userRepositoryService) {
        this.notificationRepositoryService = notificationRepositoryService;
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public List<Notification> list() {
        return notificationRepositoryService.list();
    }

    @Override
    public Notification get(Long id) {
        Notification notification = notificationRepositoryService.get(id);
        if (notification != null){
            return notification;
        }
        throw new EntityNotFoundException("No se encontro la notificación con el id: "+ id);
    }

    @Override
    public void save(String description, List<Member> members) {
        notificationRepositoryService.save(description, members);
    }

    @Override
    public Notification update(Long id, Notification entity) {
        if(!id.equals(entity.getId())){
            throw new ApiRequestException("El id de la notitificación: " + entity.getId() + " es diferente al id del parametro: " + id);
        }
        entity.setIsActive(false);
        return notificationRepositoryService.update(id, entity);
    }

    @Override
    public void createAdminNotification(String description) {
        List<Member> membersAdmins = new ArrayList<>();
        userRepositoryService.getUsersAdmin().forEach(user -> membersAdmins.add(user.getMember()));
        notificationRepositoryService.save(description, membersAdmins);
    }

    @Override
    public List<Notification> getAllNotificationActive() {
        return this.list().stream().filter(Notification::getIsActive).collect(Collectors.toList());
    }

    @Override
    public List<Notification> getAllActiveByMemberId(Long id) {
        List<Notification> notifications = new ArrayList<>();
        this.list().forEach(n -> n.getMembers().forEach(m -> {
            if (m.getId().equals(id)){
                if (n.getIsActive()){
                    notifications.add(n);
                }
            }
        }));
        return notifications;
    }
}
