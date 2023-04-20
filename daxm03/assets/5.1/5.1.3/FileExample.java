
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Examples of directories and files creation and deletion
 * @author Jose
 */
public class FileExample {

    /**
     * example of creating a directory (or some directories tree) 
     * and creating some files in it with same name and sequential number
     * @param dirPath path of directory
     * @param fileBaseName base name for files
     * @param fileExtension extension for files
     * @param numberOfFiles number of files to create
     * @param multiDir true if multiple directories are to be created
     * @throws IOException if some IO error occurs
     */
    public void createDirAndFiles(String dirPath, 
            String fileBaseName, String fileExtension, 
            int numberOfFiles, boolean multiDir) 
            throws IOException {
        boolean success = false;
        //create directory
        File dir = new File(dirPath);
        success = (multiDir) ? dir.mkdirs() : dir.mkdir();
        if (success) {  //directory created by mkdir operation
            System.out.format("Directory %s successfully created\n", dir.getAbsolutePath());
        } else {
            System.out.format("File %s already exists\n", dir.getAbsolutePath());
        }
        if (dir.exists() && dir.isDirectory()) { //check directory existence
            //create some files with basename, extension and a sequential number
            for (int i = 0; i < numberOfFiles; i++) {
                //define f name using a sequential number
                String filename = String.format(
                        "%s%02d.%s", 
                        fileBaseName, 
                        i, 
                        fileExtension);
                File file = new File(dir, filename);
                //create f
                success = file.createNewFile();
                if (success) {
                    System.out.format("File %s successfully created\n", file.getAbsolutePath());
                } else {
                    System.out.format("File %s already exists\n", file.getAbsolutePath());
                }
            }
        }
    }
    
    /**
     * example of how to delete a directory and all of its files
     * (remember that only an empty directory can be deleted)
     * @param dirPath path to directory
     */
    public void deleteDirectoryAndFiles(String dirPath) {
        boolean success = false;
        File dir = new File(dirPath);
        //get files in directory
        File[] files = dir.listFiles();
        //delete files
        for (File f : files) {
            success = f.delete();
            System.out.format("File %s successfully deleted\n", f.getAbsolutePath());
        }
        //delete directory
        success = dir.delete();
        System.out.format("Directory %s successfully deleted\n", dir.getAbsolutePath());
    }
    
    /**
     * example of how to display the names of all files in a directory
     * @param dirPath path to directory
     */
    public void listDirFilenames(String dirPath) {
        File dir = new File(dirPath);
        String[] filenames = dir.list();
        System.out.println(Arrays.toString(filenames));
    }
    
    /**
     * example of how to delete files with even number in given directory
     * @param dirPath path to directory
     */
    public void deleteFilesWithEvenNumber(String dirPath) {
        File dir = new File(dirPath);
        for (File f : dir.listFiles()) {
            //split filename into name and extension
            String[] parts = f.getName().split("[.]");
            //get name
            String name = parts[0];
            //get digits from name (last two characters)
            String digits = name.substring(name.length()-2);
            //parse into an integer
            int number = Integer.parseInt(digits);
            if ((number%2)==0) { //if even
                //delete file
                boolean success = f.delete();
                if (success) {
                    System.out.format("File %s successfully deleted\n", f.getAbsolutePath());
                }
            }
        }        
    }
    
    public static void main(String[] args)  {
        FileExample fe = new FileExample();
        try {
            //-----
            System.out.println("Create single directory and files");
            //create directory and some files within
            fe.createDirAndFiles("files", "hello", "txt", 5, false);
            //display directory files
            fe.listDirFilenames("files");
            //delete directory and files
            fe.deleteDirectoryAndFiles("files");
            //-----
            System.out.println("Create multiple directories and files");
            //create directories and some files within
            fe.createDirAndFiles("files/greetings", "hello", "txt", 5, true);
            fe.listDirFilenames("files/greetings");
            fe.deleteDirectoryAndFiles("files/greetings");
            //-----
            System.out.println("Create multiple directories and files and delete only files with even number");
            //create another time directories and files and delete only files with even number
            fe.createDirAndFiles("files/greetings", "hello", "txt", 5, true);
            fe.deleteFilesWithEvenNumber("files/greetings");
            //delete alll directories created
            fe.deleteDirectoryAndFiles("files/greetings");
            fe.deleteDirectoryAndFiles("files");
        } catch (IOException ex) {
            Logger.getLogger(FileExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
