package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.response.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Component
@Transactional
public interface CommentRepository extends JpaRepository<CommentResponse,Integer> {
    @Query(nativeQuery = true , value = "call getAllCommentByStatus(:statusid, :userid)")
    List<CommentResponse> getAllCommentByStatus(@Param(value = "statusid") int statusid, @Param(value = "userid") int userid);
}
