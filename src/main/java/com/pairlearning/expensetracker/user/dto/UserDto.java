package com.pairlearning.expensetracker.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
