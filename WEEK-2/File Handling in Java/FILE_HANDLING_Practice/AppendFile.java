
import java.io.FileWriter;
import java.io.IOException;

class AppendFile{
    public static void main(String[] args){
        try(FileWriter fw=new FileWriter("myFile1.txt",true)){
            fw.write("\nAppending new content");
            fw.write("\nThis is another line.....");

            System.out.println("Content appended");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
