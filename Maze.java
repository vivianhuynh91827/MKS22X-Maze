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
    String newMaze = "";
    File f = new File(filename);
    Scanner in = new Scanner(f);
    int rows = 0;
    while (in.hasNextLine()) {
      rows++;
    }
    int cols = 0;
    in = new Scanner(f);
    String character = in.next();
    while (in.hasNext() && !character.equals("\n") ){
      character = in.next();
      cols++;
    }
    maze = new char[rows][cols];
  }

}
