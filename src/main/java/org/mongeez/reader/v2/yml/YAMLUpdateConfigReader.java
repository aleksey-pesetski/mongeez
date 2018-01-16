package org.mongeez.reader.v2.yml;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.mongeez.reader.v2.AbstractUpdateConfigReader;

/**
 * Update config reader for YAML config file.
 *
 * @author Aleksey Pesetski
 */
public class YAMLUpdateConfigReader extends AbstractUpdateConfigReader<YAMLMapper> {

    @Override
    protected Class<YAMLMapper> getMapper() {
        return YAMLMapper.class;
    }
}
