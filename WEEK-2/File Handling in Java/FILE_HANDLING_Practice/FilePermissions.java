
import java.io.File;

class FilePermissions{
    public static void main(String[] args){
        File obj=new File("myFile1.txt");

        System.out.println("Size: "+obj.length());

        if(obj.setReadOnly()){
            System.out.println("File is now read-only");
        }

        System.out.println("Can read: "+obj.canRead());
        System.out.println("Can write: "+obj.canWrite());
    }
}
