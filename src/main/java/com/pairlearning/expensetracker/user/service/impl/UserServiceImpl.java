package com.pairlearning.expensetracker.user.service.impl;

import com.pairlearning.expensetracker.user.dto.AuthDto;
import com.pairlearning.expensetracker.user.dto.UserDto;
import com.pairlearning.expensetracker.user.entity.User;
import com.pairlearning.expensetracker.user.exception.AuthException;
import com.pairlearning.expensetracker.user.exception.ConflictException;
import com.pairlearning.expensetracker.user.exception.UserNotFoundException;
import com.pairlearning.expensetracker.user.mapper.UserMapper;
import com.pairlearning.expensetracker.user.repository.UserRepository;
import com.pairlearning.expensetracker.user.service.UserService;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto validateUser(AuthDto authDto) throws UserNotFoundException {
        User user = userRepository.findByEmail(authDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Invalid credentials"));

        if (!BCrypt.checkpw(authDto.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid credentials");
        }

        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) throws ConflictException {
        String email = "";
        User user = UserMapper.mapToUser(userDto);


        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
        Pattern pattern = Pattern.compile("^(.+)@(.+)");

        if (user.getEmail() != null) email = user.getEmail().toLowerCase();

        if (!pattern.matcher(email).matches())
            throw new ConflictException("Invalid email format");

        Optional<User> userExists = userRepository.findByEmail(user.getEmail());

        if (userExists.isPresent()) {
            throw new ConflictException("Email address is already in use");
        }

        User savedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(savedUser);

    }
}
