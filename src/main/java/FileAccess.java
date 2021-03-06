import org.apache.hadoop.fs.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class FileAccess {
    private FileSystem hdfs;
    private FSDataOutputStream os;

    /**
     * Initializes the class, using rootPath as "/" directory
     *
     * @param rootPath - the path to the root of HDFS,
     * for example, hdfs://localhost:32771
     */
    public FileAccess(String rootPath) {
        try {
            hdfs = FileSystem.get(new URI(rootPath), HdfsConfiguration.getConfiguration());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates empty file or directory
     *
     * @param path
     */
    public void create(String path) throws IOException {
       os = hdfs.create(new Path(path), (short) 1);
       os.close();
    }

    /**
     * Appends content to the file or creates a new one with content
     *
     * @param path
     * @param content
     */
    public void append(String path, String content) throws IOException {
        Path file = new Path(path);
        if (!hdfs.exists(file)) {
            create(path);
        }
        os = hdfs.append(file);
        os.writeBytes(content);
        os.close();
    }

    /**
     * Returns content of the file
     *
     * @param path
     * @return
     */
    public String read(String path) throws IOException {
        StringBuilder sb = new StringBuilder("");
        BufferedReader br = new BufferedReader(new InputStreamReader(hdfs.open(new Path(path))));
        String str;
        while ((str = br.readLine()) != null) {
            sb.append(str).append("\n");
        }
        return sb.toString();
    }

    /**
     * Deletes file or directory
     *
     * @param path
     */
    public void delete(String path) throws IOException {
        Path file = new Path(path);
        if (hdfs.exists(file)) {
            hdfs.delete(file, true);
        }
    }

    /**
     * Checks, is the "path" is directory or file
     *
     * @param path
     * @return
     */
    public boolean isDirectory(String path) throws IOException {
        if (hdfs.isDirectory(new Path(path))) return true;
        return false;
    }

    /**
     * Return the list of files and subdirectories on any directory
     *
     * @param path
     * @return
     */
    public List<String> list(String path) throws IOException {
        List<String> fileList = new ArrayList<>();
        FileStatus[] fileStatuses = hdfs.listStatus(new Path(path));
        for (FileStatus fs : fileStatuses) {
            if (fs.isDirectory()) {
                fileList.addAll(list(fs.getPath().toString()));
            }else {
                fileList.add(fs.getPath().toString());
            }
        }
        return fileList;
    }
}
