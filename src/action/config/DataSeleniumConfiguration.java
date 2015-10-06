package config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
  <selenium tag="local">
    <host>localhost</host>
    <port>4444</port>
    <command>*firefox</command>
  </selenium>
 *
 */
public class DataSeleniumConfiguration extends DataConfiguration
{
    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getPort()
    {
        return port;
    }
    
    public Integer getPortAsInteger()
    {
        Integer value = null;
        try {
            value = new Integer(Integer.parseInt(port));
        } catch (NumberFormatException e) {
            System.out.println("The selenium port must be an integer." + e);
        }
        return value;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    public String getDriver()
    {
        return driver;
    }

    public void setDriver(String driver)
    {
        this.driver = driver;
    }

    @Override
    public Element toElement(Document document) 
    {
        Element selenium = document.createElement("selenium");
        selenium.setAttribute("tag", getTag());
        Element host = document.createElement("host");
        host.setTextContent(getHost());
        selenium.appendChild(host);
        Element port = document.createElement("port");
        port.setTextContent(getPort());
        selenium.appendChild(port);
        Element driver = document.createElement("driver");
        driver.setTextContent(getDriver());
        selenium.appendChild(driver);
        return selenium;
    }

    @Override
    public void fromElement(XmlHelper xml, Element base) 
    {
        setTag(base.getAttribute("tag"));
        setHost(xml.getText(base, "host"));
        setPort(xml.getText(base, "port"));
        setDriver(xml.getText(base, "driver"));
    }

    private String host;
    private String port;
    private String driver;
}
