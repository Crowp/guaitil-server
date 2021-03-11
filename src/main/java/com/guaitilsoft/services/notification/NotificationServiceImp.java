package com.guaitilsoft.services.notification;

import com.guaitilsoft.models.Notification;
import com.guaitilsoft.web.models.notification.NotificationLazyResponse;
import com.guaitilsoft.web.models.notification.NotificationResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class NotificationServiceImp implements NotificationService {

    private final NotificationRepServ notificationRepServ;
    private final ModelMapper modelMapper;

    @Autowired
    public NotificationServiceImp(NotificationRepServ notificationRepServ,
                                  ModelMapper modelMapper) {
        this.notificationRepServ = notificationRepServ;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<NotificationResponse> list() {
        return this.parseToNotificationResponseList(notificationRepServ.list());
    }

    @Override
    public NotificationResponse get(Long id) {
        return this.parseToNotificationResponse(notificationRepServ.get(id));
    }

    @Override
    public NotificationResponse update(Long id) {
        return this.parseToNotificationResponse(notificationRepServ.updateNotification(id));
    }

    @Override
    public List<NotificationResponse> getAllNotificationActive() {
        return this.parseToNotificationResponseList(notificationRepServ.getAllNotificationActive());
    }

    @Override
    public List<NotificationLazyResponse> getAllActiveByMemberId(Long id) {
        return this.parseToNotificationLazyResponse(notificationRepServ.getAllActiveByMemberId(id));
    }

    private List<NotificationResponse> parseToNotificationResponseList(List<Notification> notifications){
        Type listType = new TypeToken<List<NotificationResponse>>(){}.getType();
        return modelMapper.map(notifications, listType);
    }

    private NotificationResponse parseToNotificationResponse(Notification notification){
        return modelMapper.map(notification, NotificationResponse.class);
    }

    private List<NotificationLazyResponse> parseToNotificationLazyResponse(List<Notification> notifications){
        Type listType = new TypeToken<List<NotificationResponse>>(){}.getType();
        return modelMapper.map(notifications, listType);
    }
}
