package com.api.notes.services;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.notes.exception.NoteException;
import com.api.notes.models.Note;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class RabbitMqSenderImpl implements RabbitMqSender {

	@Autowired
	private RabbitTemplate template;

	@Override
	public void send(Note note) {
		try {
		template.convertAndSend("notes.exchange","notes.routingKey", note);
		System.out.println("message : "+note);
		}
		catch (AmqpException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			throw new NoteException(e.getMessage());
		}
	}

}
