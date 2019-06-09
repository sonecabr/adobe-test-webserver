package com.adobe.test.webserver.io;

import com.adobe.test.webserver.io.exception.FileNotFoundUnreadableException;
import com.adobe.test.webserver.server.WebServerConfigs;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * No blocking file reader
 * <p>
 *     Read files without read lock
 * </p>
 * @author Andre Rocha
 * @since 2019-06-08
 */
@Slf4j
public class NonBlockingFileReader implements FileReader {

    private static final NonBlockingFileReader INSTANCE = new NonBlockingFileReader();

    public static NonBlockingFileReader getInstance() {
        return NonBlockingFileReader.INSTANCE;
    }

    @Override
    public WebContentFile readContent(String path, BufferedOutputStream outputStream) throws FileNotFoundUnreadableException {
        String canonicalPath = String.format("%s%s", WebServerConfigs.WEB_ROOT, path);
        if (!Files.isReadable(Paths.get(canonicalPath))) {
            throw new FileNotFoundUnreadableException(String.format("File %s is not acessible", path));
        }
        return consume(canonicalPath, outputStream);
    }

    @Override
    public WebContentFile readContent(String webroot, String path, BufferedOutputStream outputStream) throws FileNotFoundUnreadableException {
        String canonicalPath = String.format("%s%s", webroot, path);
        if (!Files.isReadable(Paths.get(canonicalPath))) {
            throw new FileNotFoundUnreadableException(String.format("File %s is not acessible", path));
        }
        return consume(canonicalPath, outputStream);

    }

    /**
     * Consume file content and write the content to outputstream (should not block the access to file)
     * @param canonicalPath
     * @param outputStream
     * @return WebContentFile - with file content
     * @throws FileNotFoundUnreadableException
     */
    private WebContentFile consume(String canonicalPath, BufferedOutputStream outputStream) {
        try (InputStream is = Files.newInputStream(Paths.get(canonicalPath), StandardOpenOption.READ)) {


            int lenght = 0;
            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = is.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                lenght += bytesRead;
            }

            return WebContentFile
                    .builder()
                    .content(outputStream)
                    .lenght(lenght)
                    .build();
        } catch (IOException e) {
            log.error(String.format("Error reading file %s", canonicalPath));
        } finally {}

        return WebContentFile.builder().build();
    }
}
