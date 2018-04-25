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
    gui = new Gui();
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
        }
      }
      catch (Exception e)
      {
        
      }
    }
  }
    
    
    
    
}