import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
class File1{
	public static void main(String args[]){
	/*
	1.Basic file operations
	*/	
	File obj = new File("myFile1.txt");
	/*try{
		createFile(obj);
	}
	catch(Exception e){
		System.err.println("An error occures while opening or creating the file.");
		e.printStackTrace();
	}*/
	//writeFile(obj);
	//readFile(obj);
	//canReadCheck(obj);
	//canWriteCheck(obj);
	//exists(obj);
	String path=absPath(obj);
	//System.out.println("Absolute PATH:"+path);
	//deleteFile(obj,path);
	
	}
	static void createFile(File obj)throws IOException{
		//if(!obj.exists()){
		//try{
			//File obj=new File("myFile1.txt");
			if(obj.createNewFile()){
				//throw new Exception();
				System.out.println("FIle created:"+obj.getName());
			}
			else{
				System.out.println("File already exists!");
			}
		//}
		//}
	}
	static void writeFile(File obj){
		
		try(FileWriter fw=new FileWriter("myFile1.txt")){
			fw.write("The content is being written into the file the second time\n");
			fw.write("Trying to write second line");
			System.out.println("Successfully written");
		}
		catch(IOException e){
			System.out.println("An errorrrr!");
			e.printStackTrace();
		}
	}
	static void readFile(File obj){
		try(Scanner fr=new Scanner(obj)){
		System.out.println("Read content");
			while(fr.hasNextLine()){
				String data=fr.nextLine();
				System.out.println(data);
				
			}
		}
		catch(IOException e){
			System.out.println("An errorrrr!");
			e.printStackTrace();
		}
	}
	static void canReadCheck(File obj){
		if(obj.canRead()){
			System.out.println("The file is readable");
		}
		else{
			System.out.println("The file is not readable");
		}
	}
	static void canWriteCheck(File obj){
		if(obj.canWrite()){
			System.out.println("The file is writable");
		}
		else{
			System.out.println("The file is not writable");
		}
	}
	static void exists(File obj){
		if (obj.exists()) {
            System.out.println("The file exists.");  // One-line explanation
        } else {
            System.out.println("The file does not exist.");
        }
	}
	static String absPath(File obj){
		return obj.getAbsolutePath();
	}
	static void deleteFile(File obj,String path){
		if(obj.delete()){
			System.out.println("The deleted file is : " + obj.getName());
        }
        else {
            System.out.println(
                "Failed in deleting the file.");
        }
	}
}


