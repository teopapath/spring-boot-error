package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorityRoleDTO {

    private String userId;
    private AuthorityStatus authorityStatus;
    private String roleName;
    private String roleCode;
    private LocalDateTime creationDate;
}
