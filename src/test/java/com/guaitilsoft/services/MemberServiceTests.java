package com.guaitilsoft.services;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.repositories.MemberRepository;
import com.guaitilsoft.services.concrete.MemberServiceImp;
import com.guaitilsoft.utils.UtilsTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

    MemberService memberService;

    Member memberBasic;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.memberService = new MemberServiceImp(memberRepository);
        this.memberBasic = UtilsTest.createBasicMember();
    }

    @Test
    public void should_create_member() {
        Member memberExpected = UtilsTest.createBasicMember();
        memberExpected.setId(1L);

        given(memberRepository.save(memberBasic)).willReturn(memberExpected);

        Member memberStored = memberService.save(memberBasic);

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
            memberService.save(memberBasic);
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
            memberService.save(memberBasic);
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

        Member memberFounded = memberService.get(1L);

        assertThat(memberFounded).isEqualTo(memberExpected);

    }

    @Test
    public void should_not_get_member_by_id() {

        given(memberRepository.findById(1L)).willReturn(Optional.empty());

        try {
            memberService.get(1L);
            fail("Expected an EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException ex) {
            assertThat(ex.getMessage()).isEqualTo("No se encontró un asociado con el id: " + 1);
        }
    }

}
