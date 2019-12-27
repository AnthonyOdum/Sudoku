//Sudoku Generator by Anthony Odum

import java.util.*;

public class SudokuGenerator {
  
  //****Variables***
  
  Scanner kb = new Scanner(System.in);
  static int[][] board = new int[9][9];
  int[][] solvedBoard = new int[9][9];
  int mistakes;
  
  //Holds the list of invalid numbers for each cell on the board
  static int[][] invalidListPerCell = new int[81][9];
  
  //Holds the coordinates for each cell
  static String[] cells = new String[]{"00","01","02","03","04","05","06","07","08","10","11","12","13","14","15","16","17","18","20","21","22","23","24","25","26","27","28",
  "30","31","32","33","34","35","36","37","38","40","41","42","43","44","45","46","47","48","50","51","52","53","54","55","56","57","58","60","61","62","63","64","65","66","67","68",
  "70","71","72","73","74","75","76","77","78","80","81","82","83","84","85","86","87","88"};
  
  //Temporary list for invalid numbers of the cell currently being checked
  static int[] invalidList = new int[9];
  static int invalidListCount = 0;
  
  //****Constructor****
  
  public SudokuGenerator() {
    for(int x = 0; x < 9; x++) {
      for(int y = 0; y < 9; y++) {
        board[x][y] = 0;
      }
    }
    
    mistakes = 0;
  }
  
  
  //****Private Helper Methods****
  
  
  
  //Matches cell coordinates to invalidListPerCell index
  private static int matchCell(int row, int column) {
    String cell = Integer.toString(row) + Integer.toString(column);
    for(int i = 0; i < cells.length; i++) {
      if(cell.equals(cells[i])) {
        return i;
      }
    }
    return -1;
  }
  
  
  //Clears the temporary invalidList
  private static void clearInvalidList() {
    for(int index = 0; index < invalidListCount; index++) {
      invalidList[index] = 0;
    }
  }
  
  //Fills the temporary invalidList with numbers previously determined as invalid for the particular cell
  private static void fillInvalidList(int cellIndex) {
    for(int index = 0; index < invalidListPerCell[cellIndex].length; index++) {
      if(invalidListPerCell[cellIndex][index] != 0) {
        invalidList[invalidListCount] = invalidListPerCell[cellIndex][index];
        invalidListCount++;
      }
    }
  }
  
  //Clears the invalidList for the particular cell
  private static void clearSavedInvalidList(int cellIndex) {
    for(int index = 0; index < invalidListPerCell[cellIndex].length; index++) {
      invalidListPerCell[cellIndex][index] = 0;
    }
  }
    
  //Fills the downward diagnol sub-grids
  private static void fillDownwardDiagnol() {
    //coordinates of the cell in the top-left corner of the grid
    int r = 0;
    int c = 0;
    
    /*When the cell in the bottom-right corner of the grid is filled
    *the intended sub-grids will all be filled
    */
    while(board[8][8] == 0) {
      //Keeps track of the cell currently being filled
      for(int currentRowIndex = r; currentRowIndex < r+3; currentRowIndex++) {
        for(int currentColumnIndex = c; currentColumnIndex < c+3; currentColumnIndex++) {
          board[currentRowIndex][currentColumnIndex] = (int)((Math.random() * 9) + 1);
          
          //Checks the entire sub-grid for any duplicate numbers
          for(int rowIndex = r; rowIndex < r+3; rowIndex++) {
            for(int columnIndex = c; columnIndex < c+3; columnIndex++) {
              if(rowIndex == currentRowIndex && columnIndex == currentColumnIndex) {
                //do nothing...
              } else if(board[currentRowIndex][currentColumnIndex] == board[rowIndex][columnIndex]) {
                //if found, sets the cell to a new random number and rechecks sub-grid
                board[currentRowIndex][currentColumnIndex] = (int)((Math.random() * 9) + 1);
                rowIndex = r-1;
                break;
              }
            }
          }
        }
      }
      //Moves to the next downward diagnol sub-grid
      r+=3;
      c+=3;
    } 
  }
  
  //Checks the 3x3 subgrid for any duplicates
  private static boolean checkSubGrid(int row, int column, int number) {
    int r = row - row%3;
    int c = column - column%3;
    
    for(int rowIndex = r; rowIndex < r+3; rowIndex++) {
      for(int columnIndex = c; columnIndex < c+3; columnIndex++) {
        if(rowIndex == row && columnIndex == column) {
          //do nothing...
        } else if(number == board[rowIndex][columnIndex]) {
          invalidList[invalidListCount] = number;
          invalidListCount++;
          return false;
        }
      }
    }
    
    return true;
  }
  
