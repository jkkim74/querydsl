package com.example.querydsl.repository;

import com.example.querydsl.dto.MemberSearchCon;
import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class MemberTestRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired MemberRepository memberRepository;

    @Autowired MemberTestRepository memberTestRepository;

    @BeforeEach
    public void init(){
        Team team1 = new Team("team1");
        Team team2 = new Team("team2");
        em.persist(team1);
        em.persist(team2);

        Member member = new Member("member1",10,team1);
        Member member1 = new Member("member2",20,team1);
        Member member2 = new Member("member3",30,team2);
        Member member3 = new Member("member4",40,team2);
        Member member4 = new Member("member5",50,team2);
        memberRepository.save(member);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);
    }

    @Test
    void basicSelect() {
    }

    @Test
    void basicSelectFrom() {
    }

    @Test
    void searchPageByApplyPage() {
    }

    @Test
    void applyPagenation() {
        MemberSearchCon condition = new MemberSearchCon();
        condition.setUsername("member1");
        condition.setTeamName("team1");
        condition.setAgeGoe(10);
        condition.setAgeLoe(50);

        PageRequest paging = PageRequest.of(0,3);

        Page<Member> members = memberTestRepository.applyPagenation(condition, paging);

        assertThat(members.getTotalPages()).isEqualTo(1);
        // assertThat(members.getContent()).extracting("username").containsExactly("member1","member2","member3");
        assertThat(members.getContent()).extracting("username").containsExactly("member1");
    }

    @Test
    void applyPagenation2() {
        MemberSearchCon condition = new MemberSearchCon();
        condition.setUsername("member1");
        condition.setTeamName("team1");
        condition.setAgeGoe(10);
        condition.setAgeLoe(50);

        PageRequest paging = PageRequest.of(0,3);

        Page<Member> members = memberTestRepository.applyPagenation2(condition, paging);

        assertThat(members.getTotalPages()).isEqualTo(1);
       // assertThat(members.getContent()).extracting("username").containsExactly("member1","member2","member3");
        assertThat(members.getContent()).extracting("username").containsExactly("member1");


    }
}