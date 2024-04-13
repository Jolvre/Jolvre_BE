package com.example.jolvre.service.user;

import com.example.jolvre.domain.user.User;
import com.example.jolvre.domain.user.dto.UserSignUpDTO;
import com.example.jolvre.domain.user.dto.verify.VerifyStudentByEmailRequestDTO;
import com.example.jolvre.domain.user.dto.verify.VerifyStudentByEmailResponseDTO;
import com.example.jolvre.domain.user.dto.verify.VerifyStudentCallRequestDTO;
import com.example.jolvre.domain.user.dto.verify.VerifyStudentCallResponseDTO;

public interface UserService {
    public VerifyStudentCallResponseDTO verifyStudentCall(VerifyStudentCallRequestDTO verifyStudentRequestDTO);

    public VerifyStudentByEmailResponseDTO verifyStudentByEmail(
            VerifyStudentByEmailRequestDTO verifyStudentByEmailRequestDTO, User user);

    public void signUp(UserSignUpDTO userSignUpDTO) throws Exception;

    public User updateAuthorize(User user);
}
