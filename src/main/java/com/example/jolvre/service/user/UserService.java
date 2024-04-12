package com.example.jolvre.service.user;

import com.example.jolvre.domain.user.User;
import com.example.jolvre.domain.user.dto.UserSignUpDTO;

public interface UserService {
    public User verifyStudent(User user);

    public void signUp(UserSignUpDTO userSignUpDTO) throws Exception;

    public User update(User user);
}
