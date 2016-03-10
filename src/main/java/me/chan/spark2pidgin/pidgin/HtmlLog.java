package me.chan.spark2pidgin.pidgin;

import java.util.Date;
import java.util.List;
import java.util.Set;

import me.chan.spark2pidgin.AppConstants;
import me.chan.spark2pidgin.spark.Message;

/**
 * Encapsulates the information that is stored in a pidgin html log file. This
 * includes a title, meta, heading and the list of conversations between the
 * owner of the logs and his/her contact. <br>
 * <br>
 * Special html content formatting and filename is needed so that the html log
 * file can be loaded by pidgin.
 * <ul>
 * <li>Filename - must be the date of the conversation in the format specified
 * in the property file.</li>
 * <li>Formatting - first line of the html log file must contain the heading. <br>
 * e.g. <code>&lt;html&gt;...&lt;body&gt;&lt;h3&gt;Heading...&lt/h3&gt;</code></li>
 * <li>Location of the logfile - it must be located in .purple directory. <br>
 * Default value is in %APP_DATA%\.purple\transcripts\owner\jabber
 * client\contact\html logs\</li>
 * </ul>
 * 
 * @author chan
 * 
 */
public class HtmlLog {

	private String meta = "<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">";
	private String title;
	private String heading;
	private List<String> conversation;
	private String owner;
	private Set<Message> messages;
	private String filename;

	private static final String JABBER_CLIENT = AppConstants.APP_CLIENT_NAME;

	/**
	 * @param jabberName
	 *          - jabber id with server name. This is used as part of the html
	 *          title or heading. <br>
	 *          e.g. Conversation with jabberName on ...
	 * @param conversationDate
	 *          - date when the conversation took place. By default, all
	 *          conversations with the same date are consolidated in one
	 *          {@link HtmlLog}.
	 * @param owner
	 *          - name/alias of the owner of the logs. This can be used to
	 *          determine whether the {@link Message} originated from the owner.
	 */
	public HtmlLog(String jabberName, Date conversationDate, String owner) {
		this(jabberName, conversationDate, owner, JABBER_CLIENT);
	}

	/**
	 * @param jabberName
	 *          - jabber id with server name. This is used as part of the html
	 *          title or heading. <br>
	 *          e.g. Conversation with jabberName on ...
	 * @param conversationDate
	 *          - date when the conversation took place. By default, all
	 *          conversations with the same date are consolidated in one
	 *          {@link HtmlLog}.
	 * @param owner
	 *          - name/alias of the owner of the logs. This can be used to
	 *          determine whether the {@link Message} originated from the owner.
	 * @param jabberClient
	 *          - instant messaging software used or alias given to the software. <br>
	 *          e.g. jabber
	 */
	public HtmlLog(String jabberName, Date conversationDate, String owner,
	    String jabberClient) {
		StringBuilder sb = new StringBuilder();
		sb.append("Convesation with ").append(jabberName).append(" at ")
		    .append(AppConstants.Pidgin.DATE_FORMAT_IMPL.format(conversationDate))
		    .append(" on ").append(owner).append("/ (").append(jabberClient)
		    .append(")");
		title = sb.toString();
		heading = sb.toString();
		this.owner = owner;
	}

	/**
	 * @return Title of the html page.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *          - title for the html page
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Heading of the page.
	 */
	public String getHeading() {
		return heading;
	}

	/**
	 * @param heading
	 *          - heading for the html page.
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	/**
	 * @return - the list of conversations
	 */
	public List<String> getConversation() {
		return conversation;
	}

	/**
	 * @param conversation
	 *          - conversations to be added in the html page
	 */
	public void setConversation(List<String> conversation) {
		this.conversation = conversation;
	}

	/**
	 * @return meta for the html page
	 */
	public String getMeta() {
		return meta;
	}

	/**
	 * @return the messages that are added in the html page
	 */
	public Set<Message> getMessages() {
		return messages;
	}

	/**
	 * @param messages
	 *          - the messages to be added to the html page
	 */
	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	/**
	 * @return owner for the conversation
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *          - sets the owner for the conversation
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * @return - returns the filename if the {@link HtmlLog} will be saved. The
	 *         filename is derived from the earliest timestamp of the conversation
	 *         in this {@link HtmlLog}.
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename
	 *          - sets the filename for {@link HtmlLog}. The filename should be
	 *          derived from the earliest timestamp of the conversation in this
	 *          {@link HtmlLog}.
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
