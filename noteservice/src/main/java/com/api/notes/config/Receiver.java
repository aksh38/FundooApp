package com.api.notes.config;

import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Component;

import com.api.notes.models.Note;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(Note message) {
        
    	System.out.println("Recieved Message "+message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
	
