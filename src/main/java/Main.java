public class Main {
    private static String rootPath = "hdfs://906467c14ee0:8020";

    public static void main(String[] args) throws Exception
    {
        FileAccess fileAccess = new FileAccess(rootPath);

        // creates a file
        fileAccess.create("/test/file1.txt");
        fileAccess.create("/dir/file1");
        fileAccess.create("/test/file1.txt");
        fileAccess.create("/test/test/file1.txt");
        fileAccess.create("/test/test/test/file1.txt");
        fileAccess.create("/test/test/test/test/file1.txt");

        // appends text to existing file (or create a new one)
//        fileAccess.append("/test/file11.txt", "\n9999");

        // prints a data from existing file
//        System.out.println(fileAccess.read("/test/file11.txt"));

        // deletes file or directory
//        fileAccess.delete("/dir/");

        // checks, is the "path" is directory or file
//        System.out.println(fileAccess.isDirectory("/test/"));

        // prints the files and subdirectories on any directory
        fileAccess.list("/").forEach(System.out::println);
    }
}
