package FileSystem;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class FSOperations{
    
    protected static FS fileSystem(String name) {
        FS home = new FS(name);
        return home;
    }

    protected static Boolean mkdir(FS root, String name) {
        FS dir = new FS(name, root, true);
        dir.root.subNodes.put(name, dir);
        return true;
    }

    protected static Boolean touch(FS root, String name) {
        FS file = new FS(name, root);
        file.root.subNodes.put(name, file);
        return true;
    }

    protected static Boolean rmdir(FS node) {
        if (!node.isDir) {
            return false;
        }

        if (!node.subNodes.isEmpty()) {
            return false;
        }

        node.root.subNodes.remove(node.name);
        return true;
    }

    protected static Boolean rmdir(FS node, String dirName) {
        if (!node.subNodes.containsKey(dirName)) {
            return false;
        }

        node = node.subNodes.get(dirName);
        if (!node.isDir) {
            return false;
        }

        if (!node.subNodes.isEmpty()) {
            return false;
        }

        node.root.subNodes.remove(node.name);
        return true;
    }

    protected static Boolean rm(FS node) {
        if (node.isDir) {
            return false;
        }

        node.root.subNodes.remove(node.name);
        return true;
    }

    protected static Boolean rm(FS node, String filename) {
        node = node.subNodes.get(filename);
        if (node.isDir) {
            return false;
        }

        node.root.subNodes.remove(node.name);
        return true;
    }

    protected static Boolean rm(FS node, String dirName ,String args) {
        node = node.subNodes.get(dirName);
        System.out.println(node.toString());

        if (node.isDir && !args.equals("-r")) {
            return false;
        }
        
        if (!node.isDir) {
            return rm(node);
        }

        for (FS branch: node.subNodes.values()) {
            rm(branch, branch.name,"-r");
        }
        node.root.subNodes.remove(node.name);

        return true;
    }

    protected static FS cd(FS node, String toBeChangedTo) {
        if (!node.isDir) {
            System.out.println("not a dir");
            return node;
        }
        if (toBeChangedTo.equals("..")) {
            System.out.println("equals ..");
            if (node.root == null) {
                System.out.println("root is null");
                return node;
            }
            System.out.println("root is " + node.root.name);
            return node.root;
        }
        if (!node.subNodes.containsKey(toBeChangedTo)) {
            System.out.println("doesnot contains the given name");
            return node;
        }

        System.out.println("contaings given name");
        return node.subNodes.get(toBeChangedTo);
    }

    protected static String ls(FS node) {
        StringBuffer sb = new StringBuffer();
        for (FS branch : node.subNodes.values()) {
            sb.append(branch.name);
            if (branch.isDir) {
                sb.append("/");
            }
            sb.append("\t");
        }

        return sb.toString();
    }

    protected static String ls(FS node, String args) {
        if (!args.equals("--tree")) {
            return "--tree is the only flag supported now";
        }

        StringBuffer sb = new StringBuffer();
        Queue<FS> q = new LinkedList<>();
        q.offer(node);
        StringBuffer path = new StringBuffer("./");

        while (!q.isEmpty()) {
            FS temp = q.poll();
            path = cwd(node,temp);
            sb.append(path + " \n");
            for (FS branch : temp.subNodes.values()) {
                if (branch.isDir) {
                    q.offer(branch);
                } else {
                    sb.append(branch.name + "\t");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    protected static String pwd(FS node) {
        StringBuffer sb = new StringBuffer();
        Stack<FS> st = new Stack<>();

        while (node != null) {
            st.push(node);
            node = node.root;
        }

        while (!st.isEmpty()) {
            sb.append("/" + st.pop().name);
        }


        return sb.toString();
    }

    protected static StringBuffer cwd(FS root, FS node) {
        StringBuffer sb = new StringBuffer();
        Stack<FS> st = new Stack<>();

        while (node != root.root) {
            st.push(node);
            node = node.root;
        }

        while (!st.isEmpty()) {
            sb.append("/" + st.pop().name);
        }


        return sb;
    }

}
