import java.util.*;

public class SudokuDriver {
  public static void main(String[] args) {
    
    Scanner kb = new Scanner(System.in);
    SudokuGenerator board = new SudokuGenerator();
    
    
    board.fillBoard();
   //  System.out.println("\nSolved Board: ");
//     board.printBoard();
    board.makeSolvable();
    board.startMenu();
    System.out.println("\n\nSudoku Board: ");
    board.printBoard();

    
    while(board.mistakes != 5) {
      System.out.println("If you insert the wrong row or column, insert 0 for the guess to retry");
      System.out.print("Insert row coordinate of the cell that you want solve for: ");
      int row = kb.nextInt();
      while(row < 0 || row > 8) {
        System.out.print("Insert row coordinate of the cell that you want solve for: ");
        row = kb.nextInt();
      }
      
      System.out.print("Insert column coordinate of the cell that you want solve for: ");
      int column = kb.nextInt();
      while(column < 0 || column > 8) {
        System.out.print("Insert column coordinate of the cell that you want solve for: ");
        column = kb.nextInt();
      }
      
      System.out.print("Insert your guess: ");
      int guess = kb.nextInt();
      
      board.makeGuess(row,column,guess);
      if(board.checkWin()) {
        System.out.println("\n\n\nYOU WIN!!!!!");
        System.exit(0);
      }
    }
    
    System.out.println("\n\n\nGAME OVER!!!");
  }
}