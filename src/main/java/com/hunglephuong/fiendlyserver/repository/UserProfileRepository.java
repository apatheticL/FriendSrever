package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Integer> {
    @Query(nativeQuery = true ,value = "SELECT * FROM user_profile WHERE " +
            "user_name = :username LIMIT 1")
    UserProfile findOneByUsername(@Param(value = "username")String username);


    @Query(nativeQuery = true,value = "INSERT INTO user_profile" +
            "(id,user_name,password,full_name,birthday,sex,avatar,email,phone_number,created_time)" +
            " VALUES (default,:username,:pass,:fullname,:birthday,:sex,:avatar,:email,:phonenumber,default) ")

    UserProfile  insertAccount(@Param(value = "username") String username,
                              @Param(value = "pass") String password,
                              @Param(value = "fullname") String fullname,
                              @Param(value = "birthday") Date birthday,
                              @Param(value = "sex") String sex,
                              @Param(value = "avatar") String avatar,
                              @Param(value = "email") String email,
                              @Param(value = "phonenumber") String phonenumber);

    @Query(nativeQuery = true,
            value = "SELECT * FROM user_profile WHERE id not in :ids")
    List<UserProfile> findAllNotFriend(
            @Param(value = "ids") List<Integer> ids
    );
}
