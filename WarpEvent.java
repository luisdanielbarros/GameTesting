package main;

public abstract class WarpEvent {
	private int X, Y, DestinationX, DestinationY;
	private WarpEventId Id;
	private WarpEventAnimationId AnimationId;
	WarpEvent(int _X, int _Y, int _DestinationX, int _DestinationY, WarpEventAnimationId _AnimationId)
	{
		X = _X;
		Y = _Y;
		DestinationX = _DestinationX;
		DestinationY = _DestinationY;
		AnimationId = _AnimationId;
	}
	protected void setId(WarpEventId _Id)
	{
		Id = _Id;
	}
	protected int getX()
	{
		return X;
	}
	protected int getY()
	{
		return Y;
	}
	protected int getDestinationX()
	{
		return DestinationX;
	}
	protected int getDestinationY()
	{
		return DestinationY;
	}
	protected WarpEventId getId()
	{
		return Id;
	}
	protected WarpEventAnimationId getAnimationId()
	{
		return AnimationId;
	}
}
