package config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public abstract class DataConfiguration implements Comparable<DataConfiguration>
{
    public abstract Element toElement(Document document) ;
    public abstract void fromElement(XmlHelper xml, Element base) ;
    
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    
    public String getTag()
    {
        return this.tag;
    }
       
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        // The objects are the same.
        if ( this == obj ) {
            return true;
        }
        
        if ( obj == null ) {
            return false;
        }

        // Comparing to a string object.
        if ( obj.getClass() == String.class ) {
            if ( tag == null ) {
                return false;
            }
            return tag.equals(obj);
        }

        // Otherwise they must both be the same class type.
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        
        // Compare the tags
        DataConfiguration other = (DataConfiguration) obj;
        if ( tag == null && other.tag == null ) {
            return true;
        }
        if ( other.tag == null ) {
            return false;
        }
        
        return tag.equals(other.tag);
    }
    
    @Override
    public int compareTo(DataConfiguration other)
    {
        if ( tag == null && other.tag == null ) return 0;
        if ( tag == null && other.tag != null ) return -1;
        if ( tag != null && other.tag == null ) return 1;
        return tag.compareTo(other.tag);
    }
    
    private String tag;
}
