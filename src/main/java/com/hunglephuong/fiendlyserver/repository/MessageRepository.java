package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.response.MessageChatResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageChatResponse,Integer> {
    @Query(nativeQuery = true,
    value = "SELECT message.id as id," +
            "message.sender_id," +
            "message.receiver_id," +
            "message.type," +
            "message.content " +
            "FROM  message  WHERE  " +
            "( message.sender_id = :senderid ) and (message.receiver_id = :receiverid)")
    List<MessageChatResponse> selectMessage(@Param(value = "senderid") int senderid,
                                            @Param(value = "receiverid") int receiverid
    );
}
