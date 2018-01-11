package org.mongeez.reader.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Abstract implementation for read config file.
 * 
 * @author Aleksey Pesetski
 */
public abstract class AbstractUpdateConfigReader<M extends ObjectMapper, T, S> implements UpdateConfigReader<T, S> {
    
    private static final Logger logger = LoggerFactory.getLogger(AbstractUpdateConfigReader.class);
    
    @Override
    public List<Path> readerChangeFiles(Path configFile, Function<T, S> ... additionalFunctions) {
        List<Path> result = new ArrayList<>();

        if (Files.exists(configFile)) {
            try {
                Class<M> objectMapperCls = getMapper();
                ObjectMapper objectMapper = objectMapperCls.newInstance();
                configFile = configFile.normalize();
                T javaBean = objectMapper.readValue(configFile.toFile(), getJavaBeanClass());
                
                if (additionalFunctions != null) {
                    Stream<Function<T, S>> stream = Arrays.stream(additionalFunctions);
                    stream.forEach(function -> function.apply(javaBean));
                }
                
                /*Path parentFolder = configFile.getParent();
                for (UpdateFile updateFile: changeFileSet.getUpdateFiles()) {
                    String path = updateFile.getPath();
                    Path pathToUpdateFile = Paths.get(parentFolder.toString(), path);
                    if (Files.exists(pathToUpdateFile)) {
                        result.add(pathToUpdateFile);
                    } else {
                        logger.trace("Path to the update file '" + path + "' doesn't exist. Updates from this file will be skipped.");
                    }
                }*/
            } catch (IOException e) {
                logger.error("Can't parse config file.", e);
            } catch (IllegalAccessException e) {
                logger.error("Can't mapper for parse file.", e);
                
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } else {
            logger.trace("Path to the config file '" + configFile.toString() + "' doesn't exist.");
        }

        return result;
    }
    
    protected abstract Class<M> getMapper();
    protected abstract Class<T> getJavaBeanClass(); 
}