  //checks the row and column for any duplicates
  private static boolean checkRowAndColumn(int row, int column, int number) {
    for(int i = 0; i < 9; i++) {
      if(i == column) {
        //making sure cell doesn't check against itself
        if(number == board[i][column]) {
          invalidList[invalidListCount] = number;
          invalidListCount++;
          return false;
        }
      } else if(i == row) {
        //making sure cell doesnt check against itself
        if(number == board[row][i]) {
          invalidList[invalidListCount] = number;
          invalidListCount++;
          return false;
        }
      } else {
        if(number == board[row][i] || number == board[i][column]) {
          invalidList[invalidListCount] = number;
          invalidListCount++;
          return false;
        }
      }
    }
    
    return true;
  }
  
  private void addMistake() {
    mistakes += 1;
  }
  
  private void printCorrectBoard() {
    
    for(int x = 0; x < 9; x++) {
      if (x == 0) {
          System.out.println("           " + mistakes + "/5 mistakes");
          System.out.println("----------------------");
        }
      for(int y = 0; y < 9; y++) {
        if(board[x][y] == 0) {
          System.out.print("0 ");
        } else {
          System.out.print(board[x][y] + " ");
        }
        if((y+1)%3==0 && y+1 != 9) {
          System.out.print("| ");
        } else if (y+1 == 9 && (x == 3 )) {
          System.out.print("| " + x + "    █     █   █  █  █   █  █   █     █       █");
        } else if (y+1 == 9 && (x == 4)) {
          System.out.print("| " + x + "    █     █   █  ███    ███    ███   █       █");
        } else if (y+1 == 9 && (x == 5)) {
          System.out.print("| " + x + "    █     █   █  █  █   █  █   █     █       █");
        } else if (y+1 == 9) {
          System.out.print("| " + x);      
        }
      }
        System.out.println();
        if(x+1 == 3) {
          System.out.println("----------------------       ████  █████  ████   ████   ████  ████  █████");
        } else if(x+1 == 6) {
          System.out.println("----------------------       ████  █████  █   █  █   █  ████  ████    █");
        } else if (x+1 == 9) {
          System.out.println("----------------------");
          for(int i = 0; i < 9; i++) {
            if((i+1)%3 == 0 && i+1 != 9) {
              System.out.print(i + "   ");
            } else {
              System.out.print(i + " ");
            }
          }
        } 
      }
      
      System.out.println("\n");
    }
    
    private void printIncorrectBoard() {
    
    for(int x = 0; x < 9; x++) {
      if (x == 0) {
          System.out.println("           " + mistakes + "/5 mistakes");
          System.out.println("----------------------");
        }
      for(int y = 0; y < 9; y++) {
        if(board[x][y] == 0) {
          System.out.print("0 ");
        } else {
          System.out.print(board[x][y] + " ");
        }
        if((y+1)%3==0 && y+1 != 9) {
          System.out.print("| ");
        } else if (y+1 == 9 && (x == 3 )) {
          System.out.print("| " + x + "      █    █ █   █  █     █   █  █  █   █  █   █     █       █");
        } else if (y+1 == 9 && (x == 4)) {
          System.out.print("| " + x + "      █    █  █  █  █     █   █  ███    ███    ███   █       █");
        } else if (y+1 == 9 && (x == 5)) {
          System.out.print("| " + x + "      █    █   █ █  █     █   █  █  █   █  █   █     █       █");
        } else if (y+1 == 9) {
          System.out.print("| " + x);      
        }
      }
        System.out.println();
        if(x+1 == 3) {
          System.out.println("----------------------       █████  ██    █  ████  █████  ████   ████   ████  ████  █████");
        } else if(x+1 == 6) {
          System.out.println("----------------------       █████  █    ██  ████  █████  █   █  █   █  ████  ████    █");
        } else if (x+1 == 9) {
          System.out.println("----------------------");
          for(int i = 0; i < 9; i++) {
            if((i+1)%3 == 0 && i+1 != 9) {
              System.out.print(i + "   ");
            } else {
              System.out.print(i + " ");
            }
          }
        } 
      }
      
      System.out.println("\n");
    }
  
  //*****Public Methods*****
  
  //Prints Board lol, includes spacing between sub-grids as well as coordinates
  public void printBoard() {
    
    for(int x = 0; x < 9; x++) {
      if (x == 0) {
          System.out.println("           " + mistakes + "/5 mistakes");
          System.out.println("----------------------");
        }
      for(int y = 0; y < 9; y++) {
        if(board[x][y] == 0) {
          System.out.print("0 ");
        } else {
          System.out.print(board[x][y] + " ");
        }
        if((y+1)%3==0 && y+1 != 9) {
          System.out.print("| ");
        } else if (y+1 == 9) {
          System.out.print("| " + x);      
        }
      }
        System.out.println();
        if((x+1)%3==0 && x+1 != 9 && x+1 != 0) {
          System.out.println("----------------------");
        } else if (x+1 == 9) {
          System.out.println("----------------------");
          for(int i = 0; i < 9; i++) {
            if((i+1)%3 == 0 && i+1 != 9) {
              System.out.print(i + "   ");
            } else {
              System.out.print(i + " ");
            }
          }
        } 
      }
      
      System.out.println("\n");
    }

