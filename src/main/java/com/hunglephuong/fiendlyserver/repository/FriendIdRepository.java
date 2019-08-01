package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendIdRepository extends JpaRepository<FriendId, Integer> {

    @Query(nativeQuery = true,
    value = "SELECT id, sender_id, receive_id " +
            "FROM friend " +
            "WHERE sender_id = :userId OR " +
            "receive_id = :userId")
    List<FriendId> findAllNotFriend(
            @Param(value = "userId") int userId
    );
}
