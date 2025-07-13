import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class DirectoryList {

    public static void listToFile(String directionPath, String fileName) {
        File dir = new File(System.getProperty("user.dir") + File.separator + directionPath);
        File outFile = new File(System.getProperty("user.dir") + File.separator + fileName);

        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Invalid directory: " + directionPath);
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(outFile))) {
            listHelper(dir, "", writer);
            System.out.println("Saved to: " + fileName);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listHelper(File dir, String indent, PrintWriter writer) {
        File[] files = dir.listFiles();
        if (files == null) return;

        Arrays.sort(files, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        for (File f : files) {
            String type = f.isDirectory() ? "[D]" : "[F]";
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(f.lastModified()));
            writer.println(indent + type + " " + f.getName() + " - " + date);
            if (f.isDirectory()) {
                listHelper(f, indent + "  ", writer);
            }
        }
    }

    public static void readTxt(String filePath) {
        File file = new File(System.getProperty("user.dir") + File.separator + filePath);

        if (!file.exists() || !file.isFile() || !file.getName().endsWith(".txt")) {
            System.out.println("Invalid TXT file: " + filePath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) System.out.println(line);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Use:");
            System.out.println("  list <dir> <output.txt>");
            System.out.println("  read <file.txt>");
            return;
        }

        if (args[0].equals("list") && args.length == 3) {
            listToFile(args[1], args[2]);
        } else if (args[0].equals("read")) {
            readTxt(args[1]);
        } else {
            System.out.println("Invalid command.");
        }
    }
}



