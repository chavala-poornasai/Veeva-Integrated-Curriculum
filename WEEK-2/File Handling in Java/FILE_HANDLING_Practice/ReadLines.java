
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.io.IOException;

class ReadLines{
    public static void main(String[] args){
        Path path=Path.of("myFile1.txt");

        try{
            List<String> lines=Files.readAllLines(path);

            for(String line:lines){
                System.out.println(line);
            }

            System.out.println("Total lines: "+lines.size());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
