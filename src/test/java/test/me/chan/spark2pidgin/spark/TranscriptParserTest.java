package test.me.chan.spark2pidgin.spark;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import me.chan.spark2pidgin.spark.Message;
import me.chan.spark2pidgin.spark.TranscriptParser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.xml.sax.SAXException;

/**
 * Test class for the xml parser.
 * 
 * @author chan
 * 
 */
public class TranscriptParserTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	/**
	 * Create a temporary file with dummy conversation before the test is started.
	 */
	public File sampleLog;

	@Before
	public void initFile() throws IOException {
		StringBuilder transcriptMsg = new StringBuilder();
		transcriptMsg
		    .append(
		        "<transcript><messages><message><to>anna@jabberserver</to><from>chan@jabberserver/spark</from><body>hi. re &quot;APVP Sales&quot;, can you give me description on what you are trying to come up with (e.g. server environment and software specifications?). Then what inputs you'll be needing.</body><date>2014-11-17 12:05:01.750 CST</date></message>")
		    .append(
		        "<message><to>chan@jabberserver/spark</to><from>anna@jabberserver/Winjab</from><body>hello</body><date>2014-11-17 12:05:54.156 CST</date></message>")
		    .append(
		        "<message><to>chan@jabberserver/spark</to><from>anna@jabberserver/Winjab</from><body>okay, will send this out as an email</body><date>2014-11-17 12:05:59.437 CST</date></message>")
		    .append(
		        "<message><to>anna@jabberserver/Winjab</to><from>chan@jabberserver/spark</from><body>hi .</body><date>2014-11-17 18:14:02.562 CST</date></message>")
		    .append(
		        "<message><to>chan@jabberserver/spark</to><from>anna@jabberserver/Winjab</from><body>hello </body><date>2014-11-17 18:14:06.546 CST</date></message>")
		    .append(
		        "<message><to>anna@jabberserver/Winjab</to><from>chan@jabberserver/spark</from><body>are you familiar with the sizing template?</body><date>2014-11-17 18:14:16.890 CST</date></message>")
		    .append(
		        "<message><to>chan@jabberserver/spark</to><from>anna@jabberserver/Winjab</from><body>what template?</body><date>2014-11-17 18:14:30.656 CST</date></message>")
		    .append(
		        "<message><to>anna@jabberserver/Winjab</to><from>chan@jabberserver/spark</from><body>I have one for an old project.. SSM.</body><date>2014-11-17 18:14:35.484 CST</date></message>")
		    .append(
		        "<message><to>anna@jabberserver/Winjab</to><from>chan@jabberserver/spark</from><body>I&apos;ll give you a copy of what I have.</body><date>2014-11-17 18:15:05.140 CST</date></message>")
		    .append(
		        "<message><to>chan@jabberserver/spark</to><from>anna@jabberserver/Winjab</from><body>ahh i see</body><date>2014-11-17 18:15:11.515 CST</date></message>")
		    .append(
		        "<message><to>chan@jabberserver/spark</to><from>anna@jabberserver/Winjab</from><body>But I dont have a copy of SSM&apos;s template, though</body><date>2014-11-17 18:15:12.46 CST</date></message>")
		    .append(
		        "<message><to>chan@jabberserver/spark</to><from>anna@jabberserver/Winjab</from><body>ahh ok </body><date>2014-11-17 18:15:17.453 CST</date></message>")
		    .append(
		        "<message><to>anna@jabberserver/Winjab</to><from>chan@jabberserver/spark</from><body>sent. can you check the file if you are familiar with it?</body><date>2014-11-17 18:16:24.562 CST</date></message>")
		    .append(
		        "<message><to>anna@jabberserver/Winjab</to><from>chan@jabberserver/spark</from><body>&quot;Sample sizing template&quot;  is the mail&apos;s subject</body><date>2014-11-17 18:16:40.593 CST</date></message>")
		    .append(
		        "<message><to>chan@jabberserver/spark</to><from>anna@jabberserver/Winjab</from><body>yes , received </body><date>2014-11-17 18:17:39.593 CST</date></message>")
		    .append(
		        "<message><to>chan@jabberserver/spark</to><from>anna@jabberserver/Winjab</from><body>yes , I am familiar with the template</body><date>2014-11-17 18:17:52.328 CST</date></message>")
		    .append(
		        "<message><to>chan@jabberserver/spark</to><from>anna@jabberserver/Winjab</from><body>used by SSM</body><date>2014-11-17 18:17:56.984 CST</date></message>")
		    .append("</messages></transcript>");

		sampleLog = tempFolder.newFile("anna@jabberserver.xml");
		try (FileWriter fw = new FileWriter(sampleLog)) {
			fw.write(transcriptMsg.toString());
		}
	}

	@Test
	public void testParse() {
		TranscriptParser obj = new TranscriptParser(sampleLog);
		try {
			Set<Message> msgSet = obj.parse();
			assertNotNull(msgSet);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
