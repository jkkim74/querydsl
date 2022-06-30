package com.example.querydsl.controller;

import com.example.querydsl.dto.MemberSearchCon;
import com.example.querydsl.dto.MemberTeamDto;
import com.example.querydsl.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberJpaRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCon condition){
        return memberJpaRepository.search(condition);
    }

    @GetMapping("/v1/members/{page}")
    public Page<MemberTeamDto> searchMemberSimpleV1(MemberSearchCon condition, @PathVariable("page") int page){
        return memberJpaRepository.searchPageSimple(condition, PageRequest.of(page,10));
    }

    @GetMapping("/v2/members/{page}")
    public Page<MemberTeamDto> searchMemberComplexV1(MemberSearchCon condition, @PathVariable("page") int page){
        return memberJpaRepository.searchPageComplex(condition, PageRequest.of(page,10));
    }
}
