package com.example.jolvre.post.service;

import com.amazonaws.util.CollectionUtils;
import com.example.jolvre.common.error.post.PostNotFoundException;
import com.example.jolvre.common.error.user.UserAccessDeniedException;
import com.example.jolvre.common.error.user.UserNotFoundException;
import com.example.jolvre.common.service.S3Service;
import com.example.jolvre.post.dto.postRequest;
import com.example.jolvre.post.dto.postResponse;
import com.example.jolvre.post.entity.Category;
import com.example.jolvre.post.entity.Post;
import com.example.jolvre.post.entity.PostImage;
import com.example.jolvre.post.repository.PostImageRepository;
import com.example.jolvre.post.repository.PostRepository;
import com.example.jolvre.user.entity.User;
import java.util.List;
import java.util.Objects;

import com.example.jolvre.user.repository.UserRepository;
import com.example.jolvre.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import java.util.ArrayList;

@Service
@Builder
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final S3Service s3Service;
    private final PostImageRepository postImageRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public void upload(postRequest request, User loginuser) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(loginuser);
        post.setCategory(request.getCategory());

        //List<MultipartFile>이 비어있지 않을 때만 s3에 이미지 저장
        if (!CollectionUtils.isNullOrEmpty(request.getImages())) {
            List<PostImage> postImages = new ArrayList<>();
            s3Service.uploadImages(request.getImages()).forEach(
                    url -> {
                        PostImage postImage = PostImage.builder().url(url).build();
                        post.addImage(postImage);
                        postImages.add(postImage);
                    }
            );
            postRepository.save(post);
            postImageRepository.saveAll(postImages);
        }
        else 
            postRepository.save(post);

        log.info("[post] : {} 업로드 성공", post.getTitle());
    }

    public Page<postResponse> getAllPost(PageRequest pageable) {
        Page<Post> postList = postRepository.findAll(pageable);
        return postList.map(postResponse::toDTO);
    }

    public Page<postResponse> getPostsByUserId(Long userId, PageRequest pageable) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException();
        }
        else{
            Page<Post> postList = postRepository.findAllByUserId(userId, pageable);
            log.info("[post] : {}의 모든 게시글 조회", userId);
            return postList.map(postResponse::toDTO);
        }
    }

    public postResponse getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        log.info("[post] : {} 불러오기", Objects.requireNonNull(post).getTitle());
        return postResponse.toDTO(post);
    }

    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (!Objects.equals(post.getUser().getId(), user.getId()))
        {
            log.info("[post] : 게시글 삭제 권한이 없습니다");
            throw new UserAccessDeniedException();
        }
        else {
            postRepository.deleteById(postId);
            log.info("[post] : {} 게시글 삭제 완료", postId);
        }
    }

    public void updatePost(postRequest request, Long postId, User loginuser) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        //로그인 한 유저가 해당 게시글의 글쓴이일때만 수정 가능
        if (!Objects.equals(existingPost.getUser().getId(), loginuser.getId())) {
            log.info("[post] : 게시글 수정 권한이 없습니다");
            throw new UserAccessDeniedException();
        } else {
            existingPost.setTitle(request.getTitle());
            existingPost.setContent(request.getContent());

            //List<MultipartFile>이 비어있지 않을 때만 s3에 이미지 저장
            if (!CollectionUtils.isNullOrEmpty(request.getImages())) {
                //s3 상의 이미지 파일 삭제
                int i = 0;
                while (i < postResponse.toDTO(existingPost).getImagesUrl().toArray().length) {
                    s3Service.deleteImage(postResponse.toDTO(existingPost).getImagesUrl().get(i));
                    i++;
                    log.info("{}번쨰 이미지를 s3상에서 삭제", i);
                }

                //DB 상의 이미지 삭제, 게시글과 매핑된 이미지 삭제
                postImageRepository.deleteAll(existingPost.getPostImages());
                existingPost.setPostImages(new ArrayList<>());

                //이미지 파일 업로드
                List<PostImage> postImages = new ArrayList<>();
                s3Service.uploadImages(request.getImages()).forEach(
                        url -> {
                            PostImage postImage = PostImage.builder().url(url).build();
                            existingPost.addImage(postImage);
                            postImages.add(postImage);
                        }
                );
                postRepository.save(existingPost);
                postImageRepository.saveAll(postImages);
            }
            else
                postRepository.save(existingPost);

            log.info("[post] : {} 게시글 수정 완료", request.getTitle());
        }
    }

    @Transactional
    public Page<postResponse> searchByKeyword(String keyword, Pageable pageable) {
        Page<Post> postList = postRepository.findByTitleContaining(keyword, pageable);
        return postList.map(postResponse::toDTO);
    }

    public void updateViews(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
        post.setView(post.getView() + 1);
        postRepository.save(post);
    }
}
