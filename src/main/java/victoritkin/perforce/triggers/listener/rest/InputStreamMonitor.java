package victoritkin.perforce.triggers.listener.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/*******************************************************************************
 * Monitor and process a given InputStream then close it.
 *
 * @author Victor Itkin
 * @version 1.0
 */
public class InputStreamMonitor extends Thread
{
  //~ Static fields/initializers -----------------------------------------------

  /** DOCUMENT ME! */
  private static final Logger logger =
    LoggerFactory.getLogger(InputStreamMonitor.class);

  //~ Instance fields ----------------------------------------------------------

  /** The InputStream to monitor */
  InputStream inputStream;

  /** Label */
  String label;

  //~ Constructors -------------------------------------------------------------

/*****************************************************************************
   * Creates a new InputStreamMonitor object.
   *
   * @param inputStream InputStream to monitor.
   */
  public InputStreamMonitor(String label, InputStream inputStream)
  {
	this.label = label;
    this.inputStream = inputStream;
  }

  //~ Methods ------------------------------------------------------------------

  /*****************************************************************************
   * Processed in the Thread.
   */
  @Override
  public void run()
  {
    try
    {
      BufferedReader reader =
        new BufferedReader(new InputStreamReader(inputStream));

      String line = "";

      try
      {
        while ((line = reader.readLine()) != null)
        {
          logger.info("[{}] {}", label, line);
        }
      }
      finally
      {
        reader.close();
      }
    }
    catch (IOException ioe)
    {
      logger.error(ioe.getMessage(), ioe);
    }
  }
}
