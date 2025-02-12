import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import FileSystem.FileSystem;

public class Main {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        try {
        
            System.out.println("WELCOME TO COMMAND LINE FILESYSTEM");
            System.out.println("Enter you username:");
            String username = sc.nextLine();
            FileSystem fs = new FileSystem(username);
            fs.exec(username, args);
            System.out.println(fs);

            Set<String> exec = new HashSet<>();
            exec.addAll(Arrays.asList(new String[]{"mkdir", "touch", "rmdir", "rm", "cd"}));

            Set<String> cmd = new HashSet<>();
            cmd.addAll(Arrays.asList(new String[]{"ls", "pwd"}));

            FileSystem iteratorFileSystem = fs;
            String currentDirectory = fs.currentDirectory();
            String input[];
            String output;
            String command;
            String[] params;
            while (true) {
                System.out.print("->  " + currentDirectory + "  ");

                input = sc.nextLine().split(" ");
                command = input[0];
                params = Arrays.copyOfRange(input, 1, input.length);

                if (command == null) {
                    break;
                } else if (exec.contains(command)) {
                
                    iteratorFileSystem = iteratorFileSystem.exec(command, params);
                    currentDirectory = iteratorFileSystem.currentDirectory();
                
                } else if (cmd.contains(command)) {

                    output = iteratorFileSystem.cmd(command, params);
                    System.out.println(output);

                } else if (command.equalsIgnoreCase("exit")) {
                    break;
                } else {

                    System.out.println("Command does not exist");
                    continue;

                }
            }

        } catch (Exception e){
            System.err.println(e);
        } finally {
            sc.close();
        }
        
    }
}
