import java.util.*;
import java.awt.Graphics;

public class Gui
{
  private GridDisplay display;
  private String image;
  private int playerNum;

  
  private static Location discard = new Location(4,4);
  
  public Gui()
  {
    playerNum=0;
    display = new GridDisplay(9,9);
    display.setTitle("UNO!");
    image="";
  }
  
  public void play()
  {
    while (true)
    {
      //wait 100 milliseconds for mouse or keyboard
      display.pause(100);
      
      //check if any locations clicked or keys pressed
      Location clickLoc = display.checkLastLocationClicked();
      int key = display.checkLastKeyPressed();
      
      if (clickLoc != null)
      {
        //a location was clicked
        locationClicked(clickLoc);
      }
      
      if (key != -1)
      {
        //a key was pressed
       
      }
      
      step();
      
//      this.pickColor();
    }
  }
  
  
  //if playernum == -1 , then we draw a card so put card c in hand
  //if card c == null, then someone else drew a card increase their card count
  //every other case is playerNum plays card c => card c on discard pile, remove if its yours or decrement opponent card count
  public void cardPlayed( Card c, int playerNum )
  {
    display.setImage(discard, c.toString());
    
  }

  //called when the user clicks on a location.
  //that location is passed to this method.
  private void locationClicked(Location loc)
  {
    if(display.getImage(loc)!=null && !loc.equals(discard))
    {
      System.out.println(image);
      image=display.getImage(loc);
      if(Card.validMove(toCard(image), toCard(display.getImage(discard))));
      {
        System.out.println(image);
        display.setImage(discard,image);
        display.setImage(loc,null);
      }
      // client.playCard(toCard(image));
    }
   
    
  }
  public int pickColor()
  {
    display.setColor(new Location(3,4),new Color(100,0,0));
    display.setColor(new Location(3,5),new Color(100,100,0));
    display.setColor(new Location(3,6),new Color(0,0,100));
    display.setColor(new Location(3,7),new Color(0,100,0));
    int color=0;
    while(color == 0)
    {
      display.pause(100);
      Location clickLoc = display.checkLastLocationClicked();
      if (clickLoc != null)
      {
        if(clickLoc.equals(new Location(3,4)))
          color = 1;
        else if(clickLoc.equals(new Location(3,5)))
          color=2;
        else if(clickLoc.equals(new Location(3,7)))
          color=3;
        else if(clickLoc.equals(new Location(3,6)))
          color=4;
      }
    }
    return color;
  }
  
  
  //this method is called at regular intervals
  public void step()
  {

  }
  
  
  public void initialize(String top, List<String> hand)
  {
    display.setImage(discard, top);
    for(int i = 0; i < hand.size(); i++)
    {
      display.setImage(new Location(8, i+1), hand.get(i)); 
    }
    //display.setImage
    display.setImage(new Location(0,4),"card.png");
    display.setImage(new Location(4,8),"card.png");
    display.setImage(new Location(4,0),"card.png");
    Graphics g = display.getGraphics();
    g.setColor(GridDisplay.toJavaColor(new Color(170,170,170)));
    g.drawString(7+"", 25, 265);
    g.drawString(7+"", 465, 265);
    g.drawString(7+"", 245, 45);
  }
  
  
  //example:  blue_0_large.png
  public Card toCard(String s)
  {
    int rank=0;
    int color=0;
    
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
  public static void main(String[] args) throws InterruptedException
  {
    Gui gui = new Gui();
    Game g = new Game(4);
    gui.initialize(g.discard.peek().toString(), convert(g.hands.get(0)));
    Thread.sleep(100);
    gui.play();
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
  
  
  //test method
  public List<String> convert(List<Card> l)
  {
    List<String> ret = new ArrayList<String>();
    for(int i = 0; i< l.size(); i++)
    {
      ret.add(l.get(i).toString());
    }
    return ret;
  }
}
