package main;

public class WarpEventIntra extends WarpEvent{
	WarpEventIntra(int _x, int _y, int _DestinationX, int _DestinationY, WarpEventAnimationId _AnimationId) {
		super(_x, _y, _DestinationX, _DestinationY, _AnimationId);
		setId(WarpEventId.Inter);
	}
}
