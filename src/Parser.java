import java.io.*;
import java.util.*;
import java.io.File;
import java.io.IOException;

public class Parser {
    String commandName;
    String [] args;
    String[] commands = {"echo", "pwd", "ls", "ls-r","cd","mkdir","cat","exit","touch","rm","cp","cp-r","mkdir","rmdir"};
    public boolean parse(String input){
        commandName = input.split(" ")[0];
        int i;
        for (i = 0;i < commands.length;i++){
            if(commands[i].equals(commandName)){
                String [] splits = input.split(" ");
                commandName = splits[0];
                args = new String[splits.length-1];
                for(int j = 1;j < splits.length;j++){
                    args[j-1] = splits[j];
                }
                break;
            }
        }
        try {
            if (i == commands.length) {
                throw new IOException();
                //return true;
            }
        }
        catch (IOException e){
            System.out.println("invalid input");
            return false;
        }
        return true;
    }

    public String getCommandName(){
        return commandName;
    }

    public String[] getArgs(){
        return args;
    }

}

