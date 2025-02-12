package FileSystem;

public class FileSystem {
    private FS node;

    public FileSystem(String name) {
        this.node = FSOperations.fileSystem(name);
    }

    private FileSystem(FS node) {
        this.node = node;
    }

    public FileSystem exec(String command, String[] args) {
        FS root = this.node;
        switch (command) {
            case "mkdir" -> {
                if (FSOperations.mkdir(root, args[0])) {
                    System.out.println(args[0] + " dir created successfully");
                }
                break;
            }
            case "touch" -> {
                if (FSOperations.touch(root, args[0])) {
                    System.out.println(args[0] + " file created successfully");
                }
                break;
            }
            case "rmdir" -> {
                if (FSOperations.rmdir(root, args[0])) {
                    System.out.println(args[0] + " dir deleted successfully");
                } else {
                    System.out.println("either there is no dir named " + args[0] + " or it is not empty");
                }
                break;
            }
            case "rm" -> {
                if (args.length == 1) {
                    if (FSOperations.rm(root, args[0])) {
                        System.out.println(args[0] + " file deleted successfully");
                    }
                } else if (args.length == 2) {
                    if (FSOperations.rm(root, args[1],args[0])) {
                        System.out.println(args[1] + " dir deleted successfully");
                    }
                }
                break;
            }
            case "cd" -> {
                FileSystem cd = new FileSystem(FSOperations.cd(root, args[0]));
                if (cd != null) {
                    System.out.println("changed dir to " + cd.node.name);
                    return cd;
                }
            }
            default -> {
                return this;
            }
        }

        return this;
    }

    public String cmd(String command, String[] args) {
        FS root = this.node;

        switch (command) {
            case "ls" -> {
                if (args.length == 0) {
                    return FSOperations.ls(root);
                } else if (args.length == 1) {
                    return FSOperations.ls(root, args[0]);
                }
                break;
            }
            case "pwd" -> {
                return FSOperations.pwd(root);
            }
            default -> {
                return "null";
            }
        }

        return "null";
    }

    public String currentDirectory() {
        return node.name;
    }
    
}
