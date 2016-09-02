package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.model.Value;

@Service
public class WebSocketMessanger  {

    @Autowired
    private SimpMessagingTemplate   template;

    @Async
    public void sendConfCallMessage(String destinationQueue, Integer peerId) {
        int i;
        for (i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
            this.template.convertAndSend("/queue/" + destinationQueue, new Value(peerId + "" + i));
        }

    }
}