
import java.io.*;

public class FIO {

    public boolean fWrite(String fName, String in) {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fName, true)));
            out.println(in);
            out.close();
            return true;
        } catch (IOException e) {
            System.out.println("File write error");
            return false;
        }
    }
}
