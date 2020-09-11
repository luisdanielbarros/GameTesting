package main;

public class Collisions {
	private boolean walkable, warps;
	private boolean[] borders = new boolean[8];
	Collisions(boolean walkable, boolean warps)
	{
		this.walkable = walkable;
		this.warps = warps;
	}
	Collisions(boolean walkable, boolean warps, boolean[] borders)
	{
		this.walkable = walkable;
		this.warps = warps;
		this.borders = borders;
	}
	protected void setwalkable(boolean walkable)
	{
		this.walkable = walkable;
	}
	protected void setwarps(boolean warps)
	{
		this.warps = warps;
	}
	protected void setborders(boolean[] borders)
	{
		this.borders = borders;
	}
	protected boolean getwalkable()
	{
		return this.walkable;
	}
	protected boolean getwarps()
	{
		return this.warps;
	}
	protected boolean[] getborders()
	{
		return this.borders;
	}
}
