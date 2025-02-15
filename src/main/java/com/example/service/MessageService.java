package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;


@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    private static Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer id){
        LOGGER.info("Fetching message with ID: {}", id);
        Optional<Message> optmessage = messageRepository.findById(id);
        return optmessage.orElse(null);

    }


    public Message saveMessage(Message message){
        LOGGER.info("saving the message: {}", message); 
        return messageRepository.save(message);
    }

    public Message updateMessage(Integer id, Message message){
        LOGGER.info("update message with ID: {}", message);
        // Check if the message exists in the database
        if(messageRepository.existsById(id)){
            // Check if the message text is empty
            if (message.getMessageText().isEmpty()) {
                throw new IllegalArgumentException("Message text cannot be empty.");
            }

            // Check if the message text exceeds 255 characters
            if (message.getMessageText().length() > 255) {
                throw new IllegalArgumentException("Message text exceeds the allowable limit.");
            }

            // If the checks pass, save the message and return
            return messageRepository.save(message);
        }
        return null; // If the message doesn't exist, return null

    }

    public Message createMessage(Message message) {
        LOGGER.info("message posted will be added: {}", message);
        return messageRepository.save(message);
    }

    public int deleteMessageById(Integer messageId){
        LOGGER.info("Delete message for ID: {}", messageId);
        return messageRepository.deleteMessageById(messageId);
    }

    public List<Message> getAllMessagesForUser(Integer userId) {
        return messageRepository.findAllByPosted_by(userId);
    }

    


}
