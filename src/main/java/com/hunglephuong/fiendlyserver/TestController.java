package com.hunglephuong.fiendlyserver;

import com.hunglephuong.fiendlyserver.model.*;
import com.hunglephuong.fiendlyserver.repository.FriendIdRepository;
import com.hunglephuong.fiendlyserver.repository.FriendResponseRepository;
import com.hunglephuong.fiendlyserver.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private UserProfileRepository userProfileRepository;

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
    public BaseResponse register(@RequestBody RegisterRequest registerResponse) {

      UserProfile userProfile=    userProfileRepository.insertAccount(registerResponse.getUsername(),
                registerResponse.getPassword(), registerResponse.getFullname(), registerResponse.getBirthday()
                , registerResponse.getSex(),
               registerResponse.getAvatar(), registerResponse.getEmail(),
               registerResponse.getMobile());
        return  BaseResponse.createResponse(userProfile);
    }

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
