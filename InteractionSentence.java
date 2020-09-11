package main;

public abstract class InteractionSentence {
	private String Sentence;
	protected InteractionSentenceId Id;
	InteractionSentence(String _Sentence)
	{
		Sentence = _Sentence;
	}
	protected String getSentence()
	{
		return Sentence;
	}
	protected InteractionSentenceId getId()
	{
		return Id;
	}
}
