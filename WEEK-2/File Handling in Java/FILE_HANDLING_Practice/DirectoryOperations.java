
import java.io.File;

class DirectoryOperations{
    public static void main(String[] args){
        File folder=new File("JavaPractice");

        if(folder.mkdir()){
            System.out.println("Directory created");
        }
        else{
            System.out.println("Directory already exists");
        }

        File nested=new File("JavaPractice/Files/Examples");

        if(nested.mkdirs()){
            System.out.println("Nested directories created");
        }
        else{
            System.out.println("Directories already exist");
        }

        System.out.println("Is directory: "+folder.isDirectory());
    }
}
