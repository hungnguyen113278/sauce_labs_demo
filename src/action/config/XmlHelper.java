package config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlHelper
{
    public void parseXml(String xml) 
    {
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            InputStream inStream =
                new ByteArrayInputStream(xml.getBytes("UTF-8"));
            document = builder.parse(inStream);
        } catch (ParserConfigurationException e) {
        	System.out.println("Can't configure the XML parser.");
        } catch (SAXException e) {
        	System.out.println("Can't parse the xml string " + xml + e);
        } catch (IOException e) {
        	System.out.println("Can't read the xml string " + xml + e);
        }
    }
    
    public void parseResource(String filename) 
    {
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            InputStream inStream = new FileInputStream(new File(filename));
            document = builder.parse(inStream);
        } catch (ParserConfigurationException e) {
        	System.out.println("Can't configure the XML parser." + e);
        } catch (SAXException e) {
        	System.out.println("Can't parse the xml file " + filename + e);
        } catch (IOException e) {
        	System.out.println("Can't read the xml file " + filename + e);
            
        }
    }
    
    /**
     * Get text of element
     * @param root
     * @param path
     * @return
     * @throws XmlException
     */
    public String getText(Element root, String path) 
    {
        String text = null;
        try {
            Object eval = xPath.evaluate(path, root, XPathConstants.NODE);
            Element element = Element.class.cast(eval);
            if ( element == null ) {
                System.out.println("XPath can't find an element: " + path);
            }
            text = element.getTextContent();
        } catch (XPathExpressionException e) {
        	System.out.println("Can't evaluate the xpath " + path);
        }
        return text;
    }
    
    public String getText(String path) 
    {
        return getText(document.getDocumentElement(), path);
    }

    public Element getElement(Element root, String path)
    {
        if ( document == null ) {
        	System.out.println("There is no document available. Did you parse one?");
        }
        
        Object eval;
        Node node = null;
        
        try {
            eval = xPath.evaluate(path, root, XPathConstants.NODE);
            node = Node.class.cast(eval);
        } catch (XPathExpressionException e) {
        	System.out.println("Can't evaluate the xpath " + path);
        }
        
        return Element.class.cast(node);

    }
    
    public Element getElement(String path) 
    {
        return getElement(document.getDocumentElement(), path);
    }
    
    /**
     * Get a list elements from xml file
     * @param root
     * @param path
     * @return
     * @throws XmlException
     */
    public List<Element> getElements(Element root, String path) 
    {
        if ( document == null ) {
        	System.out.println("There is no document available. Did you parse one?");
        }
        
        Object eval;
        NodeList nodes = null;
        
        try {
            eval = xPath.evaluate(path, root, XPathConstants.NODESET);
            nodes = NodeList.class.cast(eval);
        } catch (XPathExpressionException e) {
        	System.out.println("Can't evaluate the xpath " + path + e);
        }

        List<Element> elements = new ArrayList<Element>();
        for ( int index = 0; index < nodes.getLength(); index++ ) {
            elements.add(Element.class.cast(nodes.item(index)));
        }
        
        return elements;
    }
    
    public List<Element> getElements(String path) 
    {
        return getElements(document.getDocumentElement(), path);
    }
  
    public String getStringAttribute(String path, String name)
    {
        return getElements(path).get(0).getAttribute(name);
    }

    public String getStringAttribute(Element element, String name)
    {
        return element.getAttribute(name);
    }
    
    
    public void writeFileXML(String filepath)
    {
    	try
    	{
    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File(filepath));
		transformer.transform(source, result);
    	}catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    }
    /**
     * Get path file xml
     * @param filename
     * @return
     */
    private Document document = null;
    private final DocumentBuilderFactory builderFactory =
        DocumentBuilderFactory.newInstance();
    private final XPath xPath = XPathFactory.newInstance().newXPath();
}
