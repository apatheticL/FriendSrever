package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(nativeQuery = true,
            value = "UPDATE friend " +
                    "SET last_message = :lastMessage WHERE " +
            "(receiver_id = :receiverId and sender_id = :senderId ) OR " +
                    "(sender_id = :receiverId and receiver_id = :senderId )")
    void updateLastMessage(
            @Param(value = "lastMessage") String lastMessage,
            @Param(value = "senderId") int senderId,
            @Param(value = "receiverId") int receiverId
    );
}
