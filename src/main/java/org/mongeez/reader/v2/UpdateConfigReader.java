package org.mongeez.reader.v2;

import org.mongeez.commands.v2.UpdateConfig;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

/**
 * Base interface for define reader.
 * 
 * @author Aleksey Pesetski
 */
public interface UpdateConfigReader<T, S> {

    /**
     * Read updates from the config file.
     * 
     * @param configFile - path to the config file where defined updates
     * 
     * @return
     */
    List<Path> readerChangeFiles(Path configFile, Function<T, S> ... additionalFunction);
}