  //Inserts random 0's in board to allow board to be solved
  public void makeSolvable() {
    //makes a copy of the solved board
    for(int i = 0; i < board.length; i++) {
      for(int j = 0; j < board[i].length; j++) {
        solvedBoard[i][j] = board[i][j];
      }
    }
    
    //picks 6 random cells in each row to "hide"
    int count = 0;
    while(count < 9) {
      for(int c = 0; c < 6; c++) {
        int columnIndex = (int)((Math.random() * 9));
        if(board[count][columnIndex] != 0) {
          board[count][columnIndex] = 0;
        } else {
          c -= 1;
        }
      }
      count++;
    }
  }
  
  //Starting number
  static int number = (int)((Math.random() * 9) + 1);
  
  public void fillBoard() {
    //fills the downward diagnol
    fillDownwardDiagnol();
    
    //iterartes through the entire board
    for(int row = 0; row < 9; row++) {
      for(int column = 0; column < 9; column++) {
        
        if(board[row][column] != 0) {
          //if the cell is filled skip it
        } else {
          boolean unique = false;
          //making sure the number is not a duplicate
          while(unique == false) {
            unique = true;
              
            //if the current cell is invalid for all numbers, this backtracks to the previous cell to try a different number
            while(invalidListCount == 9) {
              if(column == 0) {
                clearInvalidList();
                invalidListCount = 0;
                int cellIndex = matchCell(row, column);
                clearSavedInvalidList(cellIndex);
                row-=1;
                column = 8;
                cellIndex = matchCell(row, column);
                fillInvalidList(cellIndex);
                int invalidNumber = board[row][column];
                invalidList[invalidListCount] = invalidNumber;
                invalidListCount++;
                board[row][column] = 0;
              } else {
                clearInvalidList();
                invalidListCount = 0;
                int cellIndex = matchCell(row, column);
                clearSavedInvalidList(cellIndex);
                column-=1;
                cellIndex = matchCell(row, column);
                fillInvalidList(cellIndex);
                int invalidNumber = board[row][column];
                invalidList[invalidListCount] = invalidNumber;
                invalidListCount++;
                board[row][column] = 0;
              }
            }
            
            //checks the number being put into the cell against the numbers currently in the invalidList
            for(int badValuesIndex = 0; badValuesIndex < invalidListCount; badValuesIndex++) {
              if(number == invalidList[badValuesIndex]) {
                number++;
                if(number > 9) {
                  number = 1;
                }
                badValuesIndex = -1;
              }
            }
            
            /*checks row, column and subgrid
            * if the number is not duplicated, then it is entered into the cell, the invalidList is saved and then cleared
            * if the number does not work then it is incremented and checked again
            */
            if(checkSubGrid(row,column,number)) {
              if(checkRowAndColumn(row,column,number)) {
                board[row][column] = number;
                int cellIndex = matchCell(row,column);
                for (int index = 0; index < invalidListCount; index++) {
                  invalidListPerCell[cellIndex][index] = invalidList[index]; 
                }
                clearInvalidList();
                invalidListCount = 0;
              } else {
                number += 1;
                if(number > 9) {
                  number = 1;
                }
                unique = false;
              }
            } else {
              number += 1;
              if(number > 9) {
              number = 1;
              }
              unique = false;
            } 
          }  
        }
      }
    }
  }
  
  public void makeGuess(int row, int column, int guess) {
    if(guess == solvedBoard[row][column]) {
      board[row][column] = guess;
      printCorrectBoard();
    } else if(guess == 0) {
      printBoard();
    } else {
      addMistake();
      printIncorrectBoard();
    }
  }
  
  public boolean checkWin() {
    boolean win = true;
    for(int i = 0; i < board.length; i++) {
      for(int j = 0; j < board[i].length; j++) {
        if(board[i][j] != solvedBoard[i][j]) {
          win = false;
        }
      }
    }
    
    return win;
  }
  
  public void startMenu() {
    System.out.println("===============================================");
    System.out.println("===============================================");
    System.out.println("  ██████  █   █  ████    █████  █   █  █   █");
    System.out.println("  █       █   █  █   █   █   █  █  █   █   █");
    System.out.println("  ██████  █   █  █    █  █   █  ███    █   █");
    System.out.println("       █  █   █  █   █   █   █  █  █   █   █");
    System.out.println("  ██████   ███   ████    █████  █   █   ███");
    System.out.println("===============================================");
    System.out.println("===============================================");
    
    System.out.print("\n\nInsert 1 to begin: ");
    int start = kb.nextInt();
    while(start != 1) {
      System.out.println("I'm sorry, I don't understand. Please try again");
      System.out.print("Insert 1 to begin: ");
      start = kb.nextInt();
    }
    System.out.println("\n\n\n\n\n");
  }
}