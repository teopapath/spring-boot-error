package com.example.demo.error2990_2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountContactInfoDTO {

    private Long accountId;

    private String accountName;

    private String userId;

}
