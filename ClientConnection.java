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
//    gui = new Gui(playerNum);
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
          //ask gui for move
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public void initialize(StringTokenizer s)
  {
    String top = s.nextToken();
    List<String> hand = new ArrayList<String>();
    while(s.nextToken() != null)
      hand.add(s.nextToken());
    gui.initialize(top, hand);
  }
    
    
    
    
}