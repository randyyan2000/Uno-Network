import java.io.*;  //for BufferedReader, InputStreamReader, PrintWriter
import java.net.*;  //for ServerSocket, Socket
import java.util.*;

public class ServerConnection extends Thread
{
  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;
  private Card recievedMove;
  
  
  public ServerConnection()
  {
    try
    {
      ServerSocket server = new ServerSocket(9000);  //start server on port 9000
      socket = server.accept();
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(socket.getOutputStream(), true);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void run()
  {
    while(true)
    {
      try
      {
        StringTokenizer s = new StringTokenizer(in.readLine());
        String cmd = s.nextToken();
      } 
      catch(Exception e){e.printStackTrace();}
    }
  }
  
  
  public void askForMove()
  {
    out.println("GETMOVE");
  }
}