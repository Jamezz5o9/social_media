package com.prophius.socialMedia.comment;

import com.prophius.socialMedia.exception.GenericException;
import com.prophius.socialMedia.post.Post;
import com.prophius.socialMedia.post.PostRepository;
import com.prophius.socialMedia.user.AppUser;
import com.prophius.socialMedia.user.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AppUserRepository userRepository;

    public CommentDTO addCommentToPost(Long userId, Long postId, String content) {

        Optional<AppUser> userOpt = userRepository.findById(userId);
        Optional<Post> postOpt = postRepository.findById(postId);

        if (userOpt.isEmpty()) {
            throw new GenericException("User not found with id: " + userId);
        }

        if (postOpt.isEmpty()) {
            throw new GenericException("Post not found with id: " + postId);
        }

        Comment newComment = Comment.builder()
                .content(content)
                .user(userOpt.get())
                .post(postOpt.get())
                .build();

        Comment savedComment = commentRepository.save(newComment);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(savedComment.getId());
        commentDTO.setContent(savedComment.getContent());

        return commentDTO;
    }
}
