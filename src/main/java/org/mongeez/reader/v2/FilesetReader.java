package org.mongeez.reader.v2;

import org.springframework.core.io.Resource;

import java.nio.file.Path;
import java.util.List;

/**
 * Base interface for define reader.
 * 
 * @author Aleksey Pesetski
 */
public interface FilesetReader {

    /**
     * Read updates from the config file.
     * 
     * @param configFile - path to the config file where defined updates
     * 
     * @return
     */
    List<Resource> readerFiles(Path configFile); 
}
