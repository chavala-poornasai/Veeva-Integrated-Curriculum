
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class CopyFile{
    public static void main(String[] args){
        try(FileInputStream fis=new FileInputStream("input.pdf");
            FileOutputStream fos=new FileOutputStream("output.pdf")){

            byte[] buffer=new byte[1024];
            int bytesRead;

            while((bytesRead=fis.read(buffer))!=-1){
                fos.write(buffer,0,bytesRead);
            }

            System.out.println("File copied successfully");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
