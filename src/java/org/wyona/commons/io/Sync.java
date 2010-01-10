package org.wyona.commons.io;

import java.io.File;

import org.apache.log4j.Logger;

/**
 *
 */
public class Sync {

    private static Logger log = Logger.getLogger(Sync.class);

    /**
     *
     */
    public void synchronize(File source, File destination) {
        if (!source.isDirectory()) {
            log.error("No such source directory: " + source.getAbsolutePath());
            return;
        }
        if (!destination.isDirectory()) {
            log.error("No such destination directory: " + destination.getAbsolutePath());
            return;
        }
        if (destination.getAbsolutePath().equals(source.getAbsolutePath())) {
            log.error("Source and destination directory are the same and hence synchronization aborted!");
            return;
        }

        log.error("Synchronizing ...");
        doSynchronize(source, destination);
        log.error("Synchronization finished.");
    }

    /**
     *
     */
    private void doSynchronize(File source, File destination) {
        String[] filesAndDirs = source.list();
        for (int i = 0; i < filesAndDirs.length; i++) {
            File fd = new File(source, filesAndDirs[i]);
            if (fd.isFile()) {
                //log.warn("DEBUG: File: " + fd.getName());
                if (!new File(destination, fd.getName()).isFile()) {
                    log.warn("No such file at destination: " + new File(destination, fd.getName()));
                }
            } else if (fd.isDirectory()) {
                //log.warn("DEBUG: Directory: " + fd.getAbsolutePath());
                if (!new File(destination, fd.getName()).isDirectory()) {
                    log.warn("No such directory at destination: " + new File(destination, fd.getName()));
                } else {
                    doSynchronize(new File(source, fd.getName()), new File(destination, fd.getName()));
                }
            } else {
                log.warn("Neither file nor directory: " + fd.getAbsolutePath());
            }
        }
    }
}
