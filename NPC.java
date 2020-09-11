package main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

public abstract class NPC extends GameObject{
	protected Handler handler;
	@SuppressWarnings("unused")
	//Variables used to store the NPC's direction (stored as an integer with 4 possible values), and the ticks left until it should walk again
	private int Direction, Walktimer;
	//Variables used to store the NPC's walking pattern and its spawn point origin (which is used by the previous variable)
	protected NPCPath Path;
	protected float OriginX, OriginY;
	//Variables used to store the NPC's sprites
	List<ImageIcon> IdleSprites;
	NPC(int x, int y, Handler _handler, NPCPath _Path) {
		super(x, y);
		handler = _handler;
		Path = _Path;
		OriginX = x * Game.TileWidth;
		OriginY = y * Game.TileHeight;
		IdleSprites = new ArrayList<ImageIcon>();
		IdleSprites.add(new ImageIcon(Game.DirectoryPath+"/Files/NPC/SKins/Man/Idle-1.png"));
	}
	@Override
	protected void tick() {
		if (1 == 1) return;
		if (Path == NPCPath.Static) return;
		//if the npc is standing still on one tile
		if (getX() % Game.TileWidth == 0 && getY() % Game.TileHeight == 0)
		{
			//if the npc is moving, stop it at the center of the tile
			setvelX(0);
			setvelY(0);
			//the npc will start counting the ticks until its next movement
			Walktimer++;
			//if it's time for its next movement
			if (Walktimer >= 100)
			{
				Walktimer = 0;
				walk();
			}
			//if the target tile is free, the npcs will be allowed to move there
			if (handler.DoCollisions(this) == true)
			{
				setX(getX() + getvelX());
    		setY(getY() + getvelY());
			}
			//if it's not free the npc will not be allowed
			else
			{
				setvelX(0);
				setvelY(0);
			}
			if (getvelX() == 0)
			{
				if (getvelY() < 0) Direction = 1;
				else if (getvelY() > 0) Direction = 3;
			}
			else if (getvelY() == 0)
			{
				if (getvelX() > 0) Direction = 2;
				else if (getvelX() < 0) Direction = 4;
			}
		}
		//if the npc is not standing still, meaning he already went through the previous procedure
		//he will be allowed to move, until he reaches the center of another tile
		else
		{
			setX(getX() + getvelX());
			setY(getY() + getvelY());
		}
	}
	private void walk()
	{
		Random rnd = new Random();
		//the npc will move accordingly with its movement pattern
		if (Path == NPCPath.ThreeXThreeAround)
		{
			if (OriginX == getX() && OriginY == getY())
			{
				int rndint = rnd.nextInt(4);
				switch (rndint) {
				case 0:
					setvelX(0);
					setvelY(-(Game.TileHeight / 8));
					break;
				case 1:
					setvelX(Game.TileWidth / 8);
					setvelY(0);
					break;
				case 2:
					setvelX(0);
					setvelY(Game.TileHeight / 8);
					break;
				case 3:
					setvelX(-(Game.TileWidth / 8));
					setvelY(0);
					break;
				}
			}
			else if (OriginX == getX() && OriginY != getY())
			{
				int rndint = rnd.nextInt(2);
				switch (rndint) {
				case 0:
					setvelX(Game.TileWidth / 8);
					setvelY(0);
					break;
				case 1:
					setvelX(-(Game.TileWidth / 8));
					setvelY(0);
					break;
				}
			}
			else if (OriginX != getX() && OriginY == getY())
			{
				int rndint = rnd.nextInt(2);
				switch (rndint) {
				case 0:
					setvelX(0);
					setvelY(-(Game.TileHeight / 8));
					break;
				case 1:
					setvelX(0);
					setvelY(Game.TileHeight / 8);
					break;
				}
			}
			else if (OriginX != getX() && OriginY != getY())
			{
				int rndint = rnd.nextInt(2);
				if (getX() > OriginX && getY() > OriginY)
				{
					switch (rndint) {
					case 0:
						setvelX(-(Game.TileWidth / 8));
						setvelY(0);
						break;
					case 1:
						setvelX(0);
						setvelY(Game.TileHeight / 8);
						break;
					}
				}
				else if (getX() > OriginX && getY() < OriginY)
				{
					switch (rndint) {
					case 0:
						setvelX(-(Game.TileWidth / 8));
						setvelY(0);
						break;
					case 1:
						setvelX(0);
						setvelY(-(Game.TileHeight / 8));
						break;
					}
				}
				else if (getX() < OriginX && getY() > OriginY)
				{
					switch (rndint) {
					case 0:
						setvelX(Game.TileWidth / 8);
						setvelY(0);
						break;
					case 1:
						setvelX(0);
						setvelY(-(Game.TileHeight / 8));
						break;
					}
				}
				else if (getX() < OriginX && getY() < OriginY)
				{
					switch (rndint) {
					case 0:
						setvelX(Game.TileWidth / 8);
						setvelY(0);
						break;
					case 1:
						setvelX(0);
						setvelY(Game.TileHeight / 8);
						break;
					}
				}
			}
		}
	}
	@Override
	protected void render(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect((int)getX() + Game.getCamera().getRenderfunctionsX(), (int)getY() + Game.getCamera().getRenderfunctionsY(), getWidth(), getHeight());
	}
}
