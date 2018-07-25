import java.util.*;
import java.net.*;



public class Server
{
  public static final int NPLAYERS = 2;
  
  private Game game;
  private List<ServerConnection> connections;
  private int Color;
  private Card cardPlayed;
  
  public Server()
  {
    connections = new ArrayList<ServerConnection>();
    
    try 
    { 
      ServerSocket server = new ServerSocket(9000);  
      for(int i = 0; i < NPLAYERS; i++)
      {
        connections.add(new ServerConnection(server.accept(), this, i));
      }
    } //start server on port 9000
    
    catch (Exception e) { e.printStackTrace(); }
    
    game = new Game(NPLAYERS);
  }
  
  public void drawCard(int playerNum)
  {
    for(int i = 0; i < NPLAYERS; i++)
    {
      if(i == playerNum)
        connections.get(i).update(game.drawCard(playerNum), -2);
      else
      {
        int adjustedPlayerNum = playerNum-i-1;
        if(adjustedPlayerNum < 0)
          adjustedPlayerNum += NPLAYERS;
        connections.get(i).update(null, adjustedPlayerNum);
      }
    }
    cardPlayed = null;
  }
  
  public void playCard(Card c, int playerNum)
  {
    game.playCard(c, playerNum);
    if(c.getColor() == Card.WILD_COLOR)
      connections.get(playerNum).askForColor();
    for(int i = 0; i < NPLAYERS; i++)
    {
      int adjustedPlayerNum = playerNum-i-1;
      if(adjustedPlayerNum < -1)
        adjustedPlayerNum += NPLAYERS;
      connections.get(i).update(c, adjustedPlayerNum);
    }
    cardPlayed = c;
  }
  
  public void pickColor(int c)
  {
    game.pickColor(c);
    for(ServerConnection connection : connections)
    {
      connection.update(new Card(13,c),-1);
    }
  }

  public static void main(String[] args)
  {
    try
    {
      System.out.println(InetAddress.getLocalHost());
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
    }
    Server s = new Server();
    try
    {
    Thread.sleep(100);
    }
    catch(Exception e)
    {e.printStackTrace();}
    s.initialize();
    s.play();
  }
  
  public void initialize()
  {
    for(int i = 0; i < NPLAYERS; i++)
    {
      ServerConnection connection = connections.get(i);
      connection.initialize(game.discard.peek(), game.hands.get(i), NPLAYERS);
//      System.out.println("init");
    }
  }
  
  public void play()
  {
    int currentPlayer = 0; //
    while(true)
    {
      ServerConnection connection = connections.get(currentPlayer);
//        System.out.println(game.hands.get(0));
//        System.out.println(game.discard.peek());
      connection.askForMove();
      if (cardPlayed != null)
      {
        if (cardPlayed.getColor() == Card.WILD_COLOR)
        {
          connection.askForColor();
          if (cardPlayed.getRank() == Card.DRAWFOUR_RANK)
          {
            for (int d = 0; d < 4; d++)
            {
              drawCard(nextPlayer(currentPlayer, true));
            }
          }
        }
        else if (cardPlayed.getRank() == Card.DRAWTWO_RANK)
        {
          drawCard(nextPlayer(currentPlayer, true));
          drawCard(nextPlayer(currentPlayer, true));
        }

        if (cardPlayed.getRank() == Card.DRAWTWO_RANK || cardPlayed.getRank() == Card.SKIP_RANK)
          currentPlayer = nextPlayer(nextPlayer(currentPlayer, true), true);
        else if (cardPlayed.getRank() == Card.REVERSE_RANK)
          currentPlayer = nextPlayer(currentPlayer, false);
        else
          currentPlayer = nextPlayer(currentPlayer, true);
      }
    }
  }

  public int nextPlayer(int currentPlayer, boolean forward)
  {
    if(forward)
    {
      if(currentPlayer == NPLAYERS-1)
        return 0;
      else
        return currentPlayer+1;
    }
    else // backward
    {
      if(currentPlayer == 0)
        return NPLAYERS-1;
      else
        return currentPlayer-1;
    }
  }
}