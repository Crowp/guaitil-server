package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Activity;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.utils.UtilsTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class LocalRepositoryTests {

    @Autowired
    LocalRepository localRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    MemberRepository memberRepository;


    Local localBasic;

    @Before
    public void init() {
        this.localBasic = UtilsTest.createBasicLocal();
    }

    @Test
    public void should_create_local() {
        Member member = UtilsTest.createBasicMember();
        Member memberStored = memberRepository.save(member);

        localBasic.setMember(memberStored);
        Local localStored = localRepository.save(localBasic);
        assertThat(localStored.getMember()).isNotNull();
    }

    @Test
    public void should_delete_local() {
        Local localStored = localRepository.save(localBasic);
        Long id = localStored.getId();
        localRepository.delete(localStored);

        Local existsLocal = localRepository.findById(id).orElse(null);

        assertThat(existsLocal).isNull();
    }

    @Test
    public void should_delete_local_that_be_in_activities() {
        Local localStored = localRepository.save(localBasic);
        Long id = localStored.getId();

        Long activityId = addToActivity(localStored.getLocalDescription());

        localRepository.delete(localStored);

        Local existsLocal = localRepository.findById(id).orElse(null);

        Activity activityWithoutLocal = activityRepository.findById(activityId).orElse(null);

        assertThat(activityWithoutLocal).isNotNull();
        assertThat(activityWithoutLocal.getLocalsDescriptions()).contains(localStored.getLocalDescription());
        assertThat(existsLocal).isNull();
    }

    private Long addToActivity(LocalDescription localDescription) {
        Activity activity = UtilsTest.createBasicActivity();

        activity.getLocalsDescriptions().add(localDescription);

        activityRepository.save(activity);

        return activity.getId();
    }

}
