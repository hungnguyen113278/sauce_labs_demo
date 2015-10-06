package config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DataSetConfiguration extends DataConfiguration {

	@Override
	public Element toElement(Document document)  {
		Element data = document.createElement("Data");
		data.setAttribute("role", getRole());
        Element users = document.createElement("Users");
        data.appendChild(users);
        Element user = document.createElement("user");
        users.appendChild(user);
        Element username = document.createElement("Username");
        username.setTextContent(getUsername());
        user.appendChild(username);
        
        Element password = document.createElement("Password");
        password.setTextContent(getPassword());
        user.appendChild(password);
		
		return data;
	}

	@Override
	public void fromElement(XmlHelper xml, Element base)  {
		setRole(base.getAttribute("role"));
		setUsername(xml.getText(base, "Username"));
		setPassword(xml.getText(base, "Password"));
	}

	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}


	private String password;
	private String username;
	private String role;
}
