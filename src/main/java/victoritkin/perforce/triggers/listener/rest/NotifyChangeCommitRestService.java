package victoritkin.perforce.triggers.listener.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeSet;

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

  //~ Instance fields ----------------------------------------------------------

  /**
   * Shared map of processes to avoid running the same command more than
   * once.
   */
  private Hashtable<String, Process> h = new Hashtable<String, Process>();

  /** Loaded configuration. */
  private Properties p = new Properties();

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
   * &nbsp;&nbsp;&nbsp;&nbsp;projectscontrol_notify_change-commit change-commit //depot/...
   * "/usr/share/perforce/bin/triggers/projectscontrol_notify_change-commit.sh
   * %client% %clienthost% %clientip% %serverhost% %serverip% %serverport%
   * %serverroot% %user% %changelist% %changeroot%"
   *
   * @param commit POJO containing all the details about the change commit.
   */
  @POST
  @Consumes("application/xml")
  public void post(ChangeCommit commit)
  {
    logger.info("[NEW CHANGE COMMIT]\n" + "Client: {}\n" + "ClientHost: {}\n" +
                "ClientIp: {}\n" + "ServerHost: {}\n" + "ServerIp: {}\n" +
                "getServerPort: {}\n" + "ServerRoot: {}\n" + "User: {}\n" +
                "ChangeList: {}\n" + "ChangeRoot: {}\n" +
                "ChangeListDescription: {}",
                new String[]
                {
                  commit.getClient(), commit.getClientHost(),
                  commit.getClientIp(), commit.getServerHost(),
                  commit.getServerIp(), commit.getServerPort(),
                  commit.getServerRoot(), commit.getUser(),
                  commit.getChangeList(), commit.getChangeRoot(),
                  commit.getDescription()
                });

    String path = commit.getChangeRoot();

    TreeSet<String> executedCommands = new TreeSet<String>();

    for (Entry<Object, Object> e : p.entrySet())
    {
      if (path.startsWith((String) e.getKey()))
      {
        String command = (String) e.getValue();

        if (!executedCommands.contains(command))
        {
          executedCommands.add(command);

          try
          {
            Process proc = h.get(command);

            if (proc != null)
            {
              proc.exitValue();
              h.remove(command);
            }

            logger.info("Run: {}", command);

            proc = Runtime.getRuntime().exec(command);
            h.put(command, proc);
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

      if (is != null)
      {
        p.load(is);
        is.close();
      }

      logger.info("Configuration: {}", p.entrySet());
    }
    catch (IOException ex)
    {
      logger.error(ex.getMessage(), ex);
    }

    return "Configuration:\n" + p.entrySet();
  }
}
