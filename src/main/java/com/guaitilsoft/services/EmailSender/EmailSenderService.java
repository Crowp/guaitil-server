package com.guaitilsoft.services.EmailSender;

import org.springframework.scheduling.annotation.Async;

public interface EmailSenderService {

    @Async("threadPoolTaskExecutor")
    void sendEmail(String subject, String emailFrom, String emailTo, String template);

}
