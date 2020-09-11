package main;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObject{
	private Handler handler;
	private int Direction;
	private boolean Moving;
	Player(int x, int y, Handler handler) {
		super(x, y);
		setWidth((int)Game.TileWidth);
		setHeight((int)Game.TileHeight);
		setId(GameObjectId.Player);
		this.handler = handler;
		Direction = 1;
		Game.getCamera().setRenderfunctionsXY((int)(-getX() + (Game.ScreenWidth / 2)), (int)(-getY() + (Game.ScreenHeight / 2) + Game.TileHeight));
	}
	protected void tick() {
		//if the player is standing still on one tile
		if (getX() % Game.TileWidth == 0 && getY() % Game.TileHeight == 0)
		{
			if (Moving == false)
			{
				setvelX(0);
				setvelY(0);
			}
			//the game will verify if the next tile, to which the player intends to move, is free or not
			//if it's free the player will be allowed to move there
			if ((getvelX() != 0 || getvelY() != 0) && handler.DoCollisions(this) == true)
			{
//				System.out.println("After checking the collisions velX = " + getvelX() + " velY = " + getvelY());
				float[] warpcoordinates = handler.DoWarpEvents(this);
				if (warpcoordinates != null)
				{
					setX(warpcoordinates[0]);
					setY(warpcoordinates[1]);
					setvelX(0);
					setvelY(0);
					Game.getCamera().setRenderfunctionsXY((int)(-getX() + (Game.ScreenWidth / 2)), (int)(-getY() + (Game.ScreenHeight / 2) + Game.TileHeight));
					return;
				}
				setX(getX() + getvelX());
    			setY(getY() + getvelY());
    			Game.getCamera().setRenderfunctionsXY((int)(-getX() + (Game.ScreenWidth / 2)), (int)(-getY() + (Game.ScreenHeight / 2) + Game.TileHeight));
			}
			//if it's not free the player will not be allowed
			else
			{
				setvelX(0);
				setvelY(0);
			}
		}
		//if the player is not standing still, meaning he already went through the previous procedure
		//he will be allowed to move, until he reaches the center of another tile
		else
		{
			setX(getX() + getvelX());
			setY(getY() + getvelY());
			Game.getCamera().setRenderfunctionsXY((int)(-getX() + (Game.ScreenWidth / 2)), (int)(-getY() + (Game.ScreenHeight / 2) + Game.TileHeight));
		}
	}
	protected void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect((Game.ScreenWidth / 2), Game.ScreenHeight / 2 + (int)Game.TileHeight, getWidth(), getHeight());
	}
	protected void setDirection(int _Direction)
	{
		Direction = _Direction;
	}
	protected void setMoving(boolean _Moving)
	{
		Moving = _Moving;
	}
	protected int getDirection()
	{
		return Direction;
	}
	protected boolean getMoving()
	{
		return Moving;
	}
}
