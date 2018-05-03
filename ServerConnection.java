import java.io.*;  //for BufferedReader, InputStreamReader, PrintWriter
import java.net.*;  //for ServerSocket, Socket
import java.util.*;

public class ServerConnection extends Thread
{
  private Socket socket;
  private Server server;
  private int playerNum;
  private BufferedReader in;
  private PrintWriter out;
  private Card recievedMove;
  
  
  public ServerConnection(Socket socket, Server server, int pNum)
  {
    this.server = server;
    this.playerNum = pNum;
    try
    {
      this.socket = socket;
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
//  public void run()
//  {
//    
//  }
  
  
  public void askForMove()
  {
    out.println("GETMOVE");
    try
    {
      StringTokenizer s = new StringTokenizer(in.readLine());
      String cmd = s.nextToken();
      
      if(cmd.equals("DRAWCARD"))
      {
        server.drawCard(playerNum);
      }
      
      else if(cmd.equals("PLAYCARD"))
      {
        String rank = s.nextToken();
        String color = s.nextToken();
        server.playCard(toCard(rank,color), playerNum);
      }
      
      else if(cmd.equals("PICKCOLOR"))
      {
        pickColor(Integer.parseInt(s.nextToken()));
      }
    } 
    catch(Exception e){e.printStackTrace();}
  }
  
  public void initialize(Card top, List<Card> hand)
  {
    String s = "INIT ";
    s += (top.toString());
    for( Card c : hand )
    {
      s += " " + c.toString();
    }
    out.println(s);
  }
  
  public void askForColor()
  {
    out.println("ASKCOLOR");
  }
  
  public void update(Card c, int playerNum)
  {
    out.println("UPDATE" + " " + c.toCode() + " " + playerNum);
  }
  
  public void pickColor(int c)
  {
    server.pickColor(c);
  }
  
  
  
  public Card toCard(String rank, String color)
  {
    int c;
    int r;
    if(color.equals("W"))
      c = Card.WILD_COLOR;
    else if(color.equals("R"))
      c = Card.RED;
    else if(color. equals("B"))
      c = Card.BLUE;
    else if(color.equals("G"))
      c = Card.GREEN;
    else //(color.equals("Y"))
      c = Card.YELLOW;
    
    r = Integer.parseInt(rank);
    return new Card(r,c);
  }
}