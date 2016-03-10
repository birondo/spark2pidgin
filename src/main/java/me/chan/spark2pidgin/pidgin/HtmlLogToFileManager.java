package me.chan.spark2pidgin.pidgin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import me.chan.spark2pidgin.AppConstants;
import me.chan.spark2pidgin.spark.Transcript;
import me.chan.spark2pidgin.spark.TranscriptMessages;

/**
 * Converts the given transcripts into {@link HtmlLog} and writes it to disk
 * using {@link HtmlLogToFile}.
 * 
 */
public class HtmlLogToFileManager {

	private static final Log LOG = LogFactory.getLog(HtmlLogToFileManager.class);

	private String outputDirPath;

	/**
	 * Instantiates the object and initializes the output directory.
	 * 
	 * @param outputDirPath
	 */
	public HtmlLogToFileManager(String outputDirPath) {
		this.outputDirPath = outputDirPath;
	}

	/**
	 * Creates the output directory if it's empty.
	 * 
	 * @throws IOException
	 */
	protected void initOutputDir() throws IOException {
		Files.createDirectory(Paths.get(this.outputDirPath));
	}

	/**
	 * Converts the map of transcripts by contact name and writes the
	 * conversations under each contact to disk.
	 * 
	 * @param transcripts
	 *          - transcripts that will be written as html logs
	 * @throws IOException
	 */
	public void convertToHtmlLog(Map<String, Transcript> transcripts)
	    throws IOException {
		if (!transcripts.isEmpty()) {
			Iterator<String> transriptKey = transcripts.keySet().iterator();
			while (transriptKey.hasNext()) {
				String contactName = transriptKey.next();
				if (LOG.isDebugEnabled()) {
					LOG.debug("Converting html logs for " + contactName);
				}
				HtmlLogToFile logToFile = new HtmlLogToFile(this.outputDirPath + "/"
				    + contactName);
				Transcript tr = transcripts.get(contactName);
				Map<String, TranscriptMessages> trMsg = tr.getMessages();
				convertUserLog(contactName, logToFile, trMsg);
			}
		}
	}

	/**
	 * Converts the conversations under a contact to an html log and writes it to
	 * disk. Conversations are grouped by date. Each conversation per date is
	 * written as one html file.
	 * 
	 * @param contactName
	 *          - contact whose conversation will be converted to html log
	 * @param logToFile
	 *          - {@link HtmlLogToFile} instance used to write the html log to
	 *          disk
	 * @param messages
	 *          - conversation under the contact that will be converted into html
	 *          log. Messages under the same day is written in one html file.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected void convertUserLog(String contactName, HtmlLogToFile logToFile,
	    Map<String, TranscriptMessages> messages) throws FileNotFoundException,
	    IOException {
		Iterator<Entry<String, TranscriptMessages>> entrySet = messages.entrySet()
		    .iterator();
		while (entrySet.hasNext()) {
			Entry<String, TranscriptMessages> e = entrySet.next();
			TranscriptMessages msg = e.getValue();
			Date transcriptDate = msg.getDate();
			HtmlLog htmlLog = new HtmlLog(contactName, transcriptDate,
			    AppConstants.OWNER);
			htmlLog.setMessages(msg.getMessages());
			htmlLog.setFilename(AppConstants.Pidgin.DATE_FULL_IMPL
			    .format(transcriptDate) + ".html");
			logToFile.writeAsHtml(htmlLog);
		}
	}

}
