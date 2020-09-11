package main;

import javax.swing.ImageIcon;

public class Tileset {
	private ImageIcon[] Tiles;
	Tileset(ImageIcon[] _Tiles)
	{
		this.Tiles = _Tiles;
	}
	protected ImageIcon getImageAt(int pos)
	{
		return Tiles[pos];
	}
}
