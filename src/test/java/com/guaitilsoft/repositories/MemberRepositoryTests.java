package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.utils.UtilsTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class MemberRepositoryTests {
    @Autowired
    MemberRepository memberRepository;

    Member memberBasic;

    @Before
    public void init() {
        this.memberBasic = UtilsTest.createBasicMember();
    }

    @Test
    public void should_get_all_members() {
        Member memberStored = memberRepository.save(memberBasic);

        List<Member> members = new ArrayList<>();
        memberRepository.findAll().forEach(members::add);

        assertThat(members).contains(memberStored);

    }

    @Test
    public void should_get_member_by_id() {
        memberRepository.save(memberBasic);
        Long id = memberBasic.getId();
        Member memberFounded = memberRepository.findById(id).orElse(null);

        assertThat(memberFounded).isNotNull();
    }

    @Test
    public void should_create_only_member() {
        Member memberStored = memberRepository.save(memberBasic);
        assertThat(memberStored.getId()).isNotNull();
    }

    @Test
    public void should_create_member_whit_a_local() {
        addLocalToMemberBasic();
        Member memberStored = memberRepository.save(memberBasic);
        assertThat(memberStored.getId()).isNotNull();

        List<Local> localsStored = memberStored.getLocals();

        Local local = localsStored.stream().findFirst().orElse(null);

        assertThat(localsStored).isNotEmpty();
        assertThat(local.getMember().getId()).isEqualTo(memberStored.getId());
    }

    @Test
    public void should_update_member() {
        Member memberStored = memberRepository.save(memberBasic);
        memberStored.setOccupation("Nueva ocupacion");
        Member memberUpdated = memberRepository.save(memberBasic);
        assertThat(memberUpdated.getOccupation()).isEqualTo("Nueva ocupacion");
    }

    @Test
    public void should_delete_only_member() {
        Member memberStored = memberRepository.save(memberBasic);
        Long id = memberBasic.getId();
        memberRepository.delete(memberStored);

        Member existMember = memberRepository.findById(id).orElse(null);

        assertThat(existMember).isNull();
    }

    @Test
    public void should_delete_member_with_a_local() {
        addLocalToMemberBasic();
        Member memberStored = memberRepository.save(memberBasic);
        Long id = memberBasic.getId();

        memberRepository.delete(memberStored);

        Member existMember = memberRepository.findById(id).orElse(null);

        assertThat(existMember).isNull();
    }

    private void addLocalToMemberBasic() {
        List<Local> locals = Collections.singletonList(UtilsTest.createBasicLocal());
        memberBasic.setLocals(locals);
    }
}
