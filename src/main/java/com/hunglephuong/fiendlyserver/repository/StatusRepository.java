package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.Status;
import com.hunglephuong.fiendlyserver.model.response.StatusResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<StatusResponse,Integer> {

    @Query(nativeQuery = true ,value = "SELECT status.id," +
            "status.user_id,status.content,status.number_like,status.number_share," +
            "status.created_time," +
            "(select count(*) from comment where comment.status_id = status.id) as number_comment," +
            " status.attachments " +
            " FROM status WHERE " +
            "status.id = :statusId LIMIT 1")
    Status findOneByStatusId(@Param(value = "statusId")int statusId);

    // lay danh sach status cua chinh user đó
    @Query(nativeQuery = true,
    value = "SELECT status.id as id," +
            "status.content," +
            "user_profile.full_name as status_user_name," +
            "status.created_time," +
            "status.number_like ," +
             "status.attachments,"+
            "status.number_share ," +
            "user_profile.id as user_id," +
            "status.number_comment  " +
            " FROM status join user_profile on " +
            " (status.user_id = user_profile.id) AND (status.user_id=:idu)")
    List<StatusResponse> findAllStatusUser(@Param(value = "idu")int idu);


    // insert status
     @Modifying
    @Query(nativeQuery = true, value =
    " insert into  status (id,user_id,content,attachments,created_time )" +
            "values (default, :userid, :content,:attachments,default )")
    @Transactional
    void insertStatus(@Param(value = "userid") int userid,
                      @Param(value = "content") String content,
                      @Param(value = "attachments") String attachments);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE status set number_like = :newnumberlike WHERE status.id = :statusid")
    void updateNumberLike(@Param(value = "newnumberlike") int newnumberlike,@Param(value = "statusid") int statusid);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE status set number_share = :newnumbershare WHERE status.id = :statusid")
    void updateNumberShare(@Param(value = "newnumbershare") int newnumbershare,@Param(value = "statusid") int statusid);

}
