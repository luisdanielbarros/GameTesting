package main;

import java.awt.Graphics;
import java.awt.Image;

public class Camera {
	//what a wonderful world we live in, but not this one, this one was very painful to code.
	private static Image BottomLayer, TopLayer;
	private static int renderfunctionsX, renderfunctionsY;
	Camera()
	{
	}
	protected void renderBottomLayer(Graphics g)
	{
		g.drawImage(BottomLayer, renderfunctionsX, renderfunctionsY, null);
	}
	protected void renderTopLayer(Graphics g)
	{
		if (TopLayer != null) g.drawImage(TopLayer, renderfunctionsX, renderfunctionsY, null);
	}
	protected void setBottomLayer(Image _BottomLayer)
	{
		BottomLayer = _BottomLayer;
	}
	protected void setTopLayer(Image _TopLayer)
	{
		TopLayer = _TopLayer;
	}
	protected void setRenderfunctionsXY(int _renderfunctionsX, int _renderfunctionsY)
	{
		renderfunctionsX = _renderfunctionsX;
		renderfunctionsY = _renderfunctionsY;
	}
	protected int getRenderfunctionsX()
	{
		return renderfunctionsX;
	}
	protected int getRenderfunctionsY()
	{
		return renderfunctionsY;
	}
}
