package config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



/**
 * The instance element brings together all of the testbed components to fully define everything
 * That a test needs to execute.
 *
 */
public class DataInstanceConfiguration extends DataConfiguration
{
    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
   
//    public String getSelenium()
//    {
//        return selenium;
//    }
    
    public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getGrid() {
		return grid;
	}
	
//    public void setSelenium(String selenium)
//    {
//        this.selenium = selenium;
//    }    
  

    @Override
    public Element toElement(Document document) 
    {
        Element instance = document.createElement("customer");
        instance.setAttribute("tag", getTag());
       
        Element host = document.createElement("url");
        host.setTextContent(getUrl());
        instance.appendChild(host);
        
//        Element selenium = document.createElement("selenium");
//        selenium.setAttribute("ref", getSelenium());
//        instance.appendChild(selenium);
        
        Element grid = document.createElement("grid");
        grid.setTextContent(getGrid());
        instance.appendChild(grid);
        
        return instance;
    }

    @Override
    public void fromElement(XmlHelper xml, Element base)
    {
        setTag(base.getAttribute("tag"));
        setUrl(xml.getText(base, "url"));        
//        setSelenium(xml.getStringAttribute(xml.getElement(base, "selenium"), "ref"));
        setGrid(xml.getText(base,"grid"));
    }

	private String url;
	private String grid;
    private String selenium;
   
}
