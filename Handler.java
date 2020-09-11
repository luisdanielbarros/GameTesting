package main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import main.Game.STATE;

public class Handler {
	private Game game;
	private LinkedList<GameObject> Objects = new LinkedList<GameObject>();
	private Collisions[][] Collisions;
	private LinkedList<WarpEvent> WarpEvents = new LinkedList<WarpEvent>();
	Handler(Game _game)
	{
		game = _game;
	}
	protected void tick()
	{
		if (game.GameState == STATE.GameDefault)
		{
			for (int i = 0; i < Objects.size(); i++)
			{
				GameObject tempObject = Objects.get(i);
				tempObject.tick();
			}
		}
	}
	protected void render(Graphics g)
	{
		for (int i = 0; i < Objects.size(); i++)
		{
			GameObject tempObject = Objects.get(i);
			tempObject.render(g);
		}
	}
	protected GameObject setplayerObject()
	{
		for (int i = 0; i < Objects.size(); i++)
		{
			GameObject tempObject = Objects.get(i);	
			if (tempObject.getID() == GameObjectId.Player)
			{
				return tempObject;
			}
		}
		return null;
	}
	protected void addObject(GameObject object)
	{
		this.Objects.add(object);
	}
	protected void addObject(WarpEvent object)
	{
		this.WarpEvents.add(object);
	}
	protected void addObject(WarpEvent[] objects)
	{
		for (int i = 0; i < objects.length; i++)
		{
			addObject(objects[i]);
		}
	}
	protected void addObject(NPC[] objects)
	{
		for (int i = 0; i < objects.length; i++)
		{
			addObject(objects[i]);
		}
	}
	protected void removeObject(Object object)
	{
		this.Objects.remove(object);
	}
	//Gets
	protected LinkedList<GameObject> getObjects()
	{
		return Objects;
	}
	//Collision related
	protected void setCollisions(Collisions[][] _Collisions)
	{
		Collisions = _Collisions;
	}
	protected boolean DoCollisions(GameObject object)
	{
		//Checks if the object collides with any other object
		for (int i = 0; i < Objects.size(); i++)
		{
			if (object != Objects.get(i) && new Rectangle((int)(object.getX() + object.getvelX()), (int)(object.getY() + object.getvelY()), object.getWidth(), object.getHeight()).intersects(Objects.get(i).getBounds())) return false;
		}
		//Checks if the object collides with any tile or with the edges of the map
		if (object.getvelX() < 0 && object.getvelY() == 0)
		{
			if (object.getX() >= Game.TileWidth)
			{
				return getTileswalkable((int)(object.getX() / Game.TileWidth) - 1, (int)(object.getY() / Game.TileHeight));
			}
			else if (object.getX() <= 0) return false;
			else return false;
		}
		else if (object.getvelX() > 0 && object.getvelY() == 0)
		{
			if (object.getX() < (int)((Collisions[(int)(object.getY() / Game.TileHeight)].length * Game.TileWidth)) - (Game.TileWidth * 2))
			{
				return getTileswalkable((int)(object.getX() / Game.TileWidth) + 1, (int)(object.getY() / Game.TileHeight));
			}
			else return false;
		}
		else if (object.getvelY() < 0 && object.getvelX() == 0)
		{
			if (object.getY() >= Game.TileHeight)
			{
				return getTileswalkable((int)(object.getX() / Game.TileWidth), (int)(object.getY() / Game.TileHeight) - 1);
			}
			else return false;
		}
		else if (object.getvelY() > 0 && object.getvelX() == 0)
		{
			if (object.getY() < (int)((Collisions.length * Game.TileHeight) - (Game.TileHeight * 2)))
			{
				return getTileswalkable((int)(object.getX() / Game.TileWidth), (int)(object.getY() / Game.TileHeight) + 1);
			}
			else return false;
		}
		else return true;
	}
	protected boolean getTileswalkable(int x, int y)
	{	
		return Collisions[x][y].getwalkable();
	}
	//Warp Events related
	protected void setWarpEvents(LinkedList<WarpEvent> _WarpEvents)
	{
		WarpEvents = _WarpEvents;
	}
	protected float[] DoWarpEvents(GameObject object)
	{
		System.out.println(WarpEvents.size() + " Warp events, Player's coordinates -> X = " + object.getX()/Game.TileWidth + " Y = " + object.getY()/Game.TileHeight);
		float x = -1, y = -1;
		//Checks if the object collides with any tile or with the edges of the map
		if (object.getvelX() < 0 && object.getvelY() == 0)
		{
			x = object.getX() - Game.TileWidth;
			y = object.getY();
		}
		else if (object.getvelX() > 0 && object.getvelY() == 0)
		{
			x = object.getX() + Game.TileWidth;
			y = object.getY();
		}
		else if (object.getvelY() < 0 && object.getvelX() == 0)
		{
			x = object.getX();
			y = object.getY() - Game.TileHeight;
		}
		else if (object.getvelY() > 0 && object.getvelX() == 0)
		{
			x = object.getX();
			y = object.getY() + Game.TileHeight;
		}
		for (int i = 0; i < WarpEvents.size(); i++)
		{
			WarpEvent tempObject = WarpEvents.get(i);
			if (x == tempObject.getX() * Game.TileWidth && y == tempObject.getY() * Game.TileHeight)
			{
				return new float[] { tempObject.getDestinationX() * Game.TileWidth, tempObject.getDestinationY() * Game.TileHeight };
			}
		}
		return null;
	}
}
