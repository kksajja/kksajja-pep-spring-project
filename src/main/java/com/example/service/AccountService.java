package com.example.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
    
    
    private AccountRepository accountRepository;
    private static Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    
    
    public Account createAccount(Account account) throws Exception{
        LOGGER.info("Creating account: {}", account);
        validateAccount(account);
            Account searchedAccount = accountRepository.findByUsername(account.getUsername());
            if (searchedAccount!= null) {
                throw new Exception("Account already exist");
            }
            Account createdAccount = accountRepository.insertAccount(account.getUsername(), account.getPassword());
            LOGGER.info("Created account: {}", createdAccount);
            return createdAccount;
       
    }
    
    
    public Account getAccountById(Integer id){
        LOGGER.info("Fetching account with ID: {}", id);
        Optional<Account> account = accountRepository.findById(id);
        LOGGER.info("Fetched account: {}", account.orElse(null));
        return account.orElse(null);
    }

    public Account getAccountByUsername(String username){
        LOGGER.info("Finding account by username: {}", username);
        Account account = accountRepository.findByUsername(username);
        LOGGER.info("Found account: {}", account);
        return account;
    }

    /**
     * Retrieves all accounts using the AccountRepository.
     *
     * @return List of all accounts.
     */
     public List<Account> getAllAccounts() {
        LOGGER.info("Fetching all accounts");
        List<Account> accounts = accountRepository.findAll();
        LOGGER.info("Fetched {} accounts", accounts.size());
        return accounts;
    } 
    
    
    public Account update(Integer id, Account account){
        LOGGER.info("Updating account: {}", account);
        if(accountRepository.existsById(id)){
            return accountRepository.save(account);
        }
        return null;
    }

    public Account saveAccount(Account account) {
        LOGGER.info("saving account: {}", account);
        return accountRepository.save(account);
    }

    public void delete(Account account){
        LOGGER.info("Deleting account: {}", account);
        if (account.getAccountId() == 0) {
            throw new IllegalArgumentException("Account ID cannot be null");
        }
        accountRepository.delete(account);    
        LOGGER.info("Deleted account: {} . Deletion successful {}", account);
    }

    public Account authenticate(String username, String password) {
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }

    public void validateAccount(Account account) throws Exception{
        LOGGER.info("Validating account: {}", account);
        String username = account.getUsername().trim();
   
        String password = account.getPassword().trim();
            if (username.isEmpty()) {
                throw new Exception("Username cannot be blank");
            }
            if (password.isEmpty()) {
                throw new Exception("Password cannot be empty");
            }

            if (password.length() < 4) {
                throw new Exception("Password must be at least 4 characters long");
            }
            if (accountRepository.doesUsernameExist(account.getUsername())) {
                throw new Exception("The username must be unique");
            }

    }

        
     




   
    
} 

