package victoritkin.perforce.triggers.listener.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


/*******************************************************************************
 * Simple example.
 *
 * @author Victor Itkin
 * @version 1.0
  */
@Path("/message")
public class MessageRestService
{
  //~ Methods ------------------------------------------------------------------

  /*****************************************************************************
   * RESTful example.
   *
   * @param msg Message to display.
   *
   * @return The resulting message.
   */
  @GET
  @Path("/{param}")
  public Response printMessage(@PathParam("param")
  String msg)
  {
    String result = "RESTful example: " + msg;

    return Response.status(200).entity(result).build();
  }
}
