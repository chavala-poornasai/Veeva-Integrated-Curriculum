
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

class NIOOperations{
    public static void main(String[] args){
        Path path=Paths.get("nioFile.txt");

        try{
            Files.createFile(path);

            Files.writeString(path,"Learning Java NIO");

            String data=Files.readString(path);

            System.out.println(data);
            System.out.println("Exists: "+Files.exists(path));
            System.out.println("Size: "+Files.size(path));

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
