package me.chan.spark2pidgin.pidgin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * Class for writing the {@link HtmlLog} to a file to disk. This class uses a
 * velocity template for creating the html file.
 * 
 */
public class HtmlLogToFile {

	private static final Template pidginHtmlTemplate;
	static {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class",
		    ClasspathResourceLoader.class.getName());
		ve.init();

		pidginHtmlTemplate = ve.getTemplate("vm/pidgin_html_log.vm", "UTF-8");
	}

	private String outputDirPath;

	/**
	 * Instantiates an object and initializes the output directory.
	 * 
	 * @throws IOException
	 * 
	 */
	public HtmlLogToFile(String outputDir) throws IOException {
		this.outputDirPath = outputDir;
		initOutputDir();
	}

	/**
	 * Creates the output directory if non-existent.
	 * 
	 * @throws IOException
	 */
	protected void initOutputDir() throws IOException {
		Files.createDirectory(Paths.get(this.outputDirPath));
	}

	/**
	 * Merges contents of the {@link HtmlLog} to the velocity template.
	 * 
	 * @param htmlLog
	 *          - html log to be written
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void writeAsHtml(HtmlLog htmlLog) throws FileNotFoundException,
	    IOException {
		VelocityContext ctx = new VelocityContext();

		ctx.put("title", htmlLog.getTitle());
		ctx.put("heading", htmlLog.getHeading());
		ctx.put("messages", htmlLog.getMessages());
		ctx.put("owner", htmlLog.getOwner());
		ctx.put("esc", new StringEscapeUtils());

		Writer writer = new StringWriter();
		pidginHtmlTemplate.merge(ctx, writer);
		writeToFile(this.outputDirPath + "/" + htmlLog.getFilename(),
		    pidginHtmlTemplate, ctx);
	}

	/**
	 * Opens the a {@link FileWriter} using specified file and writes the merged
	 * template and context.
	 * 
	 * @param file
	 *          - file where the contents will be written to
	 * @param template
	 *          - the velocity template used to create the html log file
	 * @param ctx
	 *          - context containing the values to be merged to the template
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void writeToFile(String file, Template template, VelocityContext ctx)
	    throws FileNotFoundException, IOException {
		File f = new File(file);
		try (FileWriter fw = new FileWriter(f)) {
			template.merge(ctx, fw);
		}
	}

}
