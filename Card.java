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
  
  public static boolean validMove(Card top, Card bottom)
  {
    return top.getColor() == WILD_COLOR ||
           top.getColor() == bottom.getColor() ||
           top.getRank() == bottom.getRank();
  }
  
}