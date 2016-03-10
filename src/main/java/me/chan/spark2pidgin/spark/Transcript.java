package me.chan.spark2pidgin.spark;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import me.chan.spark2pidgin.AppConstants;

/**
 * Messages under a specific contact grouped by date.
 * 
 */
public class Transcript {

	private String jabberName;
	// key: date formatted string
	private Map<String, TranscriptMessages> messages;

	/**
	 * Instantiates a {@link Transcript} for the contact.
	 * 
	 * @param jabberName
	 *          - contact name
	 */
	public Transcript(String jabberName) {
		this.jabberName = jabberName;
		this.messages = new LinkedHashMap<String, TranscriptMessages>();
	}

	/**
	 * Instantiates a {@link Transcript} for the contact and the messages.
	 * 
	 * @param jabberName
	 *          - contact name
	 * @param messages
	 *          - messages
	 */
	public Transcript(String jabberName, Map<String, TranscriptMessages> messages) {
		super();
		this.jabberName = jabberName;
		this.messages = messages;
	}

	public String getJabberName() {
		return jabberName;
	}

	public void setJabberName(String jabberName) {
		this.jabberName = jabberName;
	}

	public Map<String, TranscriptMessages> getMessages() {
		return messages;
	}

	public void setMessages(Map<String, TranscriptMessages> messages) {
		this.messages = messages;
	}

	/**
	 * Adds the message under a date key. All messages are grouped per date.
	 * 
	 * @param dateStrKey
	 *          - string format of the message date (without time component)
	 * @param messages
	 *          - messages under the specific date
	 */
	public void addTranscriptMessage(String dateStrKey,
	    TranscriptMessages messages) {
		if (!this.messages.containsKey(dateStrKey)) {
			this.messages.put(dateStrKey, messages);
		} else {
			this.messages.get(dateStrKey).getMessages()
			    .addAll(messages.getMessages());
		}
	}

	/**
	 * Takes a set of messages and groups them per date.
	 * 
	 * @param messages
	 *          - messages to be grouped per date
	 */
	public void processTranscripts(Set<Message> messages) {
		Iterator<Message> msgItr = messages.iterator();

		while (msgItr.hasNext()) {

			Message msg = msgItr.next();
			Date msgDate = msg.getDate();

			String dateStrKey = AppConstants.DATE_FORMAT_SIMPLE_IMPL.format(msgDate);

			TranscriptMessages tMsgs = this.messages.get(dateStrKey);

			if (tMsgs == null) {
				tMsgs = new TranscriptMessages(msgDate);
				this.messages.put(dateStrKey, tMsgs);
			}

			tMsgs.addMessage(msg);

		}
	}
}
