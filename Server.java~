import java.util.*;
import java.io.*;
import java.net.*;

public class Server
{
  private Game game;
  private List<ServerConnection> connections;
  
  public Server()
  {
    connections = new ArrayList<ServerConnection>();
    
    try 
    { 
      ServerSocket server = new ServerSocket(9000);  
      for(int i = 0; i < 4; i++)
      {
        connections.add(new ServerConnection(server.accept(), this, i));
      }
    } //start server on port 9000
    
    catch (Exception e) { e.printStackTrace(); }
    
    game = new Game(4);
  }
  
  public void drawCard(int playerNum)
  {
    game.drawCard(playerNum);
  }
  
  public void playCard(Card c, int playerNum)
  {
    game.playCard(c, playerNum);
  }
  
  
  public static void main(String[] args)
  {
    Server s = new Server();
    s.initialize();
    s.play();
  }
  
  public void initialize()
  {
    for( ServerConnection connection : connections)
    {
//      connection.initialize();
    }
  }
  
  public void play()
  {
    while(true)
    {
      for( ServerConnection connection : connections)
      {
        connection.askForMove();
//        connection.update(Card c, int playerNum);
      }
    }
  }
}