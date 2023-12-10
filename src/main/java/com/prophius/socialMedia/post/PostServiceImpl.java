package com.prophius.socialMedia.post;

import com.prophius.socialMedia.exception.GenericException;
import com.prophius.socialMedia.user.AppUser;
import com.prophius.socialMedia.user.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AppUserRepository userRepository;
    @Transactional
    public PostDTO createPost(Long userId, String content) {

        Optional<AppUser> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) throw new GenericException("User not found with id: " + userId);


        Post newPost = new Post();
        newPost.setContent(content);
        newPost.setUser(userOpt.get());
        newPost.setLikesCount(0);

        Post savedPost = postRepository.save(newPost);

        PostDTO postDTO = new PostDTO();
        postDTO.setId(savedPost.getId());
        postDTO.setContent(savedPost.getContent());
        postDTO.setLikesCount(savedPost.getLikesCount());

        return postDTO;
    }
    @Transactional
    public void likePost(Long userId, Long postId) {
        Optional<AppUser> userOpt = userRepository.findById(userId);
        Optional<Post> postOpt = postRepository.findById(postId);

        if (userOpt.isPresent() && postOpt.isPresent()) {
            Post post = postOpt.get();
            post.getLikedBy().add(userOpt.get());
            post.setLikesCount(post.getLikesCount() + 1);

            postRepository.save(post);
        } else throw new GenericException("Post not found");

    }
    @Transactional
    public void unlikePost(Long userId, Long postId) {
        Optional<AppUser> userOpt = userRepository.findById(userId);
        Optional<Post> postOpt = postRepository.findById(postId);

        if (userOpt.isPresent() && postOpt.isPresent()) {
            Post post = postOpt.get();
            post.setLikesCount(post.getLikesCount() - 1);
            post.getLikedBy().remove(userOpt.get());

            postRepository.save(post);
        } else throw new GenericException("Post not found");

    }
}
