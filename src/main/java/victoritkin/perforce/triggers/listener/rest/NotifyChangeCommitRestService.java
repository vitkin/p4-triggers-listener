package victoritkin.perforce.triggers.listener.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


/*******************************************************************************
 * RESTful service to be called on new change commit.
 *
 * @author Victor Itkin
 * @version 1.0
 */
@Path("/notifyChangeCommit")
public class NotifyChangeCommitRestService
{
  //~ Static fields/initializers -----------------------------------------------

  /** Private logger */
  private static final Logger logger =
    LoggerFactory.getLogger(NotifyChangeCommitRestService.class);

  /** Unknown path token */
  public static final String UNKNOWN_PATH = "UNKNOWN_PATH";

  //~ Instance fields ----------------------------------------------------------

  /**
   * Shared map of processes to avoid running the same command more than
   * once.
   */
  private Hashtable<String, Process> processesByCommands =
    new Hashtable<String, Process>();

  /** Loaded configuration. */
  private Properties commandsByPaths = new Properties();

  //~ Constructors -------------------------------------------------------------

  /*****************************************************************************
   * Creates a new NotifyChangeCommitRestService object.
   */
  public NotifyChangeCommitRestService()
  {
    loadConfiguration();
  }

  //~ Methods ------------------------------------------------------------------

  /*****************************************************************************
   * POST method handling JAX-RS XML based calls.<br>
   * Triggers:<br>
   * &nbsp;&nbsp;&nbsp;&nbsp;projectscontrol_notify_change-commit
   * change-commit //depot/...
   * "/usr/share/perforce/bin/triggers/projectscontrol_notify_change-commit.sh
   * '%client%' '%clienthost%' '%clientip%' '%serverhost%' '%serverip%'
   * '%serverport%' '%serverroot%' '%user%' '%changelist%' '%changeroot%'
   * '%oldchangelist%'"
   *
   * @param commit POJO containing all the details about the change commit.
   */
  @POST
  @Consumes("application/xml")
  public void post(ChangeCommit commit)
  {
    logger.info("[NEW CHANGE COMMIT]\n" + "Client: {}\n" + "Client Host: {}\n" +
                "Client Ip: {}\n" + "Server Host: {}\n" + "Server IP: {}\n" +
                "Server Port: {}\n" + "Server Root: {}\n" + "User: {}\n" +
                "Change List: {}\n" + "Old Change List: {}\n" +
                "Change Root: {}\n" + "Description: {}",
                new String[]
                {
                  commit.getClient(), commit.getClientHost(),
                  commit.getClientIp(), commit.getServerHost(),
                  commit.getServerIp(), commit.getServerPort(),
                  commit.getServerRoot(), commit.getUser(),
                  commit.getChangeList(), commit.getOldChangeList(),
                  commit.getChangeRoot(), commit.getDescription()
                });

    String path = commit.getChangeRoot();

    if (!path.startsWith("//"))
    {
      path = UNKNOWN_PATH;
    }

    Set<String> executedCommands = new HashSet<String>();

    for (Entry<Object, Object> e : commandsByPaths.entrySet())
    {
      if (path.startsWith((String) e.getKey()))
      {
        String command = (String) e.getValue();

        if (!executedCommands.contains(command))
        {
          executedCommands.add(command);

          try
          {
            Process process = processesByCommands.get(command);

            // Check if the command has been already started.
            // If there is still a running process for that command we'll get 
            // an IllegalStateException that will prevent to start the command 
            // more than once.
            if (process != null)
            {
              process.exitValue();
              processesByCommands.remove(command);
            }

            logger.info("[RUN COMMAND]\n Change Root: {}\n Command: {}", path,
                        command);

            process = Runtime.getRuntime().exec(command);

            InputStreamMonitor stdout =
              new InputStreamMonitor("STDOUT", process.getInputStream());
            
            stdout.start();

            InputStreamMonitor stderr =
              new InputStreamMonitor("STDERR", process.getErrorStream());
            
            stderr.start();

            processesByCommands.put(command, process);
          }
          catch (IOException ex)
          {
            logger.error(ex.getMessage(), ex);
          }
          catch (IllegalStateException ex)
          {
            logger.warn(ex.getMessage(), ex);
          }
        }
      }
    }
  }

  /*****************************************************************************
   * Loads the configuration from 'p4-triggers-listener.properties'.
   *
   * @return The loaded configuration.
   */
  @GET
  @Produces("text/plain")
  public String loadConfiguration()
  {
    try
    {
      FileInputStream is =
        new FileInputStream("p4-triggers-listener.properties");

      commandsByPaths.load(is);
      is.close();
    }
    catch (IOException ex)
    {
      logger.error(ex.getMessage(), ex);
    }

    StringBuilder sb = new StringBuilder();

    sb.append("Configuration:\n");

    for (Entry entry : commandsByPaths.entrySet())
    {
      sb.append('\t');
      sb.append((String) entry.getKey());
      sb.append(" = ");
      sb.append((String) entry.getValue());
      sb.append('\n');
    }

    String printableConfiguration = sb.toString();

    logger.info(printableConfiguration);

    return printableConfiguration;
  }
}
