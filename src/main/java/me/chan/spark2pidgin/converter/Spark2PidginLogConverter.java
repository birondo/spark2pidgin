package me.chan.spark2pidgin.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import me.chan.spark2pidgin.AppConstants;
import me.chan.spark2pidgin.pidgin.HtmlLogToFileManager;
import me.chan.spark2pidgin.spark.TranscriptParserManager;

/**
 * Main class for running the application.
 * 
 */
public class Spark2PidginLogConverter {

	private static final Log LOG = LogFactory
	    .getLog(Spark2PidginLogConverter.class);

	public Spark2PidginLogConverter() {
		// empty
	}

	/**
	 * Starts the log conversion by parsing all files in the spark log folder and
	 * storing them in memory. Once all logs are read, they are organised by
	 * conversation dates and written to disk as html files following pidgin log
	 * format.
	 * 
	 * @throws Exception
	 */
	public void process() throws Exception {
		TranscriptParserManager parserMgr = new TranscriptParserManager(
		    AppConstants.SPARK_LOG_FOLDER);
		parserMgr.parseAll();

		HtmlLogToFileManager log2FileMgr = new HtmlLogToFileManager(
		    AppConstants.PIDGIN_LOG_FOLDER);
		log2FileMgr.convertToHtmlLog(parserMgr.getTranscripts());

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LOG.info("Running Spark2PidginLogConverter..");
			new Spark2PidginLogConverter().process();
		} catch (Exception e) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(e.getMessage(), e);
			} else if (LOG.isErrorEnabled()) {
				LOG.error(e.getMessage());
			}
		} finally {
			LOG.info("End.");
		}
	}

}
