package com.hunglephuong.fiendlyserver;

import com.hunglephuong.fiendlyserver.model.*;
<<<<<<< HEAD
import com.hunglephuong.fiendlyserver.repository.StatusRepository;
=======
import com.hunglephuong.fiendlyserver.repository.FriendIdRepository;
import com.hunglephuong.fiendlyserver.repository.FriendResponseRepository;
>>>>>>> e26754ad821f7f7221725eee1e835f3a28e143c8
import com.hunglephuong.fiendlyserver.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> e26754ad821f7f7221725eee1e835f3a28e143c8

@RestController
public class TestController {
    @Autowired
    private UserProfileRepository userProfileRepository;
    private StatusRepository statusRepository;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private Date date;

    @Autowired
    private FriendResponseRepository friendResponseRepository;

    @Autowired
    private FriendIdRepository friendIdRepository;
    @PostMapping(value = "/login")
    public BaseResponse login(@RequestBody LoginRequest loginRequest) {
        UserProfile userProfile = userProfileRepository.findOneByUsername(loginRequest.getUsername());
        if (userProfile == null || !userProfile.getPassword().equals(loginRequest.getPassword())) {
            return BaseResponse.createResponse(0, "username or password invalid ");
        }
        return BaseResponse.createResponse(userProfile);
    }

    @PostMapping(value = "/register")
    public BaseResponse  register(@RequestBody RegisterRequest registerResponse) {

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
    @PostMapping(value = "/getStatusByUsername")
    public BaseResponse getStatusByUsername(@RequestBody  StatusRequest statusRequest){
//
    }

<<<<<<< HEAD

=======
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
>>>>>>> e26754ad821f7f7221725eee1e835f3a28e143c8

}
