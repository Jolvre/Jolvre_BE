package com.example.jolvre.user.service;

import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.dto.UserSignUpDTO;
import com.example.jolvre.user.dto.verify.VerifyStudentByEmailRequestDTO;
import com.example.jolvre.user.dto.verify.VerifyStudentByEmailResponseDTO;
import com.example.jolvre.user.dto.verify.VerifyStudentCallRequestDTO;
import com.example.jolvre.user.dto.verify.VerifyStudentCallResponseDTO;

public interface UserService {
    public VerifyStudentCallResponseDTO verifyStudentCall(VerifyStudentCallRequestDTO verifyStudentRequestDTO);

    public VerifyStudentByEmailResponseDTO verifyStudentByEmail(
            VerifyStudentByEmailRequestDTO verifyStudentByEmailRequestDTO, User user);

    public void signUp(UserSignUpDTO userSignUpDTO) throws Exception;

    public User updateAuthorize(User user);
}
