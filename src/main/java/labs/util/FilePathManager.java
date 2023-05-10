package labs.util;

import java.nio.file.Paths;

public class FilePathManager {
    public static String getFilePath(String fileName, String fileDir) {
        return getProjectPath() + "\\src\\main\\java\\org\\labs\\" + fileDir + "\\" + fileName;
    }

    public static String getProjectPath() {
        return Paths.get("").toAbsolutePath().toString();
    }
}
