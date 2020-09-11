package main;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import main.Game.STATE;

public class KeyInput extends KeyAdapter{
	private Game game;
	private Handler handler;	
	Timer t = new Timer();
	Timer tt = new Timer();
	KeyInput(Game game, Handler handler)
	{
		this.handler = handler;
		this.game = game;
		t = null;
	}
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		if (game.GameState == STATE.GameDefault)
		{
			if ((key == KeyEvent.VK_SPACE || key == KeyEvent.VK_W || key == KeyEvent.VK_S || key == KeyEvent.VK_D || key == KeyEvent.VK_A) == false) return;
			for (int i = 0; i < handler.getObjects().size(); i++)
			{
				GameObject tempObject = handler.getObjects().get(i);	
				if (tempObject.getID() == GameObjectId.Player)
				{
					if (key == KeyEvent.VK_SPACE && (tempObject.getX() % Game.TileWidth) == 0 && (tempObject.getY() % Game.TileHeight) == 0) 
					{
						for (int j = 0; j < handler.getObjects().size(); j++)
						{
							Player tempObject2 = (Player) tempObject;
							int x = 0, y = 0;
							switch (tempObject2.getDirection())
							{
								case 1:
										x = (int)tempObject.getX();
										y = (int)(tempObject.getY() - Game.TileHeight);
									break;
								case 2:
										x = (int)(tempObject.getX() + Game.TileWidth);
										y = (int)tempObject.getY();
									break;
								case 3:
										x = (int)tempObject.getX();
										y = (int)(tempObject.getY() + Game.TileHeight);
									break;
								case 4:
										x = (int)(tempObject.getX() - Game.TileWidth);
										y = (int)(tempObject.getY());
									break;
								default:
							}
							if (handler.getObjects().get(j).getID() == GameObjectId.NPCInteractable && new Rectangle(x, y, tempObject.getWidth(), tempObject.getHeight()).getBounds().intersects(handler.getObjects().get(j).getBounds())) game.getInteractionHandler().setActiveInteraction(((NPCInteractable)handler.getObjects().get(j)).getInteraction());
						}
					}
					if (key == KeyEvent.VK_W && (tempObject.getX() % Game.TileWidth) == 0 && (tempObject.getY() % Game.TileHeight) == 0) 
					{
						tempObject.setvelY(-(Game.TileHeight / 8));
						tempObject.setvelX(0);
						((Player)tempObject).setDirection(1);
						((Player)tempObject).setMoving(true);
					}
					if (key == KeyEvent.VK_S && (tempObject.getX() % Game.TileWidth) == 0 && (tempObject.getY() % Game.TileHeight) == 0)
					{
		            	tempObject.setvelY(Game.TileHeight / 8);
					    tempObject.setvelX(0);
						((Player)tempObject).setDirection(3);
						((Player)tempObject).setMoving(true);
					}
					if (key == KeyEvent.VK_D && (tempObject.getX() % Game.TileWidth) == 0 && (tempObject.getY() % Game.TileHeight) == 0)
					{
						tempObject.setvelY(0);
						tempObject.setvelX(Game.TileWidth / 8);
						((Player)tempObject).setDirection(2);
						((Player)tempObject).setMoving(true);
					}
					if (key == KeyEvent.VK_A && (tempObject.getX() % Game.TileWidth) == 0 && (tempObject.getY() % Game.TileHeight) == 0)
					{
						tempObject.setvelY(0);
						tempObject.setvelX(-(Game.TileWidth / 8));
						((Player)tempObject).setDirection(4);
						((Player)tempObject).setMoving(true);
					}
				}
			}
			if (key == KeyEvent.VK_ESCAPE) System.exit(1);
		}
		else if (game.GameState == STATE.GameInteracting)
		{
			if (key == KeyEvent.VK_SPACE) game.getInteractionHandler().UseSpace();
			if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) game.getInteractionHandler().UseUpArrow();
			if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) game.getInteractionHandler().UseDownArrow();
		}
	}
	public void keyReleased(KeyEvent e)
	{
		if (game.GameState == STATE.GameDefault)
		{
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_W || key == KeyEvent.VK_S || key == KeyEvent.VK_D || key == KeyEvent.VK_A)
			{
				for (int i = 0; i < handler.getObjects().size(); i++)
				{
					GameObject tempObject = handler.getObjects().get(i);
					if (tempObject.getID() == GameObjectId.Player)
					{
						((Player)tempObject).setMoving(false);
					}
				}
			}
		}
	}
}
