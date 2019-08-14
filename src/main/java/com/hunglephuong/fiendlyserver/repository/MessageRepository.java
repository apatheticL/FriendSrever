package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.MessageChatResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageChatResponse,Integer> {
//    @Query(nativeQuery = true,
//    value = "SELECT message.id as id," +
//            "message.sender_id" +
//            "message.recieve_id" +
//            "message.content" +
//            "FROM message WHERE " +
//            "(message.sender_id = :sender) and (message.recieve_id = :reveiver)")
//    List<MessageChatResponse> selectMessage(@Param(value = "sender_id") int sender, @Param(value = "receiver_id") int receiver);

    @Modifying
    @Query(nativeQuery = true, value =
    "insert  into message (id,sender_id,recieve_id,content,type,is_delete,created_time,is_read)" +
            "value (default , :senderId, :recieveId, :content, default ,default , default ,default )")
    @Transactional
    void insertMessage(@Param(value = "senderId")int senderId, @Param(value = "recieveId") int recieveId, @Param(value = "content") String content);
}
