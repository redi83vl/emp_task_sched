/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etc;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author user
 */
public class ConnParam
{
	public String dbdrv;
	public String dburl;
	public String dbusr;
	public String dbpwd;
	public String user;

	public ConnParam(String xmlFileName)
	{
		
		this.dbdrv = this.dburl = this.dbusr = this.dbpwd = this.user = "";
		
		try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(xmlFileName));
			
			NodeList nodeList = doc.getElementsByTagName("param");
			if(nodeList.getLength()==0)
				return;
			
			Element element = (Element) nodeList.item(0);
			this.dbdrv = element.getAttribute("dbdrv");
			this.dburl = element.getAttribute("dburl");
			this.dbusr = element.getAttribute("dbusr");
			this.dbpwd = element.getAttribute("dbpwd");
			this.user = element.getAttribute("user");
		}
		catch (ParserConfigurationException | SAXException | IOException ex)
		{
			Logger.getLogger(ConnParam.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	
}
