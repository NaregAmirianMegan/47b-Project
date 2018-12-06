import java.util.*;
import java.io.*;

public class tester {

  public static int cantorSquared(int x, int y, int a, int b) {
    int c1 = ((x + y + 1)*(x + y))/2 + y;
    int c2 = ((a + b + 1)*(a + b))/2 + b;
    return ((c1 + c2 + 1)*(c1 + c2))/2 + c1;
  }

  public static void main(String[] args) {
    int[][] arr = new int[2][3];
    System.out.println(arr.length + " " + arr[0].length);
  }
}
