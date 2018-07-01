import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Gui extends Thread
{
  private GridDisplay display;
  private String image;
  public boolean askingForMove;
  private Graphics g;
  private List<String> hand;

  public static final Color TABLE_COLOR = new Color(50,150,50);
  private static Location DISCARD_LOC = new Location(4,4);
  private static Location DRAWPILE_LOC = new Location(3,4);

  public Gui()
  {

    askingForMove = false;
    display = new GridDisplay(9,9);
    display.setTitle("UNO!");

    image="";
    display.setImage(DRAWPILE_LOC,"card.png");
    for(int r = 0; r < 9; r++)
    {
      for(int c = 0; c < 9; c++)
      {
        display.setColor(new Location(r,c), TABLE_COLOR);
      }
    }
    g = display.getGraphics();
    g.setColor(GridDisplay.toJavaColor(new Color(170,170,170)));
  }

  
  public void run()
  {
    while (true)
    {
      //wait 100 milliseconds for mouse or keyboard
      GridDisplay.pause(500);

      
      //check if any locations clicked or keys pressed
      Location clickLoc = display.checkLastLocationClicked();
      int key = display.checkLastKeyPressed();
      
      if (key != -1)
      {
        //a key was pressed
       
      }
      
      step();

    }
  }

  private Location findOpen()
  {
    for(int y=7;y>5;y--)
    {  
      for(int x=1;x<7;x++)
      {
        if(display.getImage(new Location(x,y)) == null)
          return new Location(x,y);
      }
      
    }
    System.out.println("this should only happen if you are really unlucky or bad at UNO, good luck!");
    return new Location(5,0);
  }
  
  //if playernum == -2 , then we draw a card so put card c in hand
  //if card c == null, then someone else drew a card increase their card count
  //every other case is playerNum plays card c => card c on DISCARD_LOC pile, remove if its yours or decrement opponent card count
  public void cardPlayed( Card c, int playerNum )
  {
    if (playerNum == -1) // special cases
    {
      if (c.getRank() == 13) // special wild placeholder
        display.setImage(DISCARD_LOC, c.toString());
      else // playing a card yourself
      {
        display.setImage(DISCARD_LOC, c.toString());
        //FIGURE OUT HOW TO REMOVE CARD FROM DISPLAY done
      }
    }
    else if(playerNum == -2)
    {
      display.setImage(findOpen(), c.toString());
    }
    else if (c != null) // someone playing a card
    {
      display.setImage(DISCARD_LOC, c.toString());
      display.opCardCount[playerNum]--;
    }
    else // opponent drawing a card
    {
      System.out.println("opponent drew card");
      display.opCardCount[playerNum]++;
      display.repaint();
    }
  }

  public Card askForMove()
  {
    display.askingForMove = true;
    while(true)
    {
      display.pause(100);
      Location clickLoc = display.checkLastLocationClicked();
      if(clickLoc != null)
      {
        if (clickLoc.equals(DRAWPILE_LOC))
        {
          System.out.println("drawcard");
          display.askingForMove = false;
          return null;
        }
        else if (display.getImage(clickLoc) != null && !clickLoc.equals(DISCARD_LOC)) // ree
        {
          image = display.getImage(clickLoc);
          if (Card.validMove(toCard(image), toCard(display.getImage(DISCARD_LOC))))  // u big ol ree
          {
            System.out.print("supposed to play card" + image);
            display.askingForMove = false;
            display.setImage(clickLoc, null);
            hand.remove(image);
            return toCard(image);
          }
        }
      }
    }
  }

  public int pickColor()
  {
    System.out.println("pickingColor");

    display.setColor(new Location(3,3),new Color(245,100,98)); // red
    display.setColor(new Location(3,5),new Color(247,227,89)); // yellow
    display.setColor(new Location(5,3),new Color(47,226,155)); // green
    display.setColor(new Location(5,5),new Color(0,195,229)); // blue
    int color = 0;
    
    while(color == 0)
    {
      GridDisplay.pause(100);
      Location clickLoc = display.checkLastLocationClicked();
      if (clickLoc != null)
      {
        if(clickLoc.equals(new Location(3,3)))
          color = 1;
        else if(clickLoc.equals(new Location(3,5)))
          color=2;
        else if(clickLoc.equals(new Location(5,3)))
          color=3;
        else if(clickLoc.equals(new Location(5,5)))
          color=4;
      }
    }
    display.setColor(new Location(3,3), TABLE_COLOR);
    display.setColor(new Location(3,5), TABLE_COLOR);
    display.setColor(new Location(5,5), TABLE_COLOR);
    display.setColor(new Location(5,3), TABLE_COLOR);
    System.out.println("gui picked color:" + color);
    return color;
  }
  
  
  //this method is called at regular intervals
  public void step()
  {

  }
  
  
  public void initialize(String top, List<String> hand, int numPlayers)
  {
    System.out.println("init");
    this.hand = hand;
    display.setImage(DISCARD_LOC, top);
    for(int i = 0; i < hand.size(); i++)
    {
      display.setImage(new Location(8, i+1), hand.get(i)); 
    }

    // drawing card backs for opponents
    if(numPlayers > 1)
      display.setImage(new Location(4, 0), "card.png");
    if(numPlayers > 2)
      display.setImage(new Location(0, 4), "card.png");
    if(numPlayers > 3)
      display.setImage(new Location(4, 8), "card.png");

    display.opCardCount = new int[numPlayers-1];
    for( int i  = 0; i < display.opCardCount.length; i++)
      display.opCardCount[i] = 7;
  }
  
  
  //example:  blue_0_large.png
  public Card toCard(String s)
  {
    int rank=0;
    int color=0;
    
//    if(s.length() == 8)//card back
//    {
//      return null;
//    }
    String[] tokens = s.split("_");
    String c = tokens[0];
    String r = tokens[1];
    if(c.equals("blue"))
      color=4;
    else if(c.equals("red"))
      color=1;
    else if(c.equals("green"))
      color=3;
    else if(c.equals("yellow"))
      color=2;
    if(r.equals("1"))
      rank=1;
    else if(r.equals("2"))
      rank=2;
    else if(r.equals("3"))
      rank=3;
    else if(r.equals("4"))
      rank=4;
    else if(r.equals("5"))
      rank=5;
    else if(r.equals("6"))
      rank=6;
    else if(r.equals("7"))
      rank=7;
    else if(r.equals("8"))
      rank=8;
    else if(r.equals("9"))
      rank=9;
    else if(r.equals("skip"))
      rank=10;
    else if(r.equals("reverse"))
      rank=11;
    else if(r.equals("picker"))
      rank=12;
    else if(r.equals("pickfour"))
      rank=12;
    
    System.out.println(rank + " " + color);

    return new Card(rank,color);
  }
  
  //this code starts a game when you click the run button
  public static void main(String[] args)
  {
    Gui gui = new Gui();
    Game game = new Game(4);
    gui.initialize(game.discard.peek().toString(), convert(game.hands.get(0)), 4);

    while(true)
    {
      gui.cardPlayed(new Card(13,gui.pickColor()),-1);
    }
  }
  
  
  //test method
  public static List<String> convert(List<Card> l)
  {
    List<String> ret = new ArrayList<String>();
    for(int i = 0; i< l.size(); i++)
    {
      ret.add(l.get(i).toString());
    }
    return ret;
  }
}
