package org.mongeez.reader.v2;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Fileset reader for XML config file.
 *
 * @author Aleksey Pesetski
 */
public class XmlFilesetReader extends AbstractFilesetReader<XmlMapper> {

    @Override
    protected XmlMapper getMapper() {
        return new XmlMapper();
    }
}
