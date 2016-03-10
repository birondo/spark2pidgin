package me.chan.spark2pidgin.spark;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A set of messages with the same date (without time component).
 * 
 */
public class TranscriptMessages {

	private Date date;
	private Set<Message> messages;

	public TranscriptMessages(Date date) {
		this.date = date;
		this.messages = new LinkedHashSet<Message>();
	}

	public TranscriptMessages(Date date, Set<Message> messages) {
		this.date = date;
		this.messages = messages;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	public void addMessage(Message message) {
		this.messages.add(message);
	}

}
