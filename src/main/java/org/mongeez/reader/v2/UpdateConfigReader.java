package org.mongeez.reader.v2;

import org.mongeez.commands.v2.ChangeSet;

import java.nio.file.Path;
import java.util.List;

/**
 * Base interface for define reader.
 * 
 * @author Aleksey Pesetski
 */
public interface UpdateConfigReader {

    /**
     * Read updates from the config file.
     * 
     * @param configFile - path to the config file where defined updates
     * 
     * @return
     */
    List<ChangeSet> readerChangeFiles(Path configFile);
}
