package com.pairlearning.expensetracker.user.service;

import com.pairlearning.expensetracker.user.dto.AuthDto;
import com.pairlearning.expensetracker.user.dto.UserDto;
import com.pairlearning.expensetracker.user.exception.AuthException;
import com.pairlearning.expensetracker.user.exception.ConflictException;
import com.pairlearning.expensetracker.user.exception.UserNotFoundException;

public interface UserService {
    UserDto validateUser(AuthDto authDto) throws UserNotFoundException;
    UserDto createUser(UserDto userDto) throws ConflictException;
}
