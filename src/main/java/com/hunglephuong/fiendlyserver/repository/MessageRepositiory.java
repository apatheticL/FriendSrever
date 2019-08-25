package com.hunglephuong.fiendlyserver.repository;

import com.hunglephuong.fiendlyserver.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepositiory extends JpaRepository<Messages, Integer> {
}
