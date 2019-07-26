package com.hunglephuong.fiendlyserver;

import com.hunglephuong.fiendlyserver.model.BaseResponse;
import com.hunglephuong.fiendlyserver.model.LoginRequest;
import com.hunglephuong.fiendlyserver.model.RegisterResponse;
import com.hunglephuong.fiendlyserver.model.UserProfile;
import com.hunglephuong.fiendlyserver.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class TestController {
    @Autowired
    private UserProfileRepository userProfileRepository;
    @PostMapping(value = "/login")
    public BaseResponse login(@RequestBody LoginRequest loginRequest){
        UserProfile userProfile = userProfileRepository.findOneByUsername(loginRequest.getUsername());
        if (userProfile==null||!userProfile.getPassword().equals(loginRequest.getPassword())){
            return BaseResponse.createResponse(0,"username or password invalid ");
        }
        return BaseResponse.createResponse(userProfile);
    }
    @PostMapping(value = "/register")
    public BaseResponse register(@RequestBody RegisterResponse registerResponse){
        UserProfile userProfile = userProfileRepository.insertAccount(registerResponse.getUsername(),
                registerResponse.getPassword(),registerResponse.getFullname(),registerResponse.getBirthday(),
                registerResponse.getSex(),registerResponse.getAvatar(),registerResponse.getEmail(),registerResponse.getMobile());
        if (userProfile.getUsername().equals(registerResponse.getUsername())){
            return BaseResponse.createResponse(0,"username  existed ");
        }
        return BaseResponse.createResponse(userProfile);
    }

}
