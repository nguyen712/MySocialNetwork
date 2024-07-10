package com.demospring.socialnetwork.service.implservice;

import com.demospring.socialnetwork.dto.request.PostActionRequest;
import com.demospring.socialnetwork.dto.request.PostRequest;
import com.demospring.socialnetwork.dto.request.UpdatePostRequest;
import com.demospring.socialnetwork.dto.response.PostResponse;
import com.demospring.socialnetwork.entity.Action;
import com.demospring.socialnetwork.entity.User;
import com.demospring.socialnetwork.repository.ActionRepository;
import com.demospring.socialnetwork.repository.FriendRepository;
import com.demospring.socialnetwork.repository.PostRepository;
import com.demospring.socialnetwork.repository.UserRepository;
import com.demospring.socialnetwork.service.iservice.IPostService;
import com.demospring.socialnetwork.util.enums.EnumAction;
import com.demospring.socialnetwork.util.enums.RelationShipStatus;
import com.demospring.socialnetwork.util.exception.AppException;
import com.demospring.socialnetwork.util.exception.ErrorCode;
import com.demospring.socialnetwork.util.mapper.PostMapper;
import com.demospring.socialnetwork.util.message.Message;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PostService implements IPostService {
    PostMapper postMapper;
    UserRepository userRepository;
    PostRepository postRepository;
    ActionRepository actionRepository;
    FriendRepository friendRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostResponse createPost(PostRequest postRequest) throws Exception {
        var context = SecurityContextHolder.getContext();
        var name = context.getAuthentication().getName();
        var user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        try {
            var post = postMapper.toPost(postRequest);
            post.setCreatedBy(user.getFirstName() + " " + user.getLastName());
            post.setUser(user);
            post.setCreatedDate(new Date());
            postRepository.save(post);
            return postMapper.toPostResponse(post);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public PostResponse updatePost(UpdatePostRequest postRequest) throws Exception {
        var context = SecurityContextHolder.getContext();
        var name = context.getAuthentication().getName();
        var user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        var post = postRepository.findById(postRequest.getPostId()).orElseThrow(() -> new AppException(ErrorCode.POST_IS_NOT_EXISTED));
        if(!user.getId().equals(post.getUser().getId())) throw new AppException(ErrorCode.USER_IS_NOT_A_AUTHOR_OF_POST);
        postMapper.UpdatePost(post, postRequest.getPostRequest());
        postRepository.save(post);
        return postMapper.toPostResponse(post);
    }

    /**
     * This is an implementation of "Like or Dislike" action for the post.
     * @param actionRequest contains which button is pressed by user, postId
     * if(user press duplicate action "like or dislike") throw AppException
     * if(query action empty) add action and update post table
     * if(query action is not match two case above) update quantity in post entity
     *                      and update action field in action entity.
     * @return String message for user.
     * @throws AppException when "save()" action to post table or action table.
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String actionWithPost(PostActionRequest actionRequest) {
        var context = SecurityContextHolder.getContext();
        var name = context.getAuthentication().getName();
        var user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        var post = postRepository.findById(actionRequest.getPostId()).orElseThrow(() -> new AppException(ErrorCode.POST_IS_NOT_EXISTED));
        var action = actionRepository.findByUsername(user.getUsername(), actionRequest.getAction().name());

        //If(duplicate action)
        if (action.isPresent() && action.get().getAction().equals(actionRequest.getAction().name()))
            throw new AppException(ErrorCode.DUPLICATE_ACTION);
        List<Action> actionList = new ArrayList<>();

        //If(this is the first request)
        if (action.isEmpty()) {
            Action newAction = new Action();
            newAction.setUser(user);
            newAction.setPost(post);
            newAction.setAction(actionRequest.getAction().name());
            try {
                actionRepository.save(newAction);
            } catch (Exception e) {
                log.error("Can not insert to action: {}", e.getMessage());
                throw new AppException(ErrorCode.ADD_UNSUCCESSFULLY);
            }
            actionList.add(newAction);

            switch (actionRequest.getAction()) {
                case EnumAction.LIKE:
                    post.setQuantityOfLike(post.getQuantityOfLike() + 1);
                    break;
                case EnumAction.DISLIKE:
                    post.setQuantityOfDislike(post.getQuantityOfDislike() + 1);
                    break;
            }
            post.setActions(actionList);

        }else{ //If(User want to change action "like or dislike" to the post)
            action.get().setAction(actionRequest.getAction().name());
            switch (actionRequest.getAction()) {
                case EnumAction.LIKE:
                    post.setQuantityOfLike(post.getQuantityOfLike() - 1);

                    break;
                case EnumAction.DISLIKE:
                    post.setQuantityOfDislike(post.getQuantityOfDislike() - 1);
                    break;
            }
            try {
                actionRepository.save(action.get());
            } catch (Exception e) {
                log.error("Can not insert to action: {}", e.getMessage());
                throw new AppException(ErrorCode.ADD_UNSUCCESSFULLY);
            }
        }

        try {
            postRepository.save(post);
            return Message.SuccessMessage.ADD_SUCCESSFULLY;
        } catch (Exception e) {
            log.error("Can not update to post: {}", e.getMessage());
            throw new AppException(ErrorCode.ADD_UNSUCCESSFULLY);
        }
    }

    @Override
    public List<PostResponse> getAllAvailablePosts() {
        var context = SecurityContextHolder.getContext();
        var name = context.getAuthentication().getName();
        var user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        var friend = friendRepository.getAllFriendsByUserId(user.getId());
        var listUserId = new ArrayList<String>();
        friend.forEach(friendship -> {
                    if (friendship.getUser().getId().equals(user.getId())) {
                        listUserId.add(friendship.getUser().getId());
                    } else {
                        listUserId.add(friendship.getFriend().getId());
                    }
                });
        var posts = postRepository.findAllByUserId(listUserId);
        if(posts.isEmpty()) return new ArrayList<>();
        return postMapper.toPostResponses(posts);
    }

}
