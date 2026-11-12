
import java.io.File;

class RenameFile{
    public static void main(String[] args){
        File oldFile=new File("myFile1.txt");
        File newFile=new File("myFile2.txt");

        if(oldFile.renameTo(newFile)){
            System.out.println("File renamed successfully");
        }
        else{
            System.out.println("Rename failed");
        }
    }
}
