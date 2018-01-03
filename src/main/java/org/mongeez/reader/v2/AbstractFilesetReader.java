package org.mongeez.reader.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.mongeez.commands.v2.ChangeFile;
import org.mongeez.commands.v2.ChangeFileSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
public abstract class AbstractFilesetReader<M extends ObjectMapper> implements FilesetReader {

    private static final Logger logger = LoggerFactory.getLogger(AbstractFilesetReader.class);
    
    @Override
    public List<Path> readerChangeFiles(Path configFile) {
        List<Path> result = new ArrayList<>();

        if (Files.exists(configFile)) {
            try {
                M objectMapper = getMapper();
                configFile = configFile.normalize();
                String configContent = StringUtils.toEncodedString(Files.readAllBytes(configFile), StandardCharsets.UTF_8);
                ChangeFileSet changeFileSet = objectMapper.readValue(configContent, ChangeFileSet.class);

                //Resource resourceConfigFile = new PathResource(configFile);
                Path parentFolder = configFile.getParent();
                for (ChangeFile changeFile: changeFileSet.getChangeFiles()) {
                    String path = changeFile.getPath();
                    Path pathToChangeFile = Paths.get(parentFolder.toString(), path);
                    if (Files.exists(pathToChangeFile)) {
                        result.add(pathToChangeFile);
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
