import java.io.*;
import java.util.*;
class Test{
  /*
      generate random graph with less than 10 to several hundreds vertices
      and save to 10 files
  */
  public static void main(String[] args){
    int n = 0;
    int edge,vertex; 
    while(n < 10){
      Random rand = new Random();
      vertex = rand.nextInt(1000) + 2;
      int [][] graph = new int[vertex][vertex];
      for(int i = 0; i <vertex ; ++i){
        for(int j = 0; j<vertex; ++j){
          graph[i][j] = 0;
        }
      }

      // randomly generate # of edges
      
      edge = rand.nextInt(vertex) + 1;
      //System.out.println(edge);
      for(int i = edge; i>0;i--){
        int index1 = rand.nextInt(vertex);
        int index2 = rand.nextInt(vertex);
        if(graph[index1][index2] == 1 || index1 == index2) continue;
        else{
          graph[index1][index2] = 1;
          graph[index2][index1] = 1;
        }
      }
      try{
        // create file
        String file = "f"+Integer.toString(n);
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        // write graph to file
        for(int i = 0; i <vertex ; ++i){
        String buffer = "";
        for(int j = 0; j<vertex; ++j){
          buffer += Integer.toString(graph[i][j])+" "; 
        }
          writer.println(buffer);
      }
        writer.close();
      }
      catch (IOException ex){
        //System.out.println("file create failed");
      }
      //System.out.println("file created");
      n++;
    }
    
  }
}