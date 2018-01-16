package org.mongeez.reader.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mongeez.commands.v2.ChangeSet;
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
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Abstract implementation for read config file.
 * 
 * @author Aleksey Pesetski
 */
public abstract class AbstractUpdateConfigReader<M extends ObjectMapper> implements UpdateConfigReader {
    
    private static final Logger logger = LoggerFactory.getLogger(AbstractUpdateConfigReader.class);
    
    @Override
    public List<ChangeSet> readerChangeFiles(Path configFile) {
        List<ChangeSet> result = null;

        if (Files.exists(configFile)) {
            try {
                Class<M> objectMapperCls = getMapper();
                ObjectMapper objectMapper = objectMapperCls.newInstance();
                configFile = configFile.normalize();
                UpdateConfig updateConfig = objectMapper.readValue(configFile.toFile(), UpdateConfig.class);

                Path parentFolder = configFile.getParent();
                result = updateConfig.getUpdateFiles().stream()
                        .map(readChangeSet(objectMapper, parentFolder))
                        .filter(changeSet -> filterChangeSet(changeSet))
                        .collect(Collectors.toList());
            } catch (IOException e) {
                logger.error("Can't parse config file.", e);
            } catch (IllegalAccessException e) {
                logger.error("Can't mapper for parse file.", e);
            } catch (InstantiationException e) {
                logger.error("Can't create object mapper for read config file.", e);
            }
        } else {
            logger.trace("Path to the config file '" + configFile.toString() + "' doesn't exist.");
        }

        return result == null ? new ArrayList<>() : result;
    }
    
    protected boolean filterChangeSet(final ChangeSet changeSet) {
        return Objects.nonNull(changeSet);
    }

    protected Function<UpdateFile, ChangeSet> readChangeSet(final ObjectMapper objectMapper, final Path parentFolder) {
        return updateFile -> {
            Path updateFilePath = 
                    Paths.get(parentFolder.toString(), updateFile.getPath());
            ChangeSet changeSet = null;
            if (Files.exists(updateFilePath)) {
                try {
                    changeSet = objectMapper.readValue(updateFilePath.toFile(), ChangeSet.class);
                } catch (IOException e) {
                    logger.error("Can't read change set file by path = '" + updateFilePath.toString() + "' .", e);
                }
            } else {
                logger.trace("Update file doesn't exist by path = '" + updateFilePath.toString() + "' .");
            }
            
            return changeSet; 
        };
    }

    protected abstract Class<M> getMapper();
}
