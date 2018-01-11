package org.mongeez.reader.v2.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.mongeez.reader.v2.AbstractUpdateFileReader;

/**
 * Update file reader for XML config file.
 *
 * @author Aleksey Pesetski
 */
public class XmlUpdateFileReader extends AbstractUpdateFileReader<XmlMapper> {

    @Override
    protected XmlMapper getMapper() {
        return new XmlMapper();
    }
}
