
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class BufferedRead{
    public static void main(String[] args){
        try(BufferedReader br=new BufferedReader(new FileReader("myFile1.txt"))){
            String line;

            while((line=br.readLine())!=null){
                System.out.println(line);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
