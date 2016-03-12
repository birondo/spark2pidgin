package test.me.chan.spark2pidgin;

import static org.junit.Assert.*;
import me.chan.spark2pidgin.AppConstants;

import org.junit.Test;

/**
 * Makes sure {@link AppConstants} is initialized properly with
 * <code>app.properties<code>.
 * 
 * @author chan
 * 
 */
public class AppConstantsTest {

	@Test
	public void test() {
		assertNotNull(AppConstants.OWNER);
	}

}
