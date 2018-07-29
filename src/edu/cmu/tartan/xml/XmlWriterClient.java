package edu.cmu.tartan.xml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.ParserConfigurationException;

public class XmlWriterClient extends XmlWriter {

	public String makeXmlForLogin(String id, String pw, XmlLoginRole role) {
		String xmlString = null;
		
		try {
			//<login_info id="takhh" pw="awefaweg14ro4aw3"/>	<!-- password is encrypted -->
			startWritingXml(XmlMessageType.REQ_LOGIN, "client", "server"); 			
			addChildElement("login_info");
			setAttributeToElement("id", id);
			setAttributeToElement("pw", pw);
			setAttributeToElement("role", role.name().toLowerCase());

			xmlString = convertDocumentToString();
			
		} catch (ParserConfigurationException e) {
			gameLogger.severe("ParserConfigurationException");
		} 
		
		gameLogger.info(xmlString);
		
		return xmlString; 
	}

	public String makeXmlForAddUser(String id, String pw) {
		String xmlString = null;
		
		try {
			//<user_info id="takhh" pw="awefaweg14ro4aw3"/>	<!-- password is encrypted -->
			startWritingXml(XmlMessageType.ADD_USER, "client", "server"); 			
			addChildElement("user_info");
			setAttributeToElement("id", id);
			setAttributeToElement("pw", pw);
			
			xmlString = convertDocumentToString();
			
		} catch (ParserConfigurationException e) {
			gameLogger.severe("ParserConfigurationException");
		} 
		
		gameLogger.info(xmlString);
		
		return xmlString; 
	}
	
	public String makeXmlForUploadMap(String fileName) {
		String xmlString = null;
		
		try{
			xmlString = readAllBytes(fileName);
		}
		catch (IOException e) {
			gameLogger.severe("IOException");
		}

		gameLogger.info(xmlString);
		
		return xmlString; 
	}
	
	private String readAllBytes(String filePath) throws IOException{
	    String content = "";
        content = new String (Files.readAllBytes( Paths.get(filePath)));
	    
	    return content;
	}
}