package mostwanted.util;

import java.io.*;

public class FileUtilImpl implements FileUtil {

    @Override
    public String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();

        File file = new File(filePath);
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        String readLine = bf.readLine();
        while (readLine != null) {
            sb.append(readLine);
            readLine = bf.readLine();
        }

        return sb.toString();
    }
}