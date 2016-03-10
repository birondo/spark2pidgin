package me.chan.spark2pidgin;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Application constants used by this app. See <code>app.properties</code> to
 * modify the default values.
 * 
 */
public class AppConstants {

	private static final Properties PROPS = new Properties();

	static {
		try (InputStream appIs = Thread.currentThread().getContextClassLoader()
		    .getResourceAsStream("app.properties")) {
			PROPS.load(appIs);
		} catch (Exception e) {

		}
	}

	public static final String APP_TIMEZONE = PROPS
	    .getProperty("app.date.timezone");
	public static final TimeZone TIMEZONE = TimeZone.getTimeZone(APP_TIMEZONE);
	public static final String APP_LOCALE = PROPS.getProperty("app.date.locale");
	public static final Locale LOCALE = new Locale(APP_LOCALE);

	public static final String DATE_FORMAT_SIMPLE = "yyyy-MM-dd";
	public static final SimpleDateFormat DATE_FORMAT_SIMPLE_IMPL = new SimpleDateFormat(
	    "yyyy-MM-dd", LOCALE);

	public static final String OWNER = PROPS.getProperty("app.owner");
	public static final String APP_CLIENT_NAME = PROPS
	    .getProperty("app.client.name");
	public static final String SPARK_LOG_FOLDER = PROPS
	    .getProperty("spark.log_folder");
	public static final String PIDGIN_LOG_FOLDER = PROPS
	    .getProperty("pidgin.log_folder");

	static {
		DATE_FORMAT_SIMPLE_IMPL.setLenient(false);
		DATE_FORMAT_SIMPLE_IMPL.setTimeZone(TIMEZONE);

	}

	/**
	 * Private constructor
	 */
	private AppConstants() {
		// no instantiation
	}

	public static String getStringPropery(String prop) {
		return PROPS.getProperty(prop);
	}

	/**
	 * Constants related to Spark.
	 */
	public static class Spark {

		private Spark() {
			// no instantiation
		}

		public static final String DATE_FULL_FORMAT = PROPS
		    .getProperty("spark.date.full");
		public static SimpleDateFormat DATE_FULL_IMPL = new SimpleDateFormat(
		    DATE_FULL_FORMAT, new Locale(PROPS.getProperty("spark.date.locale")));

		static {
			DATE_FULL_IMPL.setLenient(false);
			DATE_FULL_IMPL.setTimeZone(TimeZone.getTimeZone("spark.date.timezone"));
		}
	}

	/**
	 * Constants for Pidgin.
	 */
	public static class Pidgin {

		private static final Locale LOCALE = new Locale(
		    PROPS.getProperty("pidgin.date.locale"));
		private static final TimeZone TZ = TimeZone.getTimeZone(PROPS
		    .getProperty("pidgin.date.timezone"));
		public static final SimpleDateFormat TIME_FULL_FORMAT_IMPL = new SimpleDateFormat(
		    PROPS.getProperty("pidgin.date.time_full"), LOCALE);

		public static final String DATE_FULL_FORMAT = PROPS
		    .getProperty("pidgin.date.full");
		public static SimpleDateFormat DATE_FULL_IMPL = new SimpleDateFormat(
		    DATE_FULL_FORMAT, LOCALE);

		public static final String DATE_FORMAT = PROPS.getProperty("pidgin.date");
		public static SimpleDateFormat DATE_FORMAT_IMPL = new SimpleDateFormat(
		    DATE_FORMAT, LOCALE);

		static {
			TIME_FULL_FORMAT_IMPL.setLenient(false);
			TIME_FULL_FORMAT_IMPL.setTimeZone(TZ);
			DATE_FULL_IMPL.setLenient(false);
			DATE_FULL_IMPL.setTimeZone(TZ);
			DATE_FORMAT_IMPL.setLenient(false);
			DATE_FORMAT_IMPL.setTimeZone(TZ);
		}

	}

}
