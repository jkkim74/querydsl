package com.example.querydsl.repository;

import com.example.querydsl.dto.MemberSearchCon;
import com.example.querydsl.dto.MemberTeamDto;
import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void basicTest(){
        Member member = new Member("member1",10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> result1 = memberJpaRepository.findAll();
        assertThat(result1).containsExactly(member);

        List<Member> result2 = memberJpaRepository.findByUsername("member1");
        assertThat(result2).containsExactly(member);

    }

    @Test
    public void basicQuerydslTest(){
        Member member = new Member("member2",20);
        memberJpaRepository.save(member);

        List<Member> result1 = memberJpaRepository.findAll_Querydsl();
        assertThat(result1).containsExactly(member);

        List<Member> result2 = memberJpaRepository.findByUsername_Querydsl("member2");
        assertThat(result2).containsExactly(member);
    }

    @Test
    @Commit
    public void searchBuilderTest(){
        //em.flush();
        //em.clear();

        Team team1 = new Team("team1");
        Team team2 = new Team("team2");
        em.persist(team1);
        em.persist(team2);

        Member member = new Member("member1",10,team1);
        Member member1 = new Member("member2",20,team1);
        Member member2 = new Member("member3",30,team2);
        Member member3 = new Member("member4",40,team2);
        Member member4 = new Member("member5",50,team2);
        memberJpaRepository.save(member);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);
        memberJpaRepository.save(member4);

        MemberSearchCon condition = new MemberSearchCon();
//        condition.setUsername("member1");
//        condition.setTeamName("team1");
//        condition.setAgeGoe(10);
//        condition.setAgeLoe(50);
        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(condition);

        result.stream().forEach(System.out::println);

    }


    @Test
    @Commit
    public void searchTest(){
        //em.flush();
        //em.clear();

        Team team1 = new Team("team1");
        Team team2 = new Team("team2");
        em.persist(team1);
        em.persist(team2);

        Member member = new Member("member1",10,team1);
        Member member1 = new Member("member2",20,team1);
        Member member2 = new Member("member3",30,team2);
        Member member3 = new Member("member4",40,team2);
        Member member4 = new Member("member5",50,team2);
        memberJpaRepository.save(member);
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);
        memberJpaRepository.save(member4);

        MemberSearchCon condition = new MemberSearchCon();
        condition.setUsername("member1");
        condition.setTeamName("team1");
        condition.setAgeGoe(10);
        condition.setAgeLoe(50);
        List<MemberTeamDto> result = memberJpaRepository.search(condition);

        assertThat(result).extracting("username").containsExactly("member1");

    }


}