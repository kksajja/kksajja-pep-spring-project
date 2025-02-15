package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

    Account createAccount(Account account);
    Account findByUsername(String username);
   
    @Query("INSERT into account a(username, password) VALUES (?, ?)")
    public Account insertAccount(String username, String password);
    public Optional<Account> validateLogin(String username, String password);
    
    public void validateAccount(Account account);
   
    public boolean doesUsernameExist(String username);
}
