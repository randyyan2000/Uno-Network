import java.util.*;

public class Game
{
  private LinkedList<Card> deck;
  public LinkedList<Card> discard;
  public List<List<Card>> hands;
  
  public Game(int numPlayers)
  {
    deck = new LinkedList<Card>();
    discard = new LinkedList<Card>();
    initializeDeck();
    dealCards(numPlayers);
    discard.push(deck.pop());
  }
  
  public void initializeDeck()
  {
    for(int rank = 0; rank < 13; rank++) // ranks
    {
      for(int color = 1; color < 5; color++) // loop through colors 
      {
        deck.add(new Card(rank, color)); // add first Card
        if(rank != 0)
          deck.add(new Card(rank, color)); // if its not zero add the second Card
      }
    }
    for(int i = 0; i < 4; i++)
    {
      deck.add(new Card(Card.DRAWFOUR_RANK, Card.WILD_COLOR));
      deck.add(new Card(Card.DRAWFOUR_RANK, Card.WILD_COLOR));
      deck.add(new Card(Card.WILD_RANK, Card.WILD_COLOR));
      deck.add(new Card(Card.WILD_RANK, Card.WILD_COLOR));
    }
    Collections.shuffle(deck);
  }
  
  public void dealCards(int numPlayers)
  {
    hands = new ArrayList<List<Card>>();
    for(int i = 0; i < numPlayers; i++)
    {
      hands.add(new ArrayList<Card>());
    }
    for(int i = 0; i < 7; i++)
    {
      for(int h = 0; h < hands.size(); h++)
      {
        hands.get(h).add(deck.pop());
      }
    }
  }
  
  public void reshuffleDeck()
  {
    while(discard.size()>1)
    {
      if(discard.peek().getRank() != 13)
        deck.push(discard.pop());
    }
    Collections.shuffle(deck);
  }
  
  public Card drawCard(int playerNum)
  {
    if(deck.isEmpty())
      reshuffleDeck();
    Card c = deck.pop();
    hands.get(playerNum).add(c);
    return c;
  }
  
  public void playCard(Card card, int playerNum)
  {
    System.out.println(hands.get(playerNum));
    for(int i = 0; i < hands.get(playerNum).size(); i++)
    {
      if(hands.get(playerNum).get(i).equals(card))
      {
        hands.get(playerNum).remove(i);
        break;
      }
    }
    System.out.println(hands.get(playerNum));
    discard.push(card);
  }
  
  public void pickColor(int color)
  {
    discard.push(new Card(13, color));
  }
  
}
