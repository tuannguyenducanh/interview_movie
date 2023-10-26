package integration.com.bank.interview.movie.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {

    public static String readJsonFile(String path) throws IOException {
        return Files.readString(new ClassPathResource(path).getFile().toPath());
    }
}
