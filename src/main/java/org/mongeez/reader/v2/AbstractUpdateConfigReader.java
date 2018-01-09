package org.mongeez.reader.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mongeez.commands.v2.UpdateConfig;
import org.mongeez.commands.v2.UpdateFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation for read config file.
 * 
 * @author Aleksey Pesetski
 */
public abstract class AbstractUpdateConfigReader<M extends ObjectMapper> implements UpdateConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(AbstractUpdateConfigReader.class);
    
    @Override
    public List<Path> readerChangeFiles(Path configFile) {
        List<Path> result = new ArrayList<>();

        if (Files.exists(configFile)) {
            try {
                M objectMapper = getMapper();
                configFile = configFile.normalize();
                UpdateConfig changeFileSet = objectMapper.readValue(configFile.toFile(), UpdateConfig.class);

                Path parentFolder = configFile.getParent();
                for (UpdateFile updateFile: changeFileSet.getUpdateFiles()) {
                    String path = updateFile.getPath();
                    Path pathToUpdateFile = Paths.get(parentFolder.toString(), path);
                    if (Files.exists(pathToUpdateFile)) {
                        result.add(pathToUpdateFile);
                    } else {
                        logger.trace("Path to the update file '" + path + "' doesn't exist. Updates from this file will be skipped.");
                    }
                }
            } catch (IOException e) {
                logger.error("Can't parse config file.", e);
            }
        } else {
            logger.trace("Path to the config file '" + configFile.toString() + "' doesn't exist.");
        }

        return result;
    }
    
    protected abstract M getMapper();
}
