package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {

}
