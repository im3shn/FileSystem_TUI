package FileSystem;
import java.util.HashMap;
import java.util.Map;

class FS {

    protected FS root;
    protected String name;
    protected Map<String, FS> subNodes = new HashMap<>();
    protected Boolean isDir = false;

    protected FS(String name) {
        this.name = name;
        this.isDir = true;
        this.root = null;
    }

    protected FS(String name, FS root) {
        this.name = name;
        this.root = root;
    }

    @Override
    public String toString() {
        return "FS [root=" + root + ", name=" + name + ", subNodes=" + subNodes + ", isDir=" + isDir + "]";
    }

    protected FS(String name, FS root, Boolean isDir) {
        this.name = name;
        this.isDir = isDir;
        this.root = root;
    }

}