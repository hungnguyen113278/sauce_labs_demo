package config;


import java.util.List;

import org.w3c.dom.Element;

import common.Constant;


public class ProviderConfiguration
{
    public static synchronized ProviderConfiguration getProvider()
    {
        if ( provider == null ) {
            provider = new ProviderConfiguration();
        }
        return provider;
    }
    
    public void loadInstance() 
    {
        loadInstance(Constant.PathConfig.AUTOMATION_CONFIG_XML, null);
    }

    public void loadInstance(String instance) 
    {
        loadInstance(Constant.PathConfig.AUTOMATION_CONFIG_XML, instance);
    }
    
    public void loadInstance(String filename, String instance)
    {
        try {
            provider.load(filename, instance);
        } catch (Exception e) {
            instance = null;
            System.out.println("Failed to load due to an XML problem." + e);
        }
    }
 
    public DataInstanceConfiguration getInstance()
    {
        return instance;
    }
   
    public DataSeleniumConfiguration getSelenium()
    {
        return selenium;
    }
    
    public DataSetConfiguration getDataSet()
    {
    	return dataSet;
    }
    
    /**
     * Get Username/Password from data.xml and set us to ConfigDataSet
     * @param role
     * @throws XmlException
     */
    public DataSetConfiguration loadAccount(String type) 
    {
    	XmlHelper xml = new XmlHelper();
    	xml.parseResource(Constant.PathConfig.DATA_TEST_XML);
    	
    	for (Element element : xml.getElements("/Data/Users/User")){
    		dataSet = new DataSetConfiguration();
    		if(element.getAttribute("type").equals(type)){
    			dataSet.fromElement(xml, element);
    			break;
    		}
    	}
    	return dataSet;
    }
    
    /**
     * get email username
     * @return
     * @author truong.pham
     */
    public String getUsernameEmail(){
    	XmlHelper xml = new XmlHelper();
    	xml.parseResource(Constant.PathConfig.DATA_TEST_XML);
    	Element element = xml.getElement("/Data/Configure/Gmail/Username");
    	return element.getTextContent();
    }
    
    /**
     * get email password
     * @return
     * @author truong.pham
     */
    public String getPasswordEmail(){
    	XmlHelper xml = new XmlHelper();
    	xml.parseResource(Constant.PathConfig.DATA_TEST_XML);
    	Element element = xml.getElement("/Data/Configure/Gmail/Password");
    	return element.getTextContent();
    }
    
    /**
     * get bloomboard url on mobile
     * @return
     * @author truong.pham
     */
    public String getMobileUrl() {
    	XmlHelper xml = new XmlHelper();
    	xml.parseResource(Constant.PathConfig.AUTOMATION_CONFIG_XML);
    	Element element = xml.getElement("/automation/instances/instance/mobileUrl");
    	return element.getTextContent();
    }
     
    /**
     * Load all data from automation.config.xml
     * @param filename: file xml
     * @param instanceTag
     * @throws XmlException
     * @throws ConfigDataException
     */
	private void load(String filename, String instanceTag)  {
		XmlHelper xml = new XmlHelper();

		System.out.println("Loading configuration from file " + filename);
		xml.parseResource(filename);

		if (instanceTag == null) {
			instanceTag = xml.getStringAttribute(xml.getElement("/automation"),
					"instance");
			if (instanceTag == null) {
				System.out.println(
						"You must provide an instance name to the load function or define a default in the XML file <automation instance=\"DEFAULT INSTANCE NAME\"");
			}
		}

		System.out.println("Using the test configuration instance "
				+ instanceTag);

		instance = null;
		for (Element element : xml
				.getElements("/automation/instances/instance")) {
			instance = new DataInstanceConfiguration();
			instance.fromElement(xml, element);
			if (instance.getTag().equals(instanceTag) == false) {
				instance = null;
			} else {
				break;
			}
		}

		if (instance == null) {
			System.out.println("The test configuration instance "
					+ instanceTag + " has not been defined in " + filename);
		}

//		System.out.println("Mapping selenium server element "
//				+ instance.getSelenium());
//		selenium = null;
//		for (Element element : xml.getElements("/automation/selenium/server")) {
//			selenium = new DataSeleniumConfiguration();
//			selenium.fromElement(xml, element);
//			if (selenium.getTag().equals(instance.getSelenium()) == false) {
//				selenium = null;
//			} else {
//				break;
//			}
//		}
//
//		if (selenium == null) {
//			System.out.println("Undefined selenium element "
//					+ instance.getSelenium());
//		}

		System.out.println("Configuration loaded");
	}
    
    public String getUrl() {
    	XmlHelper xml = new XmlHelper();
    	xml.parseResource(Constant.PathConfig.AUTOMATION_CONFIG_XML);
    	Element element = xml.getElement("/automation/instances/instance/url");
    	return element.getTextContent();
    }
    
    public String getHost() {
    	XmlHelper xml = new XmlHelper();
    	xml.parseResource(Constant.PathConfig.AUTOMATION_CONFIG_XML);
    	Element element = xml.getElement("/automation/instances/instance/host");
    	return element.getTextContent();
    }
    public String getHost(String tag) {
    	String host = null;
    	XmlHelper xml = new XmlHelper();
    	xml.parseResource(Constant.PathConfig.AUTOMATION_CONFIG_XML);
    	List<Element> elements = xml.getElements("/automation/grids/grid");
    	for(Element element: elements) {
    		String tagName = element.getAttribute("tag");
    		if(tagName.equals(tag)) {
    			host =  element.getElementsByTagName("host").item(0).getTextContent();
    		}
    	}
    	return host;
    }
     
    private ProviderConfiguration() {}

    private static ProviderConfiguration provider;
    
    private DataInstanceConfiguration instance = null;
    private DataSeleniumConfiguration selenium = null;
    private DataSetConfiguration dataSet = null; 
   
}
