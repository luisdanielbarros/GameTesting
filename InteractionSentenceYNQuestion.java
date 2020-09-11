package main;

public class InteractionSentenceYNQuestion extends InteractionSentence {
	private InteractionSentenceDefault[] YInteraction, NInteraction;
	private boolean Selection, Answered;
	InteractionSentenceYNQuestion(String _Sentence, InteractionSentenceDefault[] _YInteraction, InteractionSentenceDefault[] _NInteraction) {
		super(_Sentence);
		YInteraction = _YInteraction;
		NInteraction = _NInteraction;
		Id = InteractionSentenceId.YNQuestion;
		Selection = true;
		Answered = false;
	}
	protected void setSelection(boolean _Selection)
	{
		Selection = _Selection;
	}
	protected void setAnswered(boolean _Answered)
	{
		Answered = _Answered;
	}
	protected InteractionSentenceDefault[] getYInteraction()
	{
		return YInteraction;
	}
	protected int getYInteractionlength()
	{
		return YInteraction.length;
	}
	protected int getNInteractionlength()
	{
		return NInteraction.length;
	}
	protected InteractionSentenceDefault[] getNInteraction()
	{
		return NInteraction;
	}
	protected boolean getSelection()
	{
		return Selection;
	}
	protected boolean getAnswered()
	{
		return Answered;
	}
}
