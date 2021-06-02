package com.guaitilsoft.services;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.repositories.LocalRepository;
import com.guaitilsoft.services.local.LocalRepositoryService;
import com.guaitilsoft.services.local.LocalRepositoryServiceImp;
import com.guaitilsoft.services.member.MemberRepositoryServiceImp;
import com.guaitilsoft.utils.UtilsTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class LocalServiceTests {
    @Mock
    LocalRepository localRepository;

    LocalRepositoryService localRepositoryService;

    Local localBasic;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.localRepositoryService = new LocalRepositoryServiceImp(localRepository);
        this.localBasic = UtilsTest.createBasicLocal();
    }

    @Test
    public void should_create_local() {
        Member member = UtilsTest.createBasicMember();
        Local localExpected = UtilsTest.createBasicLocal();
        localExpected.setId(1L);
        localExpected.setMember(member);
        localBasic.setMember(member);

        given(localRepository.save(localBasic)).willReturn(localExpected);
        given(localRepository.existMemberPersonLocal("901110534", LocalType.WORKSHOP)).willReturn(false);

        Local localStored = localRepositoryService.save(localBasic);

        assertThat(localStored).isNotNull();

        verify(localRepository).save(any(Local.class));
    }


    @Test
    public void should_get_local_by_id() {
        Local localExpected = UtilsTest.createBasicLocal();
        localExpected.setId(1L);
        given(localRepository.findById(1L)).willReturn(Optional.of(localExpected));

        Local localFounded = localRepositoryService.get(1L);

        assertThat(localFounded).isEqualTo(localExpected);

    }
}
