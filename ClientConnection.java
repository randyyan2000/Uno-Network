import java.io.*;  //for BufferedReader, InputStreamReader, PrintWriter
import java.net.*;  //for ServerSocket, Socket
import java.util.*;
public class ClientConnection extends Thread
{
  private ClientGame clientGame;
  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;
  private Piece pieceFromNetwork;
  
  public ClientConnection(String serverAddress, ClientGame clientGame)
  {
    this.clientGame = clientGame;
    socket = new Socket(serverAddress, 8000);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream(), true);
    start();
  }
  
  public void run()
  {
    while (true)
    {
      String line = in.readLine();  //blocks until something received
      String[] tokens = line.split(" ");
      if (tokens[0].equals("GETMOVE"))
        clientGame.processGetMove();
      else if (tokens[0].equals("RETPIECE"))
      {
        int rank = Integer.parseInt(tokens[1]);
        pieceFromNetwork = new Piece(rank);
      }
    }
  }
  
  //clientGame wants to return a move to the server
  public void returnMove(Move move)
  {
    out.println("RETMOVE " + move.getFrom().getRow() + " " +
                move.getFrom().getCol() + " " +
                move.getTo().getRow() + " " +
                move.getTo().getCol());
  }
  
  //clientGame wants to get the piece at given location.
  //blocks until has answer.
  public Piece getPiece(Location loc)
  {
    pieceFromNetwork = null;
    out.println("GETPIECE " + loc.getRow() + " " + loc.getCol());
    while (pieceFromNetwork == null)
    {
      try{Thread.sleep(100);}catch(Exception e){}
    }
    return pieceFromNetwork;
  }
}