package org.mongeez.reader.v2;

import org.mongeez.commands.v2.ChangeSet;

import java.nio.file.Path;

/**
 *  Base interface for define reader for {@link org.mongeez.commands.v2.UpdateFile}
 * 
 * @author Aleksey Pesetski
 */
public interface UpdateFileReader {

    /**
     * Read update file and create {@link ChangeSet} object.
     * 
     * @param updateFile - the path to the update file.
     * 
     * @return - read update file.
     */
    ChangeSet readeUpdateFile(Path updateFile);
    
}
