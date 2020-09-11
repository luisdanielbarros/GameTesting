package main;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import main.Camera;
import main.Handler;
import main.XMLReader;

public class MapHandler {
	private Handler handler;
	private Camera camera;
	private XMLReader xmlreader;
	MapHandler(Handler handler, Camera camera)
	{
		this.handler = handler;
		this.camera = camera;
		this.xmlreader = new XMLReader();
	}
	protected void loadMap(int id)
	{
		try {
			Map map = xmlreader.readXMLMapfile(id);
			handler.setCollisions(map.getCollisions());
			camera.setBottomLayer(map.getBottomLayer());
			camera.setTopLayer(map.getTopLayer());
			handler.addObject(xmlreader.ReadXMLNPCfile(1, handler));
			handler.addObject(xmlreader.readXMLWarpEventsfile(1, handler));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
