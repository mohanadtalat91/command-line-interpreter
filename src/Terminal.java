import java.io.*;
import java.util.*;
import java.io.File;
import java.io.IOException;

public class Terminal {
    Parser parser;
    String path_;
    public void setParser(Parser obj){
        parser = obj;
        path_=System.getProperty("user.dir");
    }
    //////////////////////////////////////////////////////////

    public void touch() throws IOException {
        String path = parser.getArgs()[0];
        char backslash = '\\';
        String name = "";

        int i, j;
        for (i = path.length() - 1; i >= 0; i--) {
            if(path.charAt(i) == backslash){
                break;
            }
            name = path.charAt(i) + name;
        }
        String newPath="";
        for(j = 0;j < i;j++){
            newPath += path.charAt(j);
        }


        if(path.charAt(1) == ':') {
            System.setProperty("user.dir",newPath);

            File newFile = new File(newPath+"\\"+name);
            newFile.createNewFile();
        }
        else{
            File newFile = new File(path_ +"\\"+newPath+"\\"+name);
            newFile.createNewFile();
        }
    }
    //-------------------------------------------------completed
    public void cat() throws IOException{
        String [] Files = parser.getArgs();
        String currentPath = path_;
        File dir = new File (currentPath);
        String [] contents = dir.list();
        try{
            int i,j;
            for(i = 0;i < Files.length;i++){
                for(j = 0;j < contents.length;j++){
                    if(Files[i].equals(contents[j])){
                        break;
                    }
                }
                if(j == contents.length){
                    throw new IOException();
                }
            }
            if(Files.length == 1){
                File first = new File(Files[0]);
                Scanner read1 = new Scanner(first);
                while(read1.hasNextLine()){
                    System.out.println(read1.nextLine());
                }
                read1.close();
            }

            else if(Files.length > 1){
                File first = new File(Files[0]);
                File second = new File(Files[1]);
                Scanner read1 = new Scanner(first);
                while(read1.hasNextLine()){
                    System.out.println(read1.nextLine());
                }
                read1.close();
                Scanner read2 = new Scanner(second);
                while(read2.hasNextLine()){
                    System.out.println(read2.nextLine());
                }
                read2.close();
            }
        }
        catch (IOException e) {
            System.out.println("cannot find the file");
        }
    }
    public void echo(){
        System.out.println(parser.getArgs()[0]);
    }
    public void pwd() throws IOException {
        System.out.println(path_);
    }
    public void exit(){
        System.exit(0);
    }
    public void cd() throws IOException {
        if (parser.getArgs().length == 0) {
            return;
        } else if (parser.getArgs()[0].equalsIgnoreCase("..")) {

            String path = "";
            int i, j;
            for (i = path_.length() - 1; i >= 0; i--) {
                if (path_.charAt(i) == '\\') {
                    break;
                }
            }
            for (j = 0; j < i; j++) {
                path += path_.charAt(j);
            }
            path_ = path;

        } else {
            if (parser.getArgs()[0].charAt(1) == ':') {
                path_ = parser.getArgs()[0];

            } else {
                path_ += "\\"+ parser.getArgs()[0];

            }
        }
        System.setProperty("user.dir", path_);
    }
    public boolean rm(){
        String file_ = parser.getArgs()[0];
        File file = new File(path_+"\\"+file_);
        file.delete();
        return true;
    }
    public void cp() throws IOException {
        String file1 = parser.getArgs()[0];
        String file2 = parser.getArgs()[1];
        File file = new File(path_,file2);
        File copied = new File(path_,file1);
        Scanner reader = new Scanner(copied);
        String line;
        FileWriter mine = new FileWriter(file);
        while(reader.hasNextLine()){
            line = reader.nextLine();
            mine.write(line);
            mine.write("\n");
        }
        mine.close();
    }
    public void mkdir(){
        String [] dirs = parser.getArgs();
        int i,j;
        for(i=0;i < dirs.length;i++){
            for(j=0;j < dirs[i].length();j++){
                if(dirs[i].charAt(j) == '\\'){
                    break;
                }
            }
            //System.out.println(dirs[i].length());
            //System.out.println(j);
            if(j == dirs[i].length()){
                System.out.println(path_+"\\"+dirs[i]);
                File theDir = new File(path_+"\\"+dirs[i]);
                if (!theDir.exists()){
                    theDir.mkdirs();
                }
            }
            else if(dirs[i].charAt(1) == ':'){
                System.out.println(dirs[i]);
                File theDir = new File(dirs[i]);
                if (!theDir.exists()){
                    theDir.mkdirs();
                }
            }
            else{
                System.out.println(path_+"\\"+dirs[i]);
                File theDir = new File(path_+"\\"+dirs[i]);
                if (!theDir.exists()){
                    theDir.mkdirs();
                }
            }

        }
    }
    public void rmdir(){
        String parameter = parser.getArgs()[0];
        if(parameter.equals("*")){
            File dir = new File(path_);
            String [] dirs = dir.list();
            int i;
            for(i=0;i < dirs.length;i++){
                File directory = new File(dirs[i]);
                if(directory.isDirectory()){
                    if(directory.length() == 0){
                        directory.delete();
                    }
                }
            }
        }
        else if(parameter.charAt(1) == ':'){
            File dir = new File(parameter);
            String [] dirs = dir.list();
            int i;
            for(i=0;i < dirs.length;i++){
                File directory_ = new File(parameter,dirs[i]);
                if(directory_.isDirectory()){
                    if(directory_.length() == 0){
                        directory_.delete();
                    }
                }
            }
        }

        else{
            File dir = new File(path_+"\\"+parameter);
            String [] dirs = dir.list();
            int i;
            for(i=0;i < dirs.length;i++){
                File directory_ = new File(parameter,dirs[i]);
                if(directory_.isDirectory()){
                    if(directory_.length() == 0){
                        directory_.delete();
                    }
                }
            }
        }
    }
    public void ls() throws IOException{
        File dir = new File (path_);
        String [] contents = dir.list();
        for(int i=0;i < contents.length;i++){
            System.out.println(contents[i]);
        }
    }
    public void ls_r() throws IOException{
        File dir = new File (path_);
        String [] contents = dir.list();
        for(int i = contents.length - 1;i >= 0;i--){
            System.out.println(contents[i]);
        }
    }

    public void chooseCommandAction() throws IOException {
        switch(parser.getCommandName()){
            case "echo":
                echo();
                break;
            case "exit":
            	System.out.println("You left!");
                exit();
                break;
            case "pwd":
                pwd();
                break;
            case "ls":
                ls();
                break;
            case "ls-r":
                ls_r();
                break;
            case "cd":
                cd();
                break;
            case "cat":
                cat();
                break;
            case "touch":
                touch();
                break;
            case "rm":
                rm();
                break;
            case "cp":
                cp();
                break;
            case "rmdir":
                rmdir();
                break;
            case "mkdir":
                mkdir();
                break;
        }
    }

    public static void main(String [] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        Terminal obj = new Terminal();
        while (true){
            System.out.print("$");
            String cmd = scan.nextLine();
            Parser object = new Parser();
            object.parse(cmd);
            if(object.parse(cmd)){
                obj.setParser(object);
                obj.chooseCommandAction();
                System.out.println("-------------------------");
                System.out.println("YES!");
            }
        }

//        for(int k = 0; k < obj.parser.getArgs().length;k++){
//            System.out.println(obj.parser.getArgs()[k]);
//        }



    }
}
