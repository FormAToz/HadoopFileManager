import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;

public class RndWordGen {
    private static String rootPath = "hdfs://906467c14ee0:8020";
    private static String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) throws Exception {
        FileSystem hdfs = FileSystem.get(new URI(rootPath), HdfsConfiguration.getConfiguration());
        Path file = new Path("hdfs://906467c14ee0:8020/test/file.txt");

        if (hdfs.exists(file)) {
            hdfs.delete(file, true);
        }

        OutputStream os = hdfs.create(file);
        BufferedWriter br = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8")
        );

        for(int i = 0; i < 10_000_000; i++) {
            br.write(getRandomWord() + " ");
        }

        br.flush();
        br.close();
        hdfs.close();
    }

    private static String getRandomWord() {
        StringBuilder builder = new StringBuilder();
        int length = 2 + (int) Math.round(10 * Math.random());
        int symbolsCount = symbols.length();
        for(int i = 0; i < length; i++) {
            builder.append(symbols.charAt((int) (symbolsCount * Math.random())));
        }
        return builder.toString();
    }
}
