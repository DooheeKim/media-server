package com.doohee.mediaserver.service;

import com.doohee.mediaserver.dto.CommentAbstractDto;
import com.doohee.mediaserver.entity.Comment;
import com.doohee.mediaserver.entity.User;
import com.doohee.mediaserver.entity.Video;
import com.doohee.mediaserver.exception.NoPermissionException;
import com.doohee.mediaserver.exception.NoUsernameException;
import com.doohee.mediaserver.repository.CommentRepository;
import com.doohee.mediaserver.repository.UserRepository;
import com.doohee.mediaserver.repository.VideoRepository;
import com.doohee.mediaserver.repository.support.CommentRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentRepositorySupport commentRepositorySupport;
    @Autowired
    VideoService videoService;


    // 댓글 작성
    public CommentAbstractDto writeComment(String username, String videoId, String content, String parentCommentId){
        if(!videoService.checkVideoPermission(username, videoId)){
            throw new NoPermissionException("해당 요청에 대한 접근 권한이 없습니다.");
        }
        User user = userRepository.findByUsername(username).get(); // 존재하지 않는 유저인지 위에서 이미 체크함
        Video video = videoRepository.findById(videoId).get(); // 존재하지 않는 비디오인지 위에서 이미 체크함

        Comment parentComment = null;
        if(parentCommentId!=null){
            Optional<Comment> parentCommentOpt = commentRepository.findById(parentCommentId);
            if(parentCommentOpt.isPresent()){
                parentComment = parentCommentOpt.get();
            }
        }


        Comment comment = Comment.builder()
                .commentId(UUID.randomUUID().toString())
                .fixed(false)
                .isDeleted(false)
                .video(video)
                .writer(user)
                .content(content)
                .registerDate(LocalDateTime.now())
                .parent(parentComment)
                .build();

        return CommentAbstractDto.from(comment);
    }
    // 댓글 수정
    public CommentAbstractDto modifyComment(String username, String commentId, String content){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) throw new NoUsernameException("존재하지 않는 유저입니다.");

        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if(commentOpt.isEmpty() || !commentOpt.get().getWriter().equals(user.get())){
            throw new NoPermissionException("작업을 수행할 권한이 없습니다");
        }
        Comment comment = commentOpt.get();
        comment.setContent(content); // 콘텐츠 바꾸기
        commentRepository.save(comment);

        return CommentAbstractDto.from(comment);
    }

    // 댓글 삭제
    public CommentAbstractDto deleteComment(String username, String commentId){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) throw new NoUsernameException("존재하지 않는 유저입니다.");

        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if(commentOpt.isEmpty() || !commentOpt.get().getWriter().equals(user.get())){
            throw new NoPermissionException("작업을 수행할 권한이 없습니다");
        }
        Comment comment = commentOpt.get();
        comment.setDeleted(true); // 콘텐츠 바꾸기
        commentRepository.save(comment);

        return new CommentAbstractDto(commentId);
    }
    // 댓글 가져오기
    public Page<CommentAbstractDto> getCommentsList(String username, String videoId, Integer page, Integer limit){
        if(!videoService.checkVideoPermission(username, videoId)){
            throw new NoPermissionException("해당 요청에 대한 접근 권한이 없습니다.");
        }
        return commentRepositorySupport.findCommentByVideoId(videoId, PageRequest.of(page, limit));
    }
}
