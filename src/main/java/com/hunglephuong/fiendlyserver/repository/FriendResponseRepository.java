package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.Status;
import com.hunglephuong.fiendlyserver.model.response.FriendResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendResponseRepository extends JpaRepository<FriendResponse, Integer> {
    @Query(nativeQuery = true,
            value = "SELECT " +
                    "friend.id as id, "+
                    "user_profile.id as friend_id, "+
                    "user_profile.full_name as friend_name, "+
                    "user_profile.user_name as friend_username, "+
                    "user_profile.avatar as friend_avatar, "+
                    "friend.created_time as created_time " +
                    "FROM " +
                    "friend JOIN user_profile ON " +
                    "(friend.sender_id = :userId AND friend.receive_id = user_profile.id) OR "+
                    "(friend.receive_id = :userId AND friend.sender_id = user_profile.id) "
    )
    List<FriendResponse> findAllFriend(
            @Param(value = "userId") int userId
    );


}
