package com.batchRun.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.batchRun.DAO.CronTimerDAO;
import com.batchRun.bean.CronTimer;

public class App {

	static String[] str = {"classpath:META-INF/spring/job-config.xml","classpath:META-INF/spring/context-config.xml"};
	static String context = "classpath:META-INF/spring/context-config.xml";

	public static void main(String args[]) {
		try {
			String expression = "";
			ApplicationContext ctx = new ClassPathXmlApplicationContext(context);
			List<CronTimer> cronString = new ArrayList<CronTimer>();
			CronTimerDAO cronTimerDAO = (CronTimerDAO) ctx.getBean("cronDao");
					cronString = cronTimerDAO.cronExpression();

			for(CronTimer cronExpString : cronString) {
				expression = cronExpString.getCronExpression();
			}		
			
			String filepath = "src/main/resources/META-INF/spring/job-config.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			Element element = (Element) doc.getElementsByTagName("task:scheduled-tasks").item(0);
			if(element != null) {
				element.getParentNode().removeChild(element);
			}
			
			Node task = doc.getElementsByTagName("beans").item(0);
			Element taskTag = doc.createElement("task:scheduled-tasks");
			task.appendChild(taskTag);

			Element cronTag = doc.createElement("task:scheduled");
			cronTag.setAttribute("ref", "myScheduler");
			cronTag.setAttribute("method", "run");
			cronTag.setAttribute("cron", expression);
			taskTag.appendChild(cronTag);

			// write the content into xml file TransformerFactory transformerFactory =
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);

			System.out.println("Done");
			
			ctx = new ClassPathXmlApplicationContext(str);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}
}
