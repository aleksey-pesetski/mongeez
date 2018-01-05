package org.mongeez.reader.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.mongeez.commands.v2.UpdateConfig;
import org.mongeez.reader.v2.exception.ConfigFileReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public List<Resource> readerFiles(Path configFile) {
        List<Resource> result = new ArrayList<>();

        UpdateConfig updateConfig = readUpdateConfig(configFile);
        readUpdateFile(updateConfig);
        return result;
    }

    private List<Resource> readUpdateFile(UpdateConfig updateConfig) {
        return updateConfig.getChangeFiles().stream().map(updateFile -> updateFile.getPath());
    }

    private UpdateConfig readUpdateConfig(Path configFile) throws ConfigFileReadException {
        UpdateConfig updateConfig = null;
        try {
            M objectMapper = getMapper();
            String configContent = StringUtils.toEncodedString(Files.readAllBytes(configFile), StandardCharsets.UTF_8);
            updateConfig = objectMapper.readValue(configContent, UpdateConfig.class);
        } catch (IOException e) {
            logger.error("Can't read config file.", e);
            
            throw new ConfigFileReadException(e);
        }
        
        return updateConfig;
    }

    protected abstract <T> T getMapper();
}
