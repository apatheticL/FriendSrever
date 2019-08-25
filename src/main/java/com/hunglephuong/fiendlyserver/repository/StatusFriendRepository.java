package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.Status;
import com.hunglephuong.fiendlyserver.model.response.StatusFriendRespomse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatusFriendRepository extends JpaRepository<StatusFriendRespomse, Integer> {
    //  lay danh sach status cua banj cua user do

    @Query(nativeQuery = true, value =
            "SELECT status.id as id," +
                    "(select count(*) from comment where comment.status_id = status.id) as number_comment ,status.number_like," +
                    "user_profile.avatar as avatar_friend, " +
                    "status.number_share," +
                    "status.content," +
                    "status.created_time ," +
                    "status.attachments,"+
                    "user_profile.id as user_id," +
                    "user_profile.full_name as friend_full_name " +
                    "FROM  user_profile JOIN friend  ON " +
                    "( (friend.sender_id = :iduser AND friend.receiver_id = user_profile.id) OR " +
                    " (friend.receiver_id = :iduser AND friend.sender_id = user_profile.id) )" +
                    "   JOIN status on status.user_id = user_profile.id")
    List<StatusFriendRespomse> findAllStatusFriend(@Param(value = "iduser") int iduser);

    @Query(nativeQuery = true,
            value = "SELECT status.id as id," +
                    " (select count(*) from comment where comment.status_id = status.id) as number_comment " +
                    ",status.number_like," +
                    " user_profile.avatar as avatar_friend," +
                    " status.number_share," +
                    " status.content," +
                    " status.created_time ," +
                    " status.attachments," +
                    "  status.user_id," +
                    "user_profile.full_name as friend_full_name FROM" +
                    " user_profile join status on status.user_id = user_profile.id" +
                    " WHERE status.user_id in :ids" +
                    " order by status.created_time DESC")
    List<StatusFriendRespomse> findAllStatusOfFriend(
            @Param(value = "ids") List<Integer> ids
    );


}
