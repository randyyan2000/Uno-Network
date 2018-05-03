import java.io.*;  //for BufferedReader, InputStreamReader, PrintWriter
import java.net.*;  //for ServerSocket, Socket
import java.util.*;

public class ClientConnection extends Thread
{
  private Socket socket;
  private int playerNum;
  private BufferedReader in;
  private PrintWriter out;
  
  public Gui gui;
  
  public ClientConnection(int playerNum)
  {
    this.playerNum = playerNum;
    //gui = new Gui(playerNum);
  }
  
  public void run()
  {
    while(true)
    {
      try
      {
        StringTokenizer s = new StringTokenizer(in.readLine());
        String cmd = s.nextToken();
        if(cmd.equals("INIT"))
        {
          initialize(s);
        }
        else if(cmd.equals("GETMOVE"))
        {
          gui.askingForMove = true;
        }
        if(cmd.equals("UPDATE"))
        {
          String r = s.nextToken();
          String c = s.nextToken();
          int playerNum = Integer.parseInt(s.nextToken());
          Card card = toCard(r,c);
          gui.cardPlayed(card, playerNum);
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public void drawCard()
  {
    out.println("DRAWCARD");
  }
  
  public void playCard(Card c)
  {
    out.println("PLAYCARD" + " " + c.toCode());
  }
  
  public void initialize(StringTokenizer s)
  {
    String top = s.nextToken();
    List<String> hand = new ArrayList<String>();
    while(s.nextToken() != null)
      hand.add(s.nextToken());
    gui.initialize(top, hand);
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