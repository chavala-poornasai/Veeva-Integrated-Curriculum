
import java.io.File;

class ListFiles{
    public static void main(String[] args){
        File folder=new File("JavaPractice");

        if(folder.exists() && folder.isDirectory()){
            File[] files=folder.listFiles();

            for(File f:files){
                if(f.isFile()){
                    System.out.println("File: "+f.getName());
                }
                else if(f.isDirectory()){
                    System.out.println("Folder: "+f.getName());
                }
            }
        }
    }
}
