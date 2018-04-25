import java.util.*;

public class Gui
{
  private GridDisplay display;
  private String image;

  
  private static Location discard = new Location(4,4);
  
  public Gui()
  {
    
    display = new GridDisplay(9,9);
    display.setTitle("UNO!");
    display.setImage(new Location(8,4) , "blue_0_large.png");
    display.setImage(new Location(8,2) , "blue_0_large.png");
    display.setImage(new Location(8,3) , "blue_0_large.png");
    display.setImage(new Location(8,5) , "blue_0_large.png");
    display.setImage(new Location(8,6) , "blue_0_large.png");
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
    }
  }
  
  public void cardPlayed( Card c, int playerNum )
  {
    update(c);
  }
  
  public void update(Card c)
  {
    display.setImage(discard, c.toString());
  }

  //called when the user clicks on a location.
  //that location is passed to this method.
  private void locationClicked(Location loc)
  {
    if(display.getImage(loc)!=null && !loc.equals(discard))
    {
      image=display.getImage(loc);
      System.out.println(image);
      display.setImage(discard,image);
      display.setImage(loc,null);
     // client.playCard(toCard(image));
    }
   
    
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
    else if(r.equals("drawfour"))
      rank=12;

    return new Card(rank,color);
  }
  
  //this code starts a game when you click the run button
  public static void main(String[] args)
  {
    Gui g = new Gui();
    g.play();
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
