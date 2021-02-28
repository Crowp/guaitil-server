package com.guaitilsoft.scheduledTasks;

import com.guaitilsoft.services.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final MemberService memberService;

    @Autowired
    public ScheduledTask(MemberService memberService) {
        this.memberService = memberService;
    }

    @Scheduled(fixedRate = 15000)
    public void deleteMember(){
        logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
        memberService.delete(2L);
        logger.info("Member had been deleted");
    }
}
