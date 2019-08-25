package com.hunglephuong.fiendlyserver;
import com.hunglephuong.fiendlyserver.model.*;

import com.hunglephuong.fiendlyserver.repository.StatusRepository;
import com.hunglephuong.fiendlyserver.repository.FriendIdRepository;
import com.hunglephuong.fiendlyserver.repository.FriendResponseRepository;
import com.hunglephuong.fiendlyserver.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hunglephuong.fiendlyserver.model.response.BaseResponse;
import com.hunglephuong.fiendlyserver.model.response.LoginResponse;
import com.hunglephuong.fiendlyserver.model.response.RegisterResponse;
import com.hunglephuong.fiendlyserver.model.response.StatusResponse;
import com.hunglephuong.fiendlyserver.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    private StatusFriendRepository statusFriendRepository;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Date date;
    private UserProfile userProfile = new UserProfile() ;

    @Autowired
    private FriendResponseRepository friendResponseRepository;

    @Autowired
    private FriendIdRepository friendIdRepository;

    // phan start

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

//    @PostMapping(value = "/getStatusByUsername")
//    public BaseResponse getStatusByUsername(@RequestBody  StatusRequest statusRequest){
////
//    }




// phan status
    @GetMapping(value = "/getStatusByUser")
    public Object getStatusByUser(@RequestParam int Id ){
        return statusRepository.findAllStatusUser(Id);
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
        if (statusResponse.getContent().equals("")){
            BaseResponse.createResponse(0,"add status error");
        }
        else {
              statusRepository.insertStatus(status.getUserId(),status.getContent());
        }
        return BaseResponse.createResponse(status);

    }

    @GetMapping(value = "/getStatusByFriendUser")
    public Object getStatusByFriendUser(@RequestParam int idUser){
        return statusFriendRepository.findAllStatusFriend(idUser);
    }



    //phan friend


    @GetMapping(value = "/getAllUser")
    public Object getAllUser(){
        return userProfileRepository.findAll();
    }

    @GetMapping(value = "/getAllFriend")
    public Object getAllFriend(@RequestParam int id){
        return friendResponseRepository.findAllFriend(id);
    }


    @GetMapping(value = "/getAllNotFriend")
    public Object getAllNotFriend(@RequestParam int id){
        List<FriendId> friendIds= friendIdRepository.findAllNotFriend(id);
        List<Integer> fIds = new ArrayList<>();
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


}
