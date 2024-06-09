package com.example.jolvre.exhibition.service;

import com.example.jolvre.common.error.comment.CommentNotFoundException;
import com.example.jolvre.common.error.exhibition.ExhibitNotFoundException;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitCommentInfoResponses;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitCommentUpdateRequest;
import com.example.jolvre.exhibition.dto.ExhibitDTO.ExhibitCommentUploadRequest;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.entity.ExhibitComment;
import com.example.jolvre.exhibition.repository.ExhibitCommentRepository;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.service.UserService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExhibitCommentService {
    private final ExhibitRepository exhibitRepository;
    private final UserService userService;
    private final ExhibitCommentRepository exhibitCommentRepository;


    @Transactional
    public void uploadComment(Long exhibitId, Long loginUserId, ExhibitCommentUploadRequest request) {
        Exhibit exhibit = exhibitRepository.findById(exhibitId).orElseThrow(
                ExhibitNotFoundException::new);
        User user = userService.getUserById(loginUserId);

        ExhibitComment comment = ExhibitComment.builder()
                .exhibit(exhibit)
                .user(user)
                .content(request.getContent()).build();

        exhibitCommentRepository.save(comment);
    }

    @Transactional
    public ExhibitCommentInfoResponses getAllCommentInfo(Long exhibitId) {
        List<ExhibitComment> comments = exhibitCommentRepository.findAllByExhibitId(exhibitId);

        return ExhibitCommentInfoResponses.toDTO(comments);
    }

    @Transactional
    public void updateComment(Long commentId, Long loginUserId, ExhibitCommentUpdateRequest request) {
        ExhibitComment comment = exhibitCommentRepository.findByIdAndUserId(commentId, loginUserId)
                .orElseThrow(CommentNotFoundException::new);

        comment.updateContent(request.getContent());

        exhibitCommentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long loginUserId) {
        ExhibitComment comment = exhibitCommentRepository.findByIdAndUserId(commentId, loginUserId)
                .orElseThrow(CommentNotFoundException::new);

        exhibitCommentRepository.delete(comment);
    }
}
