package cn.com.bmsoft.dxms.dao.impl;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLOperator {
	
    public XMLOperator() {
    }
    
    public String getExecuteSQLInternal(String filePath, String fileName, String executeName){
    	String executeSQL = null;
    	//String path = this.getClass().getClassLoader().getResource("/").getPath().replace("classes", "executesqlinternal") + fileName + ".xml";
    	String path = filePath + fileName + ".xml";
    	try{
    		File file = new File(path);
        	SAXReader reader = new SAXReader();
        	Document document = reader.read(file);
        	Element root = document.getRootElement();
        	List<Element> listE = root.elements("execute");
        	for(Element e : listE){
        		if(e.elementTextTrim("executeName").equals(executeName)){
        			executeSQL = e.elementTextTrim("executeSQL");
        			break;
        		}
        	}
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return executeSQL;
    }
    
}
