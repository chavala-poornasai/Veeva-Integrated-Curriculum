
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class ByteStreams{
    public static void main(String[] args){
        try(FileOutputStream fos=new FileOutputStream("data.txt")){
            String data="Learning Java Byte Streams";
            fos.write(data.getBytes());
        }
        catch(IOException e){
            e.printStackTrace();
        }

        try(FileInputStream fis=new FileInputStream("data.txt")){
            int ch;

            while((ch=fis.read())!=-1){
                System.out.print((char)ch);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
