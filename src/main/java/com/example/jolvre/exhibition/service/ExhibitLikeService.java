package com.example.jolvre.exhibition.service;

import com.example.jolvre.common.error.exhibition.ExhibitLikeDuplicationException;
import com.example.jolvre.common.error.exhibition.ExhibitNotFoundException;
import com.example.jolvre.common.util.RedisUtil;
import com.example.jolvre.exhibition.entity.Exhibit;
import com.example.jolvre.exhibition.repository.ExhibitRepository;
import com.example.jolvre.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExhibitLikeService {
    private final ExhibitRepository exhibitRepository;
    private final UserService userService;
    private final RedisUtil redisUtil;

    //전시 좋아요 (한 전시당 8시간마다 좋아요 가능)
    public void likeUpExhibit(Long exhibitId, Long userId) {
        String redisKey = "LIKE_" + exhibitId + userId;

        if (redisUtil.getData(redisKey) != null) {
            throw new ExhibitLikeDuplicationException();
        }

        Exhibit exhibit = exhibitRepository.findById(exhibitId).orElseThrow(ExhibitNotFoundException::new);
        exhibit.likeUp();
        exhibitRepository.save(exhibit);

        redisUtil.setData(redisKey, String.valueOf(exhibit.getLikes()));
    }
}
