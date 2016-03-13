package me.chan.spark2pidgin.spark;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXParseException;

/**
 * Handles the parsing of all xml files under the spark transcript directory.
 * Parsed conversations are loaded in memory. Contact information (jabber
 * name/alias) is taken from the filename.
 * <p>
 * Unparseable transcripts are skipped.
 * 
 */
public class TranscriptParserManager {

	private static final Logger LOG = LoggerFactory
	    .getLogger(TranscriptParserManager.class);

	private String logDirectory;
	private Map<String, Transcript> transcripts;

	/**
	 * Creates a {@link TranscriptParserManager} which reads the logs from the
	 * given directory.
	 */
	public TranscriptParserManager(String logDirectory) {
		this.logDirectory = logDirectory;
		this.transcripts = new HashMap<String, Transcript>();
	}

	/**
	 * Parses all xml files under the transcript directory.
	 * 
	 * @throws Exception
	 */
	public void parseAll() throws Exception {
		if (LOG.isInfoEnabled()) {
			LOG.info("Parsing logs from " + logDirectory);
		}
		File dir = new File(logDirectory);
		if (dir.isDirectory()) {
			File[] files = dir.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String filename) {
					return filename.endsWith(".xml");
				}
			});

			for (File file : files) {
				try {
					if (LOG.isDebugEnabled()) {
						LOG.debug("Processing log file " + file.getName());
					}
					TranscriptParser parser = new TranscriptParser(file);
					Set<Message> messages = parser.parse();
					String jabberContact = resolveJabberContact(file.getName());
					addTranscript(jabberContact, messages);
				} catch (SAXParseException sax) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(sax.getMessage(), sax);
					} else if (LOG.isErrorEnabled()) {
						LOG.error(sax.getMessage());
					}
				}
			}

		}
	}

	/**
	 * Substrings the filename to get the contact jabber name/alias. Jabber may
	 * create a short separate transcript file for current conversation which ends
	 * with "_current.xml". If this file is found jabber name will be resolved by
	 * removing the suffix.
	 * 
	 * @param filename
	 *          - filename of the transcript file
	 * @return
	 */
	protected String resolveJabberContact(String filename) {
		return filename.replace(".xml", "").replace("_current", "");
	}

	/**
	 * Adds the set of {@link Message} from the transcript to the map of
	 * {@link Transcript} object identified by contact name.
	 * 
	 * @param jabberContact
	 * @param messages
	 */
	protected void addTranscript(String jabberContact, Set<Message> messages) {
		Transcript transcript = this.transcripts.get(jabberContact);
		if (transcript == null) {
			transcript = new Transcript(jabberContact);
			this.transcripts.put(jabberContact, transcript);
		}
		transcript.processTranscripts(messages);
	}

	public String getLogDirectory() {
		return logDirectory;
	}

	public void setLogDirectory(String logDirectory) {
		this.logDirectory = logDirectory;
	}

	public Map<String, Transcript> getTranscripts() {
		return transcripts;
	}

}
