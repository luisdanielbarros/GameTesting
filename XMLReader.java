package main;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLReader {
	protected NPC[] ReadXMLNPCfile(int id, Handler handler) throws ParserConfigurationException, SAXException, IOException
	{
		//Obtains the path to the NPCs' XML file
		File XMLFile = new File(Game.DirectoryPath+"Files/Map/Maps.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(XMLFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("map");
		if (id >= nList.getLength()) throw new NullPointerException();
		Node nNode = nList.item(id);
		Element eElement = (Element) nNode;
		String path = "";
		if (eElement.getElementsByTagName("pathtoNPCs").getLength() > 0) path = eElement.getElementsByTagName("pathtoNPCs").item(0).getTextContent();
		else return null;
		XMLFile = new File(Game.DirectoryPath+"Files/Map/"+path);
		//Reads the NPCs' XML file
		List<NPC> Npcs = new ArrayList<NPC>();
		doc = dBuilder.parse(XMLFile);
		doc.getDocumentElement().normalize();
		nList = doc.getElementsByTagName("npc");
		for (int i = 0; i < nList.getLength(); i++)
		{
			int x = 0;
			int y = 0;
			int npcpathId = -1;
			NPCPath npcpath = NPCPath.Static;
			List<InteractionSentence> interaction = new ArrayList<InteractionSentence>();
			if (nList.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				eElement = (Element) nList.item(i);
				if (eElement.getElementsByTagName("x").getLength() > 0) x = Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent());
				if (eElement.getElementsByTagName("y").getLength() > 0) y = Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent());
				if (eElement.getElementsByTagName("npcpathId").getLength() > 0) 
				{
					npcpathId = Integer.parseInt(eElement.getElementsByTagName("npcpathId").item(0).getTextContent());
					if (npcpathId == 1) npcpath = NPCPath.ThreeXThreeAround;

				}
				if (eElement.getElementsByTagName("interaction").getLength() > 0)
				{
					NodeList nList2 = eElement.getElementsByTagName("interaction").item(0).getChildNodes();
					for (int j = 0; j < nList2.getLength(); j++)
					{
						if (nList2.item(j).getNodeType() == Node.ELEMENT_NODE)
						{
							eElement = (Element) nList2.item(j);
							if (eElement.getTagName().equals("default")) interaction.add(new InteractionSentenceDefault(eElement.getTextContent()));
							if (eElement.getTagName().equals("ynquestion"))
							{
								String question = eElement.getElementsByTagName("question").item(0).getTextContent();
								List<InteractionSentenceDefault> ynanswer = new ArrayList<InteractionSentenceDefault>();
								NodeList nList3 = eElement.getElementsByTagName("yanswer").item(0).getChildNodes();
								for (int k = 0; k < nList3.getLength(); k++)
								{
									ynanswer.add(new InteractionSentenceDefault(nList3.item(k).getTextContent()));
								}
								InteractionSentenceDefault[] yinteraction = ynanswer.toArray(new InteractionSentenceDefault[ynanswer.size()]);
								ynanswer.clear();
								nList3 = eElement.getElementsByTagName("nanswer").item(0).getChildNodes();
								for (int k = 0; k < nList3.getLength(); k++)
								{
									ynanswer.add(new InteractionSentenceDefault(nList3.item(k).getTextContent()));
								}
								InteractionSentenceDefault[] ninteraction = ynanswer.toArray(new InteractionSentenceDefault[ynanswer.size()]);
								interaction.add(new InteractionSentenceYNQuestion(question, yinteraction, ninteraction));
							}
						}
					}
				}
			}
			InteractionSentence[] Sentences = new InteractionSentence[interaction.size()];
			for (int j = 0; j < Sentences.length; j++)
			{
				Sentences[j] = interaction.get(j);
			}
			Npcs.add(new NPCInteractable(x, y, handler, npcpath, new Interaction(Sentences, InteractionId.NPC)));
		}
		return Npcs.toArray(new NPC[Npcs.size()]);
	}
	protected WarpEvent[] readXMLWarpEventsfile(int id, Handler handler) throws ParserConfigurationException, SAXException, IOException
	{
		//Obtains the path to the Map's XML file
		File XMLFile = new File(Game.DirectoryPath+"Files/Map/Maps.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(XMLFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("map");
		if (id >= nList.getLength()) throw new NullPointerException();
		Node nNode = nList.item(id);
		Element eElement = (Element) nNode;
		String path = "";
		if (eElement.getElementsByTagName("pathtoWarpEvents").getLength() > 0) path = eElement.getElementsByTagName("pathtoWarpEvents").item(0).getTextContent();
		else return null;
		XMLFile = new File(Game.DirectoryPath+"Files/Map/"+path);
		//Reads the WarpEvents' XML file
		List<NPC> Npcs = new ArrayList<NPC>();
		doc = dBuilder.parse(XMLFile);
		doc.getDocumentElement().normalize();
		nList = doc.getElementsByTagName("warpevent");
		List<WarpEvent> WarpEvents = new ArrayList<WarpEvent>();
		for (int i = 0; i < nList.getLength(); i++)
		{
			int X = 0, Y = 0, DestinationX = 0, DestinationY = 0, MapId = 0;
			WarpEventId Id = WarpEventId.Intra;
			WarpEventAnimationId AnimationId = WarpEventAnimationId.DefaultInternal;
			if (nList.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				eElement = (Element) nList.item(i);
				if (eElement.getElementsByTagName("x").getLength() > 0) X = Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent());
				if (eElement.getElementsByTagName("y").getLength() > 0) Y = Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent());
				if (eElement.getElementsByTagName("destinationx").getLength() > 0) DestinationX = Integer.parseInt(eElement.getElementsByTagName("destinationx").item(0).getTextContent());
				if (eElement.getElementsByTagName("destinationy").getLength() > 0) DestinationY = Integer.parseInt(eElement.getElementsByTagName("destinationy").item(0).getTextContent());

				if (eElement.getElementsByTagName("mapId").getLength() > 0) 
				{
					Id = WarpEventId.Inter;
					MapId = Integer.parseInt(eElement.getElementsByTagName("mapId").item(0).getTextContent());
				}
				if (eElement.getElementsByTagName("warpeventanimationId").getLength() > 0)
				{
					int warpeventanimationId = Integer.parseInt(eElement.getElementsByTagName("warpeventanimationId").item(0).getTextContent());
					switch (warpeventanimationId)
					{
						case 1: 
							AnimationId = WarpEventAnimationId.DefaultExternal;
							break;
					}
					
				}
			}
			if (Id == WarpEventId.Inter) WarpEvents.add(new WarpEventInter(X, Y, DestinationX, DestinationY, AnimationId, MapId));
			else WarpEvents.add(new WarpEventIntra(X, Y, DestinationX, DestinationY, AnimationId));
		}
		return WarpEvents.toArray(new WarpEvent[WarpEvents.size()]);
	}
	protected ImageIcon[] readXMLTilefile(int id) throws ParserConfigurationException, SAXException, IOException
	{
		File XMLFile = new File(Game.DirectoryPath+"Files/Tileset/Tilesets.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(XMLFile);
		doc.getDocumentElement().normalize();
		//Gets a list of all the tilesets
		NodeList nList = doc.getElementsByTagName("tileset");
		if (id > nList.getLength()) throw new NullPointerException();
		//Gets the required tileset
		Node nNode = nList.item(id);
		//Creates a list of all the nodes inside the required tileset and counts all its elements
		NodeList nList2 = nNode.getChildNodes();
		int nCount = 0;
		for (int i = 0; i < nList2.getLength(); i++)
		{
			if (nList2.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				nCount++;
			}
		}
		ImageIcon[] tiles = new ImageIcon[nCount];
		for (int i = 0; i < tiles.length; i++) {
			Element eElement = (Element) nNode;
			tiles[i] = new ImageIcon(Game.DirectoryPath+"Files/Tileset/"+eElement.getElementsByTagName("pathtoTile").item(i).getTextContent());
		}
		return tiles;
	}
	protected Map readXMLMapfile(int id) throws ParserConfigurationException, SAXException, IOException
	{
		File XMLFile = new File(Game.DirectoryPath+"Files/Map/Maps.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(XMLFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("map");
		if (id >= nList.getLength()) throw new NullPointerException();
		Node nNode = nList.item(id);
		Element eElement = (Element) nNode;
		String pathtoBottomLayer = null;
		String pathtoTopLayer = null;
		String pathtoCollisions = null;
		int TilesetId = -1;
		if (eElement.getElementsByTagName("pathtoBottomLayer").getLength() > 0) pathtoBottomLayer = eElement.getElementsByTagName("pathtoBottomLayer").item(0).getTextContent();
		if (eElement.getElementsByTagName("pathtoTopLayer").getLength() > 0) pathtoTopLayer = eElement.getElementsByTagName("pathtoTopLayer").item(0).getTextContent();
		if (eElement.getElementsByTagName("pathtoCollisions").getLength() > 0) pathtoCollisions = eElement.getElementsByTagName("pathtoCollisions").item(0).getTextContent();
		if (eElement.getElementsByTagName("TilesetId").getLength() >= 0) 
		{
			TilesetId = Integer.parseInt(eElement.getElementsByTagName("TilesetId").item(0).getTextContent());
		}
		Tileset Tileset = new Tileset(readXMLTilefile(TilesetId));
		//Layers
		//Bottom Layer
		Image BottomLayer = null;
		if (pathtoBottomLayer != null) BottomLayer = MapGenImages.Draw(Game.DirectoryPath+"Files/Map/"+pathtoBottomLayer, Tileset);
		//Top Layer
		Image TopLayer = null;
		if (pathtoTopLayer != null) TopLayer = MapGenImages.Draw(Game.DirectoryPath+"Files/Map/"+pathtoTopLayer, Tileset);
		//Collisions
		Collisions[][] Collisions = null;
		if (pathtoCollisions != null) 
		{
			Collisions = MapGenCollisions.Set(Game.DirectoryPath+"Files/Map/"+pathtoCollisions);
		}
		return new Map(BottomLayer, TopLayer, Collisions);
	}
}
