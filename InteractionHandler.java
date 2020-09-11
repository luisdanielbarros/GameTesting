package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import main.Game.STATE;

public class InteractionHandler {
	private Game game;
	private Interaction ActiveInteraction;
	private int ActiveInteractionIndex, ActiveInteractionSubIndex;
	private boolean SetUp, SentencesAdjusted;
	private float TextMarginTop, textMarginLeft;
	private Rectangle ChatBox, YNBox;
	private Font AdjustedFont, AdjustedYNFont;
	List<String> AdjustedSentences;
	InteractionHandler(Game _game)
	{
		game = _game;
		SetUp = false;
		SentencesAdjusted = false;
		TextMarginTop = (Game.TileHeight / 4);
		textMarginLeft = (Game.TileWidth / 4);
		ChatBox = new Rectangle(0, Game.ScreenHeight - (int)(Game.TileHeight * 6), Game.ScreenWidth, (int)(Game.TileHeight * 6));
		YNBox = new Rectangle((int)(Game.ScreenWidth - (Game.TileHeight * 3)), (int)(Game.ScreenHeight - (Game.TileHeight * 8)), (int)(Game.TileHeight * 3), (int)(Game.TileHeight * 1));
		AdjustedFont = new Font("Microsoft Sans Serif", Font.PLAIN, 1);
		AdjustedYNFont = new Font("Impact", Font.PLAIN, 1);
		AdjustedSentences = new ArrayList<String>();
	}
	protected void render(Graphics g)
	{
		if (ActiveInteraction == null) return;
		InteractionSentence CurrentSentence = ActiveInteraction.getSentences()[ActiveInteractionIndex];
		//The objects sets its base variables each time that a new interaction is called
		//these base variables are essential to renderizing the interaction properly
		if (!SetUp)
		{
			SetUp(g);
			SetUp = true;
		}
		//Draws the base for every interaction
		g.setColor(new Color(1f, 1f, 1f, .5f));
		g.fillRect(ChatBox.x, ChatBox.y, ChatBox.width, ChatBox.height);
		g.setColor(Color.black);
		//Adjusts the sentences by splitting them into shorter ones that fit in the screen
		if (!SentencesAdjusted)
		{
			genAdjustedSentences(g);
			SentencesAdjusted = true;
		}
		//Sets the font before drawing any string
		g.setFont(AdjustedFont);
		//Draws each adjusted sentence
		for (int i = 0; i < AdjustedSentences.size(); i++)
		{
			Rectangle2D CurrentStringBounds = g.getFontMetrics().getStringBounds(AdjustedSentences.get(i), g);
			g.drawString(AdjustedSentences.get(i), (int)(textMarginLeft), (int)(TextMarginTop + Game.ScreenHeight - (Game.TileHeight * 5) + (CurrentStringBounds.getHeight() * i)));
		}
		//If it's a question that hasn't been answered, it will render the "YES" and "NO" boxes
		if (CurrentSentence.getId() == InteractionSentenceId.YNQuestion && ((InteractionSentenceYNQuestion)CurrentSentence).getAnswered() == false)
		{
			g.setColor(new Color(.5f, .5f, .5f, .9f));
			g.fillRect(YNBox.x, YNBox.y, YNBox.width, YNBox.height * 2);			
			g.setFont(AdjustedYNFont);
			g.setColor(new Color(1f, 1f, 1f, .5f));
			if (((InteractionSentenceYNQuestion)CurrentSentence).getSelection() == true) g.fillRect(YNBox.x, YNBox.y, YNBox.width, YNBox.height);
			else g.fillRect(YNBox.x, YNBox.y + YNBox.height, YNBox.width, YNBox.height);
			g.setColor(Color.white);
			Rectangle2D StringBounds = g.getFontMetrics().getStringBounds("Yes", g);
			g.drawString("Yes", (int)(YNBox.getX() + (YNBox.getWidth() - StringBounds.getWidth()) / 2), (int)(YNBox.getY() + YNBox.height - ((YNBox.height - StringBounds.getHeight()) * 2)));
			StringBounds = g.getFontMetrics().getStringBounds("No", g);
			g.drawString("No", (int)(YNBox.getX() + (YNBox.getWidth() - StringBounds.getWidth()) / 2), (int)(YNBox.getY() + (YNBox.height * 2) - ((YNBox.height - StringBounds.getHeight()) * 2)));
		}

	}
	protected void setActiveInteraction(Interaction _ActiveInteraction)
	{
		SetUp = false;
		SentencesAdjusted = false;
		ActiveInteraction = _ActiveInteraction;
		ActiveInteractionIndex = 0;
		ActiveInteractionSubIndex = 0;
		game.GameState = STATE.GameInteracting;
	}
	protected void DeactivateInteraction()
	{
		ActiveInteraction.reset();
		ActiveInteraction = null;
		ActiveInteractionIndex = 0;
		ActiveInteractionSubIndex = 0;
		game.GameState = STATE.GameDefault;
	}
	private void SetUp(Graphics g)
	{
		//Increases the font size until 3 lines of text, stacked vertically upon each other, stop fitting inside the chatbox
		int FontSize = 1;
		do {
			FontSize++;
			AdjustedFont = new Font("Microsoft Sans Serif", Font.PLAIN, FontSize);
			g.setFont(AdjustedFont);
		}
		while (g.getFontMetrics().getStringBounds("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPWRSTUVWXYZ1234567890", g).getHeight() * 1.5 * 3 + TextMarginTop < ChatBox.getHeight());
		AdjustedFont = new Font("Microsoft Sans Serif", Font.PLAIN, FontSize--);
		FontSize = 1;
		do {
			FontSize++;
			AdjustedYNFont = new Font("Impact", Font.PLAIN, FontSize);
			g.setFont(AdjustedYNFont);
		}
		while (g.getFontMetrics().getStringBounds("YesNo", g).getHeight() * 1.1 < YNBox.getHeight());
		AdjustedYNFont = new Font("Impact", Font.PLAIN, FontSize--);
	}
	private void genAdjustedSentences(Graphics g)
	{
		//Splits any sentences that go over the screen's width in shorter sentences that fit
		if (ActiveInteraction == null) return;
		AdjustedSentences.clear();
		InteractionSentence CurrentSentence = ActiveInteraction.getSentences()[ActiveInteractionIndex];
		//Gets the string that is supposed to be rendered
		String CurrentString = "Unexpected string value at InteractionHandler";
		if (CurrentSentence.Id == InteractionSentenceId.Default || CurrentSentence.getId() == InteractionSentenceId.YNQuestion && ((InteractionSentenceYNQuestion)CurrentSentence).getAnswered() == false) CurrentString = CurrentSentence.getSentence();
		else if (CurrentSentence.getId() == InteractionSentenceId.YNQuestion && ((InteractionSentenceYNQuestion)CurrentSentence).getAnswered() == true)
		{
			if (((InteractionSentenceYNQuestion)CurrentSentence).getSelection()) CurrentString = ((InteractionSentenceYNQuestion)CurrentSentence).getYInteraction()[ActiveInteractionSubIndex].getSentence();
			else CurrentString = ((InteractionSentenceYNQuestion)CurrentSentence).getNInteraction()[ActiveInteractionSubIndex].getSentence();
		}
		//Checks if the string fits the screen size, and splits it accordingly
		Rectangle2D StringBounds = g.getFontMetrics().getStringBounds(CurrentString, g);
		if (Game.TileWidth + StringBounds.getWidth() > Game.ScreenWidth)
		{
			String[] Words;
			//If it's a default sentence or a question that hasn't been answered, the string will be taken from one place
			if (CurrentSentence.Id == InteractionSentenceId.Default || CurrentSentence.getId() == InteractionSentenceId.YNQuestion && ((InteractionSentenceYNQuestion)CurrentSentence).getAnswered() == false)Words = CurrentSentence.getSentence().split(" ");
			//Else if it's a question that has been answered, the string will be taken from another place
			else if (CurrentSentence.getId() == InteractionSentenceId.YNQuestion && ((InteractionSentenceYNQuestion)CurrentSentence).getAnswered() == true) 
			{
				//If the answer was a "YES"
				if (((InteractionSentenceYNQuestion)CurrentSentence).getSelection()) Words = ((InteractionSentenceYNQuestion)CurrentSentence).getYInteraction()[ActiveInteractionSubIndex].getSentence().split(" ");
				//Else if the answer was a "NO"
				else Words = ((InteractionSentenceYNQuestion)CurrentSentence).getNInteraction()[ActiveInteractionSubIndex].getSentence().split(" ");
			}
			else Words = new String[]  { "Unexpected string value at InteractionHandler" };
			for (int i = 0; i < Words.length; i++)
			{
				if (i == 0) 
				{
					AdjustedSentences.add(Words[i]);
					continue;
				}
				else
				{
					Rectangle2D CurrentWordBounds = g.getFontMetrics().getStringBounds(" "  + Words[i], g);
					Rectangle2D CurrentStringBounds = g.getFontMetrics().getStringBounds(AdjustedSentences.get(AdjustedSentences.size() -1), g);
					if (textMarginLeft + CurrentWordBounds.getWidth() + CurrentStringBounds.getWidth() + Game.TileWidth < Game.ScreenWidth) AdjustedSentences.set(AdjustedSentences.size() - 1, AdjustedSentences.get(AdjustedSentences.size() - 1) + " " + Words[i]);
					else AdjustedSentences.add(Words[i]);
				}
			}
		}
		else AdjustedSentences.add(CurrentString);
	}
	protected void UseSpace()
	{
		if (ActiveInteraction == null) return;
		InteractionSentence CurrentSentence = ActiveInteraction.getSentences()[ActiveInteractionIndex];
		//If it's a default sentence
		//If it reached its end
		if (CurrentSentence.getId() == InteractionSentenceId.Default && ActiveInteractionIndex + 1 == ActiveInteraction.getSentencesLength()) DeactivateInteraction();
		//If it didn't reach its end
		if (CurrentSentence.getId() == InteractionSentenceId.Default) ActiveInteractionIndex++;
		//Else if it's a question
		else if (CurrentSentence.getId() == InteractionSentenceId.YNQuestion)
		{
			//If it hasn't been answered
			if (((InteractionSentenceYNQuestion)CurrentSentence).getAnswered() == false) ((InteractionSentenceYNQuestion)CurrentSentence).setAnswered(true);
			//Else if it has been answered and it reached its end
			else if (((InteractionSentenceYNQuestion)CurrentSentence).getSelection() == true && ((InteractionSentenceYNQuestion)CurrentSentence).getYInteractionlength() == ActiveInteractionSubIndex + 1 || ((InteractionSentenceYNQuestion)CurrentSentence).getSelection() == false && ((InteractionSentenceYNQuestion)CurrentSentence).getNInteractionlength() == ActiveInteractionSubIndex + 1)
			{
				ActiveInteractionIndex++;
				ActiveInteractionSubIndex = 0;
				//If it reached the absolute end
				if (ActiveInteractionIndex == ActiveInteraction.getSentencesLength()) DeactivateInteraction();
			}
			//Else if it has been answered and didn't reach its end
			else ActiveInteractionSubIndex++;
		}
		SentencesAdjusted = false;
	}
	protected void UseUpArrow()
	{
		InteractionSentence CurrentSentence = ActiveInteraction.getSentences()[ActiveInteractionIndex];
		if (CurrentSentence.getId() == InteractionSentenceId.YNQuestion)
		{
			if (((InteractionSentenceYNQuestion)CurrentSentence).getSelection() == false) ((InteractionSentenceYNQuestion)CurrentSentence).setSelection(true);
		}
	}
	protected void UseDownArrow()
	{
		InteractionSentence CurrentSentence = ActiveInteraction.getSentences()[ActiveInteractionIndex];
		if (CurrentSentence.getId() == InteractionSentenceId.YNQuestion)
		{
			if (((InteractionSentenceYNQuestion)CurrentSentence).getSelection() == true) ((InteractionSentenceYNQuestion)CurrentSentence).setSelection(false);
		}
	}
}
