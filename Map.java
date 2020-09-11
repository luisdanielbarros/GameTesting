package main;

import java.awt.Image;

public class Map {
	private Image BottomLayer, TopLayer;
	private Collisions[][] Collisions;
	Map(Image _BottomLayer, Image _TopLayer, Collisions[][] _Collisions)
	{
		this.BottomLayer = _BottomLayer;
		this.TopLayer = _TopLayer;
		this.Collisions = _Collisions;
	}
	protected Image getBottomLayer()
	{
		return BottomLayer;
	}
	protected Image getTopLayer()
	{
		return TopLayer;
	}
	protected Collisions[][] getCollisions()
	{
		return Collisions;
	}
}
