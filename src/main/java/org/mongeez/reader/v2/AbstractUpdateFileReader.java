package org.mongeez.reader.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mongeez.commands.v2.ChangeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Abstract implementation for update file reader.
 * 
 * @author Aleksey Pesetski
 */
public abstract class AbstractUpdateFileReader<M extends ObjectMapper> implements UpdateFileReader {

    private static final Logger logger = LoggerFactory.getLogger(AbstractUpdateFileReader.class);
    
    @Override
    public ChangeSet readeUpdateFile(Path updateFile) {
        ChangeSet result = null;

        if (Files.exists(updateFile)) {
            try {
                M objectMapper = getMapper();
                updateFile = updateFile.normalize();
                result = objectMapper.readValue(updateFile.toFile(), ChangeSet.class);
            } catch (IOException e) {
                logger.error("Can't parse update file.", e);
            }
        } else {
            logger.trace("Path to the update file '" + updateFile.toString() + "' doesn't exist.");
        }
        
        return result;
    }
    
    protected abstract M getMapper();
}
