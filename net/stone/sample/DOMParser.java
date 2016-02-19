package stone.sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.text.DocumentFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

public class DOMParser {
	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance(); 
	String filePath;
	public HashMap names = new HashMap();
	//HashMap names=new 
	Document document;
	public Document parse(String filePath) { 
		Document document = null;
		this.filePath = filePath;
	    try { 
	    	//DOM parser instance 
	        DocumentBuilder builder = builderFactory.newDocumentBuilder(); 
	        //parse an XML file into a DOM tree 
	        document = builder.parse(new File(filePath)); 
	    } catch (ParserConfigurationException e) { 
	         e.printStackTrace();  
	    } catch (SAXException e) { 
	         e.printStackTrace(); 
	    } catch (IOException e) { 
	         e.printStackTrace(); 
	    } 
	    this.document = document;
	    return document; 
	} 
	public EntryActivity parseActivity(Node node,EntryActivity activity){
		NodeList nodelist = node.getChildNodes();
		for(int i=0;i<nodelist.getLength();i++){
			Node child = nodelist.item(i);
			//map.put(child.getNodeName(), child);
			if(child.getNodeName().equals("intent-filter")){
				NodeList filterChildren = child.getChildNodes();
				for(int j=0;j<filterChildren.getLength();j++){
					if (filterChildren.item(j).getNodeName().equals("action")){
						String name = filterChildren.item(j).getAttributes().getNamedItem("android:name").getNodeValue();
						activity.addFilterByName(name);
					}
				}
				
			}
		}
		
		return activity;
	}
	public HashMap parseChildNodes(Node node){
		HashMap map = new HashMap();
		NodeList nodelist = node.getChildNodes();
		
		for(int i=0;i<nodelist.getLength();i++){
			Node child = nodelist.item(i);
			map.put(child.getNodeName(), child);
		}
		return map;
	}
	public void switchMain(Document document,String toName){
		//找出main//删除之
		Iterator iterator=names.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry<String,Object> item = (java.util.Map.Entry<String, Object>) iterator.next();
			EntryActivity activity = (EntryActivity) item.getValue();
			if (activity.isMain()){
				Node node =(Node) activity.getData();
				NodeList list = node.getChildNodes();
				for (int i = 0; i < list.getLength(); i++) {
					if (list.item(i).getNodeName().equals("intent-filter")){
						node.removeChild(list.item(i));
					}
				}
			}
		}
		

		
		
		//添加main
		//this.document
		EntryActivity activity = getActivity(document, toName);
		//activity.setAsMainActivity();
		Node node =(Node) activity.getData();
		//removeRecursively(, name);
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeName().equals("intent-filter")){
				node.removeChild(list.item(i));
			}
		}
		Node ele = this.addNode_main(document,node);
		//node.appendChild(ele);
		save(document);
	}
	public void removeRecursively(Node node, String name) {
		if (node.getNodeName().equals(name)) {
            node.getParentNode().removeChild(node);
        }
	}
	private Node addNode_main(Document document,Node activityNode){
		
		
		//Node ele = document.createTextNode("<intent-filter><action android:name=\"android.intent.action.MAIN\"/><category android:name=\"android.intent.category.LAUNCHER\"/></intent-filter>");
		Node ele = document.createElement("intent-filter");
		activityNode.appendChild(ele);
		Element action =  document.createElement("action");
		action.setAttribute("android:name", "android.intent.action.MAIN");
		
		ele.appendChild(action);
		Element category = document.createElement("category");
		category.setAttribute("android:name", "android.intent.category.LAUNCHER");
		ele.appendChild(category);
		//node node =this.document.createElementNS(arg0, arg1)
		
		return ele;
	}
	public boolean containsKey(HashMap map,String str){
		Iterator iter = map.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry) iter.next();
			Object name = entry.getKey();
			if(name.toString().equals(str)){
				return true;
			}
		}
		
		return false;
	}
	public EntryActivity addActivity(Document document,String name){
		if(names.containsKey(name)){
			EntryActivity entry = getActivity(document, name); 
			return entry;
		}
		Node activites = getApplication(document);
		Element ele = document.createElement("activity");

		ele.setAttribute("android:label","@string/app_name");//不一定百分百有@string/app_name
		ele.setAttribute("android:name", name);
		activites.appendChild(ele);
		
		EntryActivity entry = new EntryActivity();
		entry.setName(name);
		entry.setData(ele);
		names.put(name, entry);
		//NodeList application = document.getDocumentElement().getElementsByTagName("application");
		//application.item(0).appendChild(activity);
		save(document);
		
		return entry;
	}
	public void save(Document document){
		TransformerFactory tf = TransformerFactory.newInstance();
		String fileName = this.filePath;
		try {
		    Transformer transformer = tf.newTransformer();
		    DOMSource source = new DOMSource(document);
		    //transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312");
		    transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		    PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
		    StreamResult result = new StreamResult(pw);
		    transformer.transform(source, result);
		    System.out.println("生成XML文件成功!");
		} catch (TransformerConfigurationException e) {
		    System.out.println(e.getMessage());
		} catch (IllegalArgumentException e) {
		    System.out.println(e.getMessage());
		} catch (FileNotFoundException e) {
		    System.out.println(e.getMessage());
		} catch (TransformerException e) {
		    System.out.println(e.getMessage());
		}
	}
	public EntryActivity getActivity(Document document,String name){
		Node application = getApplication(document);
		NodeList nodes = application.getChildNodes();
        for (int i=0; i < nodes.getLength(); i++) 
        { 
        	Node node = nodes.item(i);
        	System.out.println(node.getNodeName());
        	if(node.getNodeName().equals("activity")){
        		String activityName = node.getAttributes().getNamedItem("android:name").getNodeValue();
        		if(activityName.equals(name))
        		{
        			EntryActivity activity = new EntryActivity();
        			activity.setName(activityName);
        			activity.setData(node);
        			this.parseActivity(node, activity);
        			return activity;
        		}
        	}
        }
        
        return null;
	}
	public Node getApplication(Document document){
		Element rootElement = document.getDocumentElement(); 
		NodeList application = rootElement.getElementsByTagName("application");
		return application.item(0);
	}
}
