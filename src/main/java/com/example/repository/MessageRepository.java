package com.example.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

    
    @Transactional
    @Modifying
    @Query("DELETE FROM message m WHERE m.id = ?1")
    int deleteMessageById(Integer messageId);

    @Query("SELECT m FROM message m WHERE m.posted_by = ?1")
    List<Message> findAllByPostedby(Integer postedby);
    
    
}
