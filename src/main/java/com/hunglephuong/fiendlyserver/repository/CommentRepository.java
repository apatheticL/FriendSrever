package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.response.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Component
@Transactional
public interface CommentRepository extends JpaRepository<CommentResponse,Integer> {
    @Query(nativeQuery = true ,
            value = "select comment.id, comment.status_id,comment.user_id,user_profile.avatar," +
                    "comment.content,user_profile.full_name,comment.created_time  " +
                    "from comment join status  on comment.status_id = status.id " +
                    "join user_profile on comment.user_id = user_profile.id " +
                    "where comment.status_id = :statusid " +
                    "order by comment.created_time asc ")
    List<CommentResponse> getAllCommentByStatus(@Param(value = "statusid") int statusid);
    @Modifying
    @Query(nativeQuery = true, value =
            " insert into  comment (id,user_id,status_id,content,created_time )" +
                    "values (default, :userid,:statusid,:content,default )")
    @Transactional
    void insertStatus(@Param(value = "userid") int userid,
                      @Param(value = "statusid") int statusid,
                      @Param(value = "content") String content);

}
