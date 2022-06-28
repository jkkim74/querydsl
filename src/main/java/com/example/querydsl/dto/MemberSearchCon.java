package com.example.querydsl.dto;

import lombok.Data;

@Data
public class MemberSearchCon {

    private String username;
    private String teamName;
    private Integer ageGoe;
    private Integer ageLoe;
}
