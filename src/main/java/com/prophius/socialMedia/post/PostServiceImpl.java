package com.prophius.socialMedia.post;

import com.prophius.socialMedia.exception.GenericException;
import com.prophius.socialMedia.user.AppUser;
import com.prophius.socialMedia.user.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AppUserRepository userRepository;

    public void likePost(String userId, String postId) {
        Optional<AppUser> userOpt = userRepository.findById(userId);
        Optional<Post> postOpt = postRepository.findById(postId);

        if (userOpt.isPresent() && postOpt.isPresent()) {
            Post post = postOpt.get();
            post.getLikedBy().add(userOpt.get());

            postRepository.save(post);
        } else throw new GenericException("Post not found");

    }

    public void unlikePost(String userId, String postId) {
        Optional<AppUser> userOpt = userRepository.findById(userId);
        Optional<Post> postOpt = postRepository.findById(postId);

        if (userOpt.isPresent() && postOpt.isPresent()) {
            Post post = postOpt.get();
            post.getLikedBy().remove(userOpt.get());

            postRepository.save(post);
        } else throw new GenericException("Post not found");

    }
}
