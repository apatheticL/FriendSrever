package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.response.StatusFriendRespomse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatusFriendRepository extends JpaRepository<StatusFriendRespomse, Integer> {
    //  lay danh sach status cua banj cua user do

    @Query(nativeQuery = true, value =
            "SELECT status.id as id," +
                    "status.number_comment,status.number_like," +
                    "user_profile.avatar as avatar_friend, " +
                    "status.number_share,status.content," +
                    "status.created_time ," +
                    "status.attachments,"+
                    "user_profile.id as user_id," +
                    "user_profile.full_name as friend_full_name " +
                    "FROM  user_profile JOIN friend  ON " +
                    "( (friend.sender_id = :iduser AND friend.receive_id = user_profile.id) OR " +
                    " (friend.receive_id = :iduser AND friend.sender_id = user_profile.id) )" +
                    "   JOIN status on status.user_id = user_profile.id")
    List<StatusFriendRespomse> findAllStatusFriend(@Param(value = "iduser") int iduser);

}
