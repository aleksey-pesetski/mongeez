package org.mongeez.reader.v2.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.mongeez.reader.v2.AbstractUpdateConfigReader;

/**
 * Update config reader for XML config file.
 *
 * @author Aleksey Pesetski
 */
public class XmlUpdateConfigReader extends AbstractUpdateConfigReader<XmlMapper> {

    @Override
    protected Class<XmlMapper> getMapper() {
        return XmlMapper.class;
    }
}
