
import java.io.File;

class FileProperties{
    public static void main(String[] args){
        File obj=new File("myFile1.txt");

        System.out.println("Name: "+obj.getName());
        System.out.println("Path: "+obj.getPath());
        System.out.println("Parent: "+obj.getParent());
        System.out.println("Absolute Path: "+obj.getAbsolutePath());
        System.out.println("Is File: "+obj.isFile());
        System.out.println("Is Directory: "+obj.isDirectory());
        System.out.println("Size: "+obj.length()+" bytes");
        System.out.println("Last Modified: "+obj.lastModified());
    }
}
