package victoritkin.perforce.triggers.listener.rest;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/*******************************************************************************
 * POJO that maps the XML for a change-commit notification.
 *
 * @author Victor Itkin
 * @version 1.0
  */
@XmlRootElement(name = "change-commit")
public class ChangeCommit
{
  //~ Instance fields ----------------------------------------------------------

  /** DOCUMENT ME! */
  String changeList;

  /** DOCUMENT ME! */
  String changeListDescription;

  /** DOCUMENT ME! */
  String changeRoot;

  /** DOCUMENT ME! */
  String client;

  /** DOCUMENT ME! */
  String clientHost;

  /** DOCUMENT ME! */
  String clientIp;

  /** DOCUMENT ME! */
  String serverHost;

  /** DOCUMENT ME! */
  String serverIp;

  /** DOCUMENT ME! */
  String serverPort;

  /** DOCUMENT ME! */
  String serverRoot;

  /** DOCUMENT ME! */
  String user;

  //~ Methods ------------------------------------------------------------------

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @XmlElement
  public String getClient()
  {
    return client;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @param client DOCUMENT ME!
   */
  public void setClient(String client)
  {
    this.client = client;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @XmlElement(name = "clienthost")
  public String getClientHost()
  {
    return clientHost;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @param clientHost DOCUMENT ME!
   */
  public void setClientHost(String clientHost)
  {
    this.clientHost = clientHost;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @XmlElement(name = "clientip")
  public String getClientIp()
  {
    return clientIp;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @param clientIp DOCUMENT ME!
   */
  public void setClientIp(String clientIp)
  {
    this.clientIp = clientIp;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @XmlElement(name = "serverhost")
  public String getServerHost()
  {
    return serverHost;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @param serverHost DOCUMENT ME!
   */
  public void setServerHost(String serverHost)
  {
    this.serverHost = serverHost;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @XmlElement(name = "serverip")
  public String getServerIp()
  {
    return serverIp;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @param serverIp DOCUMENT ME!
   */
  public void setServerIp(String serverIp)
  {
    this.serverIp = serverIp;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @XmlElement(name = "serverport")
  public String getServerPort()
  {
    return serverPort;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @param serverPort DOCUMENT ME!
   */
  public void setServerPort(String serverPort)
  {
    this.serverPort = serverPort;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @XmlElement(name = "serverroot")
  public String getServerRoot()
  {
    return serverRoot;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @param serverRoot DOCUMENT ME!
   */
  public void setServerRoot(String serverRoot)
  {
    this.serverRoot = serverRoot;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @XmlElement
  public String getUser()
  {
    return user;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @param user DOCUMENT ME!
   */
  public void setUser(String user)
  {
    this.user = user;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @XmlElement(name = "changelist")
  public String getChangeList()
  {
    return changeList;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @param changeList DOCUMENT ME!
   */
  public void setChangeList(String changeList)
  {
    this.changeList = changeList;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @XmlElement(name = "changeroot")
  public String getChangeRoot()
  {
    return changeRoot;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @param changeRoot DOCUMENT ME!
   */
  public void setChangeRoot(String changeRoot)
  {
    this.changeRoot = changeRoot;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  @XmlElement
  public String getDescription()
  {
    return changeListDescription;
  }

  /*****************************************************************************
   * DOCUMENT ME!
   *
   * @param description DOCUMENT ME!
   */
  public void setDescription(String description)
  {
    this.changeListDescription = description;
  }
}
