import java.util.*;
import java.io.*;

public class Maze {
  private char[][] maze;
  private boolean animate;

  /*Constructor loads a maze text file, and sets animate to false by default.

      1. The file contains a rectangular ascii maze, made with the following 4 characters:
      '#' - Walls - locations that cannot be moved onto
      ' ' - Empty Space - locations that can be moved onto
      'E' - the location of the goal (exactly 1 per file)
      'S' - the location of the start(exactly 1 per file)

      2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!

      3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:
         throw a FileNotFoundException or IllegalStateException
  */
  public Maze(String filename) throws FileNotFoundException {
    animate = false;
    File f = new File(filename);
    Scanner in = new Scanner(f);
    int rows = 0;
    while (in.hasNextLine()) {
      rows++;
      in.nextLine();
    }
    in = new Scanner(f);
    String row = in.nextLine();
    int cols = row.length();
    maze = new char[rows][cols];
    System.out.println(rows);
    System.out.println(cols);
    in = new Scanner(f);
    for (int r = 0; r < rows; r++) {
      String line = in.nextLine();
      for (int c = 0; c < cols; c++) {
        maze[r][c]=line.charAt(c);
      }
    }
  }

  private void wait(int millis) {
    try {
      Thread.sleep(millis);
    }
    catch (InterruptedException e) {}
  }

  public void setAnimate(boolean b) {
    animate = b;
  }

  public void clearTerminal() {
    System.out.println("\033[2J\033[1;1H");
  }

  public String toString() {
    String s = "";
    for (int r = 0; r < maze.length; r++) {
      for (int c = 0; c < maze[0].length; c++) {
        s+=maze[r][c];
      }
      s+="\n";
    }
    return s.substring(0,s.length()-1);
  }

  public static void main(String[] args) {
    try {
      Maze test = new Maze("data1.dat");
      System.out.println(test);
    }
    catch(FileNotFoundException e) {
      System.out.println(e);
    }
  }
}
