package org.mongeez.reader.v2.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.mongeez.commands.v2.UpdateConfig;
import org.mongeez.reader.v2.AbstractUpdateConfigReader;

import java.io.Serializable;

/**
 * Update config reader for XML config file.
 *
 * @author Aleksey Pesetski
 */
public class XmlUpdateConfigReader extends AbstractUpdateConfigReader<XmlMapper, UpdateConfig, ?> {

    @Override
    protected Class<XmlMapper> getMapper() {
        return XmlMapper.class;
    }

    @Override
    protected Class<UpdateConfig> getJavaBeanClass() {
        return UpdateConfig.class;
    }
}
