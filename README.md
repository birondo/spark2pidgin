## spark2pidgin

Converts Spark (http://www.igniterealtime.org/projects/spark/) transcripts to Pidgin (https://pidgin.im/) chat logs.

## Intended Users

Spark transcripts (chat logs), which are saved in a single xml per contact, can impact the s/w client as the xml size grows: slower response when opening chat history, slower response finding text, etc.
<p>Pidgin, on the other hand, averts this issue by saving chat logs into multiple html files.
<p>This project is for Spark users who want to migrate to Pidgin and still be able to view old chat logs using Pidgin's open log function.

## Installation/Usage for Windows

1. Download the binary distribution.
2. Unzip the file and go to &lt;distribution folder&gt;/dist/
3. Edit the following file: *props/app.properties*

   ```
   # Owner of the logs to be converted to pidgin.
   # This must match the to/from contact in the Spark transcripts.
   # The html log converter uses this name to display the messages 
   # for the owner in a different font color.
   app.owner=<your username>@<your server>
   # Used for displaying the s/w client alias in the logs.
   app.client.name=<jabber or the alias you want for your IM client>

   # Xml files in this folder will be parsed according to spark transcript xsd.
   # Spark transcripts are in the filename format contactname@server.xml.
   spark.log_folder=C:\\WORKSPACE\\spark2pidgin\\spark
   
   # Output directory for html-converted parsed Spark transcripts.
   # Existing files will be overwritten.
pidgin.log_folder=C:\\WORKSPACE\\spark2pidgin\\pidgin
```

4. Open command line / console window and cd to dist folder where spark2pidgin-&lt;version&gt;.jar is located. Run the following command replacing &lt;version&gt; accordingly.

   ```
   cmd>java -cp .;lib;props;spark2pidgin-<version>.jar me.chan.spark2pidgin.converter.Spark2PidginLogConverter
   ```

5. Preview the generated html log files. Then move the converted files to  your .purple directory. Please refer to Pidgin documentation on where to place your newly converted log files under .purple directory.

## License

Licensed under MIT.
