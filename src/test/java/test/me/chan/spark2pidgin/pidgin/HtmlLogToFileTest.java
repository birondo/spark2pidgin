package test.me.chan.spark2pidgin.pidgin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.chan.spark2pidgin.pidgin.HtmlLogToFileManager;
import me.chan.spark2pidgin.spark.Message;
import me.chan.spark2pidgin.spark.Transcript;
import me.chan.spark2pidgin.spark.TranscriptMessages;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Runs test for transforming Transcripts into html log files.
 * 
 * @author chan
 * 
 */
@RunWith(Parameterized.class)
public class HtmlLogToFileTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Parameter(value = 0)
	public Transcript transcript;

	@Parameters(name = "{index}: Trascript({0})")
	public static Collection<Object[]> messagesData() {
		Date msgDate1 = new Date();
		String dateKey = new SimpleDateFormat("yyyyMMdd").format(msgDate1);
		Message m1 = new Message(
		    "chan@jabberserver",
		    "anna@jabberserver",
		    "hi. the documents you requested are here:) pick them up at reception area. <br/>\nthanks:)",
		    new Date());
		Message m2 = new Message("anna@jabberserver", "chan@jabberserver",
		    "great! will drop by. thanks", new Date());
		TranscriptMessages tm1 = new TranscriptMessages(new Date());
		tm1.addMessage(m1);
		tm1.addMessage(m2);
		Transcript t1 = new Transcript("anna@jabberserver");
		t1.addTranscriptMessage(dateKey, tm1);

		Message m3 = new Message("bob@jabberserver", "chan@jabberserver",
		    "hey, print function isnt working. any ideas?", new Date());
		TranscriptMessages tm2 = new TranscriptMessages(new Date());
		tm2.addMessage(m3);
		Transcript t2 = new Transcript("bob@jabberserver");
		t2.addTranscriptMessage(dateKey, tm2);

		Object[][] array = new Object[][] { { t1 }, { t2 } };
		return Arrays.asList(array);
	}

	@Test
	public void testWriteAsHtml() throws FileNotFoundException, IOException {
		File tempFolder = folder.newFolder("_tmp");
		HtmlLogToFileManager mgr1 = new HtmlLogToFileManager(
		    tempFolder.getAbsolutePath());
		Map<String, Transcript> conv = new HashMap<String, Transcript>();
		conv.put(transcript.getJabberName(), transcript);
		mgr1.convertToHtmlLog(conv);

		String[] subfolders = tempFolder.list();
		assertNotNull(subfolders);
		assertNotEquals(subfolders.length, 0);
		assertThat(Arrays.asList(subfolders), either(hasItem("anna@jabberserver"))
		    .or(hasItem("bob@jabberserver")));
	}
}
