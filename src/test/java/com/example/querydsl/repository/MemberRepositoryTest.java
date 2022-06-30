package com.example.querydsl.repository;

import com.example.querydsl.dto.MemberSearchCon;
import com.example.querydsl.dto.MemberTeamDto;
import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired MemberRepository memberRepository;

    @Test
    public void basicTest(){
        Member member = new Member("member1",10);
        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);

        List<Member> result1 = memberRepository.findAll();
        assertThat(result1).containsExactly(member);

        List<Member> result2 = memberRepository.findByUsername("member1");
        assertThat(result2).containsExactly(member);

    }
//
//    @Test
//    public void basicQuerydslTest(){
//        Member member = new Member("member2",20);
//        memberRepository.save(member);
//
//        List<Member> result1 = memberRepository.findAll();
//        assertThat(result1).containsExactly(member);
//
//        List<Member> result2 = memberRepository.findByUsername("member2");
//        assertThat(result2).containsExactly(member);
//    }
//
//    @Test
//    @Commit
//    public void searchBuilderTest(){
//        //em.flush();
//        //em.clear();
//
//        Team team1 = new Team("team1");
//        Team team2 = new Team("team2");
//        em.persist(team1);
//        em.persist(team2);
//
//        Member member = new Member("member1",10,team1);
//        Member member1 = new Member("member2",20,team1);
//        Member member2 = new Member("member3",30,team2);
//        Member member3 = new Member("member4",40,team2);
//        Member member4 = new Member("member5",50,team2);
//        memberRepository.save(member);
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//        memberRepository.save(member3);
//        memberRepository.save(member4);
//
//        MemberSearchCon condition = new MemberSearchCon();
////        condition.setUsername("member1");
////        condition.setTeamName("team1");
////        condition.setAgeGoe(10);
////        condition.setAgeLoe(50);
//        List<MemberTeamDto> result = memberRepository.searchByBuilder(condition);
//
//        result.stream().forEach(System.out::println);
//
//    }
//
//
    @Test
    @Commit
    public void searchTest(){

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

        MemberSearchCon condition = new MemberSearchCon();
        condition.setUsername("member1");
        condition.setTeamName("team1");
        condition.setAgeGoe(10);
        condition.setAgeLoe(50);
        List<MemberTeamDto> result = memberRepository.search(condition);

        assertThat(result).extracting("username").containsExactly("member1");

    }

    @Test
    @Commit
    public void searchPageSimpleTest(){

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

        MemberSearchCon condition = new MemberSearchCon();
        PageRequest paging = PageRequest.of(0, 3);

        Page<MemberTeamDto> result = memberRepository.searchPageSimple(condition, paging);

        assertThat(result.getSize()).isEqualTo(3);
        assertThat(result.getContent()).extracting("username").containsExactly("member1","member2","member3");

    }

    @Test
    @Commit
    public void searchPageComplexTest(){

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

        MemberSearchCon condition = new MemberSearchCon();
        PageRequest paging = PageRequest.of(0, 3);

        Page<MemberTeamDto> result = memberRepository.searchPageComplex(condition, paging);

        assertThat(result.getSize()).isEqualTo(3);
        assertThat(result.getContent()).extracting("username").containsExactly("member1","member2","member3");

    }
}
