import java.util.*;
import java.io.*;
import java.net.*;



public class Server
{
  public static final int NPLAYERS = 1; 
  
  private Game game;
  private List<ServerConnection> connections;
  private int Color;
  
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
    game.drawCard(playerNum);
  }
  
  public void playCard(Card c, int playerNum)
  {
    game.playCard(c, playerNum);
    if(c.getColor() == Card.WILD_COLOR)
      connections.get(playerNum).askForColor();
  }
  
  public void pickColor(int c)
  {
    
  }
  
  public static void main(String[] args)
  {
    Server s = new Server();
    s.initialize();
    s.play();
  }
  
  public void initialize()
  {
    for(int i = 0; i < NPLAYERS; i++)
    {
      ServerConnection connection = connections.get(i);
      connection.initialize(game.discard.peek(), game.hands.get(i));
    }
  }
  
  public void play()
  {
    while(true)
    {
      for( ServerConnection connection : connections)
      {
        System.out.println(game.hands.get(0));
        System.out.println(game.discard.peek());
        connection.askForMove();
//        connection.update(Card c, int playerNum);
      }
    }
  }
}