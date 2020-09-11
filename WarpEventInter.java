package main;

public class WarpEventInter extends WarpEvent{
	private int MapId;
	WarpEventInter(int _x, int _y, int _DestinationX, int _DestinationY, WarpEventAnimationId _AnimationId, int _MapId) {
		super(_x, _y, _DestinationX, _DestinationY,  _AnimationId);
		setId(WarpEventId.Inter);
		MapId = _MapId;
	}
	protected int getMapId()
	{
		return MapId;
	}
}
