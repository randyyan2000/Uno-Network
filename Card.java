public class Card
{
  //color constants
  public static final int WILD_COLOR = 0;
  public static final int RED = 1;
  public static final int YELLOW = 2;
  public static final int GREEN = 3;
  public static final int BLUE = 4;
  
  //special rank constants
  public static final int SKIP_RANK = 10;
  public static final int REVERSE_RANK = 11;
  public static final int DRAWTWO_RANK = 12;
  
  public static final int DRAWFOUR_RANK = 12;
  public static final int WILD_RANK = 0;
  
  private int rank;
  private int color;
  
  public Card(int rank, int color)
  {
    this.rank = rank;
    this.color = color;
  }
  
  public int getRank()
  {
    return rank;
  }
  
  public int getColor()
  {
    return color;
  }
  
  public boolean equals(Object o)
  {
    return (this.rank == ((Card)o).getRank()) && (this.color == ((Card)o).getColor());
  }
  
  public static boolean validMove(Card top, Card bottom)
  {
    return top.getColor() == WILD_COLOR ||
           top.getColor() == bottom.getColor() ||
           top.getRank() == bottom.getRank();
  }
  
  public String toCode()
  {
    String s = ""+rank+" ";
    
    if(color == Card.BLUE)
      s+=("B");
    else if(color==Card.GREEN)
      s+=("G");
    else if(color==Card.YELLOW)
      s+=("Y");
    else if(color==Card.RED)
      s+=("R");
    else
      s+=("W");
    return s;
  }
  
  public String toString()
  {
    String s = "";
    if(color == Card.BLUE)
      s+=("blue");
    else if(color==Card.GREEN)
      s+=("green");
    else if(color==Card.YELLOW)
      s+=("yellow");
    else if(color==Card.RED)
      s+=("red");
    else
      s+=("wild");
    s+=("_");
    int r = rank;

    if(r == Card.SKIP_RANK)
      s+="skip";
    else if(r == Card.REVERSE_RANK)
      s+="reverse";
    else if(r==Card.DRAWFOUR_RANK && color == Card.WILD_COLOR)
      s+="pickfour";
    else if(r==Card.DRAWTWO_RANK)
      s+="picker";
    else if(r==Card.WILD_RANK && color == Card.WILD_COLOR)
      s+="wild";
    else if(r == 13)
      s+="wild";
    else
      s += r;
    s+="_large.png";
      
    return s;
  }
  
}