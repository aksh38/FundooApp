package com.api.notes.services;

import com.api.notes.models.Note;

public interface RabbitMqSender {

	void send(Note note);
}
