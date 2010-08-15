package org.wyona.commons.io;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * Utility to synchronize two directories/volumes. Also see http://ant.apache.org/manual/CoreTasks/sync.html
 * INFO: At the moment it only displays the differences, but does not copy anything!
 */
public class Sync {

    private static Logger log = Logger.getLogger(Sync.class);

    /**
     * Synchronize two directories/volumes recursively
     * @param source Source directory
     * @param destination Destination directory
     * @param excludes Comma separated list of directories and files which should be excluded from synchronization
     * @param ignoreHidden Ignore hidden files from synchronization
     */
    public void synchronize(File source, File destination, String excludes, boolean ignoreHidden) {
        if (!source.isDirectory()) {
            log.error("No such source directory: " + source.getAbsolutePath());
            return;
        }
        if (!destination.isDirectory()) {
            log.error("No such destination directory: " + destination.getAbsolutePath());
            return;
        }
        if (destination.compareTo(source) == 0) {
            log.error("Source and destination directory are the same '" + source.getAbsolutePath() + "' and hence synchronization aborted!");
            return;
        }

        String[] excludedFilesAndDirs = new String[0];
        if (excludes != null) {
            excludedFilesAndDirs = excludes.split(",");
        }

        log.warn("INFO: Synchronizing (Source: '" + source + "', Destination: '" + destination + "') ...");
        doSynchronize(source, destination, excludedFilesAndDirs, ignoreHidden);
        log.warn("INFO: Synchronization finished.");
    }

    /**
     *
     */
    private void doSynchronize(File source, File destination, String[] excludes, boolean ignoreHidden) {
        if (!source.canRead()) {
            log.warn("Permission denied: " + source);
            return;
        }
        if (ignoreHidden && source.isHidden()) {
            log.warn("Hidden file or directory: " + source);
            return;
        }
        String[] filesAndDirs = source.list();
        if (filesAndDirs != null) { // TODO: filesAndDirs can be null for example if permission denied
            for (int i = 0; i < filesAndDirs.length; i++) {
                File fd = new File(source, filesAndDirs[i]);

                boolean excluded = false;
                for (int k = 0; k < excludes.length; k++) {
                    if (excludes[k].equals(fd.getName())) {
                        log.info("Excluded: " + fd.getName());
                        excluded = true;
                        break;
                    }
                }
                if(excluded) {
                    continue;
                }

                if (fd.isFile()) {
                    //log.warn("DEBUG: File: " + fd.getName());
                    if (!new File(destination, fd.getName()).isFile()) {
                        log.warn("No such file at destination: " + new File(destination, fd.getName()));
                    } else {
                        if (fd.length() != new File(destination, fd.getName()).length()) {
                            log.warn("Source '" + fd.getAbsolutePath() + "' and destination '" + new File(destination, fd.getName()).getAbsolutePath() + "' have not the same size!");
                        }
                    }
                } else if (fd.isDirectory()) {
                    //log.warn("DEBUG: Directory: " + fd.getAbsolutePath());
                    if (!new File(destination, fd.getName()).isDirectory()) {
                        log.warn("No such directory at destination: " + new File(destination, fd.getName()));
                    } else {
                        doSynchronize(new File(source, fd.getName()), new File(destination, fd.getName()), excludes, ignoreHidden);
                    }
                } else {
                    log.warn("Neither file nor directory: " + fd.getAbsolutePath());
                }
            }
        } else {
            log.warn("No children found within: " + source);
        }
    }
}
