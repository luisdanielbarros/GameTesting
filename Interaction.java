package main;

public class Interaction {
	private InteractionSentence[] Sentences;
	private InteractionId Id;
	Interaction(InteractionSentence[] _Sentences, InteractionId _Id)
	{
		Sentences = _Sentences;
		Id = _Id;
	}
	protected InteractionSentence[] getSentences()
	{
		return Sentences;
	}
	protected int getSentencesLength()
	{
		return Sentences.length;
	}
	protected InteractionId getId()
	{
		return Id;
	}
	protected void reset()
	{
		for (int i = 0; i < Sentences.length; i++)
		{
			if (Sentences[i].getId() == InteractionSentenceId.YNQuestion) ((InteractionSentenceYNQuestion)Sentences[i]).setAnswered(false);
		}
	}
}
