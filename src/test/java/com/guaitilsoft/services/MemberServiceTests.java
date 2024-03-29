package com.guaitilsoft.services;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.repositories.MemberRepository;
import com.guaitilsoft.services.member.MemberRepositoryServiceImp;
import com.guaitilsoft.services.member.MemberRepositoryService;
import com.guaitilsoft.utils.UtilsTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class MemberServiceTests {

    @Mock
    MemberRepository memberRepository;

    MemberRepositoryService memberRepositoryService;

    Member memberBasic;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.memberRepositoryService = new MemberRepositoryServiceImp(memberRepository);
        this.memberBasic = UtilsTest.createBasicMember();
    }

    @Test
    public void should_create_member() {
        Member memberExpected = UtilsTest.createBasicMember();
        memberExpected.setId(1L);

        given(memberRepository.save(memberBasic)).willReturn(memberExpected);

        Member memberStored = memberRepositoryService.save(memberBasic);

        assertThat(memberStored).isNotNull();

        verify(memberRepository).save(any(Member.class));
    }

    @Test
    public void should_not_create_member_that_already_exists_by_dni() {
        Member memberExpected = UtilsTest.createBasicMember();
        memberExpected.setId(1L);

        String personId = memberExpected.getPerson().getId();

        given(memberRepository.existMemberPersonId(personId)).willReturn(true);

        memberBasic.getPerson().setId(personId);

        try {
            memberRepositoryService.save(memberBasic);
            fail("Expected an ApiRequestException to be thrown");
        } catch (ApiRequestException ex) {
            assertThat(ex.getMessage()).isEqualTo("Cédula: " + personId + " esta ocupada");
        }
    }

    @Test
    public void should_not_create_member_that_already_exists_by_email() {
        Member memberExpected = UtilsTest.createBasicMember();
        memberExpected.setId(1L);

        String email = memberExpected.getPerson().getEmail();

        given(memberRepository.existMemberPersonEmail(email)).willReturn(true);

        memberBasic.getPerson().setEmail(email);

        try {
            memberRepositoryService.save(memberBasic);
            fail("Expected an ApiRequestException to be thrown");
        } catch (ApiRequestException ex) {
            assertThat(ex.getMessage()).isEqualTo("Email: " + email + " esta ocupado");
        }
    }

    @Test
    public void should_get_member_by_id() {
        Member memberExpected = UtilsTest.createBasicMember();
        memberExpected.setId(1L);
        given(memberRepository.findById(1L)).willReturn(Optional.of(memberExpected));

        Member memberFounded = memberRepositoryService.get(1L);

        assertThat(memberFounded).isEqualTo(memberExpected);

    }

}
