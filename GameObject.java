package main;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	private float x, y;
	private int width, height;
	private GameObjectId id;
	private float velX, velY;
	GameObject(int x, int y)
	{
		this.x = x * Game.TileWidth;
		this.y = y * Game.TileHeight;
	}
	protected abstract void tick();
	protected abstract void render(Graphics g);
	protected void setX(float x)
	{
		this.x = x;
	}
	protected void setY(float y)
	{
		this.y = y;
	}
	protected void setId(GameObjectId id)
	{
		this.id = id;
	}
	protected void setWidth(int width)
	{
		this.width = width;
	}
	protected void setHeight(int height)
	{
		this.height = height;
	}
	protected void setvelX(float f)
	{
		this.velX = f;
	}
	protected void setvelY(float f)
	{
		this.velY = f;
	}
	protected float getX()
	{
		return x;
	}
	protected float getY()
	{
		return y;
	}
	protected int getWidth()
	{
		return width;
	}
	protected int getHeight()
	{
		return height;
	}
	protected GameObjectId getID()
	{
		return id;
	}
	protected float getvelX()
	{
		return velX;
	}
	protected float getvelY()
	{
		return velY;
	}
	protected Rectangle getBounds()
	{
		return new Rectangle((int)x, (int)y, width, height);
	}
	protected void Collide()
	{
		x -= velX;
		y -= velY;
		velX = 0;
		velY = 0;
	}
}
