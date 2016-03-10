package me.chan.spark2pidgin.spark;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.chan.spark2pidgin.AppConstants;

/**
 * The message as parsed from a line in a spark conversation.
 * 
 */
public class Message {

	/**
	 * Recipient of the message. Can be either the owner of the logs or his/her
	 * contact.
	 */
	private String to;

	/**
	 * Sender of the message. Can be either the owner of the logs or his/her
	 * contact.
	 */
	private String from;

	/**
	 * Content of the message.
	 */
	private String body;

	/**
	 * Timestamp for the message.
	 * <p>
	 * <b>Note:</b> Spark may use CST as timezone for this date. However, Spark
	 * does not properly adjust CST with daylight savings time. When this is
	 * parsed in Java, {@link SimpleDateFormat} may automatically adjust the time
	 * after factoring in the adjustments for DST. This makes the parsed date
	 * different from the actual timestamp of the message. Refer to
	 * <code>app.properties</code> to know how to go around this issue.
	 */
	private Date date;

	public Message(String to, String from, String body, Date date) {
		super();
		this.to = to;
		this.from = from;
		this.body = body;
		this.date = date;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		// Used to ensure no duplicate messages are parsed from spark.
		if (obj instanceof Message) {
			Message pMsg = (Message) obj;
			return this.date.equals(pMsg.getDate())
			    && this.body.equals(pMsg.getBody());
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return this.date.hashCode() + this.body.hashCode();
	}

	/**
	 * @return the time formatted based on pidgin format
	 */
	public String getFormattedTime() {
		return AppConstants.Pidgin.TIME_FULL_FORMAT_IMPL.format(this.date);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// formats the message as one line in the format (ts) from: message
		StringBuilder sb = new StringBuilder();
		sb.append("(")
		    .append(AppConstants.Pidgin.TIME_FULL_FORMAT_IMPL.format(date))
		    .append(") ").append(from).append(": ").append(body);
		return sb.toString();
	}
}
