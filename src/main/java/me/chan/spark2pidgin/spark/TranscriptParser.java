package me.chan.spark2pidgin.spark;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import me.chan.spark2pidgin.AppConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Parses a given Spark transcript file and builds a set of {@link Message}.
 * 
 */
public class TranscriptParser {

	private File file;

	public TranscriptParser(File file) {
		super();
		this.file = file;
	}

	/**
	 * Returns a set of Messages containing the parsed conversation from the
	 * transcript file.
	 * 
	 * @return a set of {@link Message} containing the parsed conversation
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Set<Message> parse() throws ParserConfigurationException,
	    SAXException, IOException, ParseException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setValidating(false);
		factory.setIgnoringComments(true);
		DocumentBuilder bld = factory.newDocumentBuilder();

		Document doc = bld.parse(file);
		NodeList messages = doc.getElementsByTagName("message");
		return parseMessages(messages);
	}

	/**
	 * Parses the <code>message</code> element of the transcript file.
	 * 
	 * @param messages
	 *          - the node list of <code>message</code> tags
	 * @return a set of {@link Message} containing the parsed conversation
	 * @throws ParseException
	 */
	protected Set<Message> parseMessages(NodeList messages) throws ParseException {
		Set<Message> messageSet = new LinkedHashSet<Message>();
		if (messages != null) {
			for (int x = 0; x < messages.getLength(); x++) {
				Node msg = messages.item(x);
				if (Element.class.isAssignableFrom(msg.getClass())) {
					Element elem = (Element) msg;
					String to = getFirstElemText(elem.getElementsByTagName("to"));
					String from = getFirstElemText(elem.getElementsByTagName("from"));
					String body = getFirstElemText(elem.getElementsByTagName("body"));
					String date = getFirstElemText(elem.getElementsByTagName("date"));
					Message message = new Message(to, from, body,
					    AppConstants.Spark.DATE_FULL_IMPL.parse(date));
					messageSet.add(message);
				}
			}
		}
		return messageSet;
	}

	/**
	 * Returns the text content of the first element of the list, if the list is
	 * not null.
	 * 
	 * @param list
	 *          - a list of nodes, can be null
	 * @return the text of the first element in the list
	 */
	protected String getFirstElemText(NodeList list) {
		String text = null;
		if (list != null) {
			text = list.item(0).getTextContent();
		}
		return text;
	}

}
