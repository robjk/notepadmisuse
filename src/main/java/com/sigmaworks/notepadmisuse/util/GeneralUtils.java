package com.sigmaworks.notepadmisuse.util;

import com.sigmaworks.notepadmisuse.NotepadMisuse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class GeneralUtils {

    /**
     * Simple classpath resource reader, supports .gz compressed resources
     *
     * @param resourceName name of the resource to read
     * @return the (decompressed if necessary) resource's bytes
     */
    public static byte[] readResource(String resourceName) {
        byte[] content;

        try (InputStream resourceAsStream = NotepadMisuse.class.getResourceAsStream("/" + resourceName);
             InputStream sourceStream = resourceName.endsWith(".gz") ? new GZIPInputStream(Objects.requireNonNull(resourceAsStream)) : resourceAsStream) {
            content = Objects.requireNonNull(sourceStream).readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException("unable to read resource " + resourceName, e);
        }

        return content;
    }

}
