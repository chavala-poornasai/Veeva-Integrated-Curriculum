
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

class BufferedWrite{
    public static void main(String[] args){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("myFile1.txt"))){
            bw.write("Java File Handling");
            bw.newLine();
            bw.write("Learning BufferedWriter");
            bw.newLine();
            bw.write("Third Line");

            System.out.println("Successfully written");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
