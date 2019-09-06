package com.hunglephuong.fiendlyserver;
import com.hunglephuong.fiendlyserver.model.*;
import com.hunglephuong.fiendlyserver.model.response.*;
import com.hunglephuong.fiendlyserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import sun.plugin.util.UserProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;


@RestController
public class TestController {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private StatusRepository statusRepository;
    private Status status = new Status();
    @Autowired
    private StatusFriendRepository statusFriendRepository;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Date date;
    private UserProfile userProfile = new UserProfile() ;


    @Autowired
    private FriendResponseRepository friendResponseRepository;

    @Autowired
    private FriendIdRepository friendIdRepository;
    @Autowired
    private CommentRepository commentRepository;
    // phan start

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping(value = "/login")
    public BaseResponse login(@RequestBody LoginResponse loginRequest) {
        userProfile = userProfileRepository.findOneByUsername(loginRequest.getUsername());

        if (userProfile == null || !userProfile.getPassword().equals(loginRequest.getPassword())) {
            return BaseResponse.createResponse(0, "username or password invalid ");
        }
        return BaseResponse.createResponse(userProfile);
    }

    @PostMapping(value = "/register")
    public BaseResponse  register(@RequestBody RegisterResponse registerResponse) {

        UserProfile userProfile = new UserProfile();
        userProfile.setUsername(registerResponse.getUsername());
        userProfile.setPassword(registerResponse.getPassword());
        userProfile.setFullname(registerResponse.getFullname());
        userProfile.setBirthday(registerResponse.getBirthday());
        userProfile.setSex(registerResponse.getSex());
        userProfile.setAvatar(registerResponse.getAvatar());
        userProfile.setEmail(registerResponse.getEmail());
        userProfile.setPhoneNumber(registerResponse.getMobile());
        userProfile.setCreatedTime(registerResponse.getCreatedtime());
        try {
            date = dateFormat.parse( userProfile.getBirthday());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (userProfile.getUsername().equals("")|| userProfile.getPassword().equals("")) {
            BaseResponse.createResponse(0,"username and password not null");
        }
        UserProfile userProfile1 = userProfileRepository.findOneByUsername(userProfile.getUsername());
        if (userProfile1==null) {
            userProfileRepository.insertAccount(userProfile.getUsername(), userProfile.getPassword(),
                    userProfile.getFullname(), date, userProfile.getSex(), userProfile.getAvatar(), userProfile.getEmail(),
                    userProfile.getPhoneNumber());
        }
        else {
            BaseResponse.createResponse(0,"username consist ");
        }
        return BaseResponse.createResponse(userProfile);
    }

    @PostMapping(value = "/forgotPassword")
    public BaseResponse forgotPassword(@RequestParam String newPassword,@RequestParam int userId){
        userProfile.setId(userId);
        userProfile.setPassword(newPassword);
        if (newPassword.equals("")){
            BaseResponse.createResponse(0,"Invalid password ");
        }
        userProfileRepository.updatePassWord(newPassword,userId);
        return BaseResponse.createResponse(userProfile);
    }


    // phan status
    @GetMapping(value = "/getOneStatusById")
    public BaseResponse getOneStatusById(@RequestParam int id){
        status = statusRepository.findOneByStatusId(id);
        if (status==null){
            BaseResponse.createResponse(0,"not found");
        }
        return BaseResponse.createResponse(status);
    }
    @GetMapping(value = "/getStatusByUser")
    public Object getStatusByUser(@RequestParam int Id ){
        return statusRepository.findAllStatusUser(Id);
    }

    @PostMapping(value = "/updateNumberLikeByUser")
    public BaseResponse updateNumberLikeByUser(@RequestParam int newNumberLike,@RequestParam int statusId){
        status.setNumberLike(newNumberLike);
        if (status.getId()!=statusId){
            BaseResponse.createResponse(0,"failed to update");
        }
        else {
            statusRepository.updateNumberLike(status.getNumberLike(),statusId);
        }
        return BaseResponse.createResponse(status);

    }

//    @PostMapping(value = "/updateNumberCommentByUser")
//    public BaseResponse updateNumberCommentByUser(@RequestParam int newNumberComment,@RequestParam int statusId){
//        status.setNumberLike(newNumberComment);
//        if (status.getId()!=statusId){
//            BaseResponse.createResponse(0,"failed to update");
//        }
//        else {
//            statusRepository.updateNumberComment(status.getNumberComment(),statusId);
//        }
//        return BaseResponse.createResponse(status);

    //    }
    @PostMapping(value = "/shareStatusByUser")
    public BaseResponse shareStatusByUser(@RequestBody StatusResponse statusResponse){
        Status status =new Status();
        status.setUserId(statusResponse.getUserId());//
        status.setId(statusResponse.getId());
        status.setContent(statusResponse.getContent());
        status.setNumberLike(statusResponse.getNumberLike());
        status.setNumberComment(statusResponse.getNumberComment());
        status.setNumberShare(statusResponse.getNumberComment());
        status.setCreatedTime(statusResponse.getCreateTime());
        status.setAttachments(statusResponse.getAttachments());
        if (statusResponse.getContent().equals("")&&statusResponse.getAttachments().equals("")){
            BaseResponse.createResponse(0,"share status error");
        }
        else {
            statusRepository.insertStatus(status.getUserId(),status.getContent(),status.getAttachments());
            int newNumber = status.getNumberShare() +1;
            statusRepository.updateNumberShare(newNumber,status.getId());
        }
        return BaseResponse.createResponse(status);
    }
    // them status by user

    @PostMapping (value = "/insertStatus")
    public BaseResponse insertStatus(@RequestBody StatusResponse statusResponse){
        Status status =new Status();
        status.setUserId(statusResponse.getUserId());//
        status.setId(statusResponse.getId());
        status.setContent(statusResponse.getContent());
        status.setNumberLike(statusResponse.getNumberLike());
        status.setNumberComment(statusResponse.getNumberComment());
        status.setNumberShare(statusResponse.getNumberComment());
        status.setCreatedTime(statusResponse.getCreateTime());
        status.setAttachments(statusResponse.getAttachments());
        if (statusResponse.getContent().equals("")&&statusResponse.getAttachments().equals("")){
            BaseResponse.createResponse(0,"add status error");
        }
        else {
            statusRepository.insertStatus(status.getUserId(),status.getContent(),status.getAttachments());
        }
        return BaseResponse.createResponse(status);

    }

    @GetMapping(value = "/getStatusByFriendUser/{id}")
    public Object getStatusByFriendUser(@PathVariable(value ="id" ) int userId){
        return statusFriendRepository.findAllStatusFriend(userId);
    }

    @PostMapping(value = "/deleteStatusById")
    public Object deleteStatusById(@RequestParam int id){
        return statusRepository.deleteByIdNative(id);
    }
//
//    @GetMapping(value = "/getNumberLike")
//    public int getNumberLike(@RequestParam(value = "id") int id){
//        return statusRepository.getNumberLike(id);
//    }
//
//
//    @GetMapping(value = "/getNumberComment")
//    public int getNumberComment(@RequestParam(value = "id") int id){
//        return statusRepository.getNumberComment(id);
//    }
//
//    @GetMapping(value = "/getNumberShare")
//    public int getNumberShare(@RequestParam(value = "id") int id){
//        return statusRepository.getNumberShare(id);
//    }



    //    @PostMapping(value = "/updateNumberLikeByStatusFriend")
//    public BaseResponse updateNumberLikeByStatusFriend(@RequestParam int newNumberLike,@RequestParam int statusId){
//
//        statusFriendRepository.updateNumberLikeByStatusFriend(newNumberLike,statusId);
//    }
//
//    @PostMapping(value = "/updateNumberShareByStatusFriend")
//    public void updateNumberShareByStatusFriend(@RequestParam int newNumberShare,@RequestParam int statusId){
//        statusFriendRepository.updateNumberShareByStatusFriend(newNumberShare,statusId);
//    }
    // comment
    @GetMapping(value = "/getAllCommentByStatus")
    public Object getAllCommentByStatus(@RequestParam int statusid){
        return commentRepository.getAllCommentByStatus(statusid);
    }

    @PostMapping(value = "/insertComment")
    public BaseResponse insertComment(@RequestBody CommentResponse commentResponse){
        Comment comment = new Comment();
        comment.setId(commentResponse.getId());
        comment.setUserId(commentResponse.getUserId());
        comment.setContent(commentResponse.getContent());
        comment.setStatusId(commentResponse.getStatusId());
        comment.setCreatedTime(commentResponse.getCreatedTime());
        if (commentResponse.getContent().equals("")){
            BaseResponse.createResponse(0,"add comment error");
        }
        else {
            commentRepository.insertStatus(comment.getUserId(),comment.getStatusId(),comment.getContent());
            status.setNumberComment(status.getNumberComment()+1);
            statusRepository.updateNumberComment(status.getNumberComment(),comment.getStatusId());
        }
        return BaseResponse.createResponse(comment);
    }
    //phan friend

    @GetMapping(value = "/getAllUser")
    public Object getAllUser(){
        return userProfileRepository.findAll();
    }

    @GetMapping(value = "/getAllFriend")
    public Object getAllFriend(
            @RequestParam int id
    ){
        return friendResponseRepository.findAllFriend(id);
    }


    @GetMapping(value = "/getAllNotFriend")
    public Object getAllNotFriend(
            @RequestParam int id
    ){
        List<FriendId> friendIds=
                friendIdRepository.findAllNotFriend(id);
        List<Integer> fIds = new ArrayList<>();
        fIds.add(id);
        for (FriendId friendId : friendIds) {
            if (friendId.getReceiverId() == id){
                fIds.add(friendId.getSenderId());
            }else {
                fIds.add(friendId.getReceiverId());
            }
        }
        return
                userProfileRepository.findAllNotFriend(fIds);

    }


    //messager
    @GetMapping(value = "/selectMessage")
    public Object selectMessage(@RequestParam int senderId,@RequestParam int receiverId ){
        return messageRepository.selectMessage(senderId,receiverId);
    }

    @GetMapping(value = "/getStatus")
    public Object getStatus(
            @RequestParam int id
    ){
        List<FriendId> friendIds=
                friendIdRepository.findAllNotFriend(id);
        List<Integer> fIds = new ArrayList<>();
        fIds.add(id);
        for (FriendId friendId : friendIds) {
            if (friendId.getReceiverId() == id){
                fIds.add(friendId.getSenderId());
            }else {
                fIds.add(friendId.getReceiverId());
            }
        }
        return
                statusFriendRepository.findAllStatusOfFriend(fIds);

    }
}
