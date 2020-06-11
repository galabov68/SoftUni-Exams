package app.ccb.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtilImpl implements FileUtil {

    @Override
    public String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));

        String line = bf.readLine();

        while (line != null) {
            sb.append(line);
            line = bf.readLine();
        }

        return sb.toString();
    }
}
