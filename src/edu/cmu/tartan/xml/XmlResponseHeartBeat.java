package edu.cmu.tartan.xml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XmlResponseHeartBeat extends XmlResponse {

	private String idStr;

	public XmlResponseHeartBeat() {
		msgType = XmlMessageType.HEART_BEAT;
	}
	
	public String getId() {
		return idStr; 
	}
	

	@Override
	public XmlParseResult doYourJob(Document doc) {
		
		return parsingHeartBeat(doc);
	}
	

	public XmlParseResult parsingHeartBeat(Document doc) {
		

		NodeList nList;
		XmlParseResult result = XmlParseResult.SUCCESS; 
		
		nList = getNodeListOfGivenTag("common_info", doc);
		idStr = getAttributeValueAtNthTag("user_id", nList, 0);	//id should be unique. 

		if(idStr == null) {
			return XmlParseResult.INVALID_DATA;
		}
		
		return result;
	}
	
}
