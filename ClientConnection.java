import java.io.*;  //for BufferedReader, InputStreamReader, PrintWriter
import java.net.*;  //for ServerSocket, Socket
import java.util.*;

public class ClientConnection extends Thread
{
  private static final String ip = "192.168.0.105"; // dell xps
//  private static final String ip = "10.13.32.72"; // cs laptop #2
//  private static final String ip = "10.3.177.205"; // dell inspiron

  private Socket socket;
  private int playerNum;
  private BufferedReader in;
  private PrintWriter out;
  private Gui gui;
  
  public ClientConnection(int playerNum, String ip, int port)
  {
    try
    {
      socket = new Socket(ip, port);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);
    }
    catch(Exception e)
    {e.printStackTrace();}
    
    this.playerNum = playerNum;
    gui = new Gui();
    gui.start();
    start();
  }
  
  public static void main(String[] args)
  {
    ClientConnection c = new ClientConnection(0, ip,9000);
  }
  
  public void run()
  {
    while(true)
    {
      try
      {
        String line = in.readLine();
        System.out.println("received:  " + line);
        StringTokenizer s = new StringTokenizer(line);
        String cmd = s.nextToken();
        System.out.println(cmd);
        if(cmd.equals("INIT"))
        {
          initialize(s);
        }
        else if(cmd.equals("GETMOVE"))
        {
          Card c = gui.askForMove();
          if(c == null)
            drawCard();
          else
            playCard(c);
        }
        else if(cmd.equals("UPDATE"))
        {
          String r = s.nextToken();
          String c = s.nextToken();
          int playerNum = Integer.parseInt(s.nextToken());
          Card card;
          if(r.equals("null"))
            card = null;
          else
            card = toCard(r,c);
          gui.cardPlayed(card, playerNum);
        }
        else if(cmd.equals("ASKCOLOR"))
        {
          send("PICKCOLOR " + gui.pickColor());
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
    send("PLAYCARD" + " " + c.toCode());
   
  }
  
  public void send(String message)
  {
    System.out.println("sending:    " + message);
    out.println(message);
  }
  
  public void initialize(StringTokenizer s)
  {
    String top = s.nextToken();
    System.out.println("top: " + top);
    List<String> hand = new ArrayList<String>();
    for(int i = 0; i < 7; i++)
    {
      hand.add(s.nextToken());
    }
    gui.initialize(top, hand, Integer.parseInt(s.nextToken()));
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