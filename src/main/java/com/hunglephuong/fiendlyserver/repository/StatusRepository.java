package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status,Integer> {
    // lay danh sach status cua chinh user đó
    @Query(nativeQuery = true,
    value = "SELECT * FROM status WHERE user_id=:username")
    Status findAllByStatusUser(@Param(value = "username")String username);


}
