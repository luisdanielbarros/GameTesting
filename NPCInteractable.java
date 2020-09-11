package main;

public class NPCInteractable extends NPC{
	private Interaction CustomInteraction;
	NPCInteractable(int x, int y, Handler handler, NPCPath path, Interaction _CustomInteraction) {
		super(x, y, handler, path);
		setWidth((int)Game.TileWidth);
		setHeight((int)Game.TileHeight);
		setId(GameObjectId.NPCInteractable);
		CustomInteraction = _CustomInteraction;
	}
	protected Interaction getInteraction()
	{
		return CustomInteraction;
	}
}
