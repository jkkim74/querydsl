package com.example.querydsl.repository;

import com.example.querydsl.dto.MemberSearchCon;
import com.example.querydsl.dto.MemberTeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepositoryCustom {

    List<MemberTeamDto> search(MemberSearchCon condition);
    Page<MemberTeamDto> searchPageSimple(MemberSearchCon condition, Pageable pageable);
    Page<MemberTeamDto>  searchPageComplex(MemberSearchCon condition, Pageable pageable);

}
