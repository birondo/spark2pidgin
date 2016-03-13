package test.me.chan.spark2pidgin;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.me.chan.spark2pidgin.pidgin.HtmlLogToFileTest;
import test.me.chan.spark2pidgin.spark.TranscriptParserTest;

@RunWith(Suite.class)
@SuiteClasses({ AppConstantsTest.class, TranscriptParserTest.class,
    HtmlLogToFileTest.class })
public class AllTests {

}
