package org.wyona.commons.io;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 *
 */
public class PathUtil {

    private static Logger log = LogManager.getLogger(PathUtil.class);

    /**
     * Generate filename from title
     * @param title Title, e.g. 'Hello World!'
     * @return filename, e.g. 'hello_world'
     */
    public static String getFilenameFromTitle(String title) {
        return title.toLowerCase().replace(" ", "_"); // TODO: Remove all special characters
    }

    /**
     * Get name, e.g. path = /foo/bar.txt --> bar.txt
     */
    public static String getName(String path) {
        // Quick and dirty
        return new java.io.File(path).getName();
    }

    /**
     * Get name without suffix, e.g. path = /foo/bar.txt --> bar
     */
    public static String getNameWithoutSuffix(String path) {
        String name = getName(path);
        if (name.lastIndexOf(".") >= 0) {
            return name.substring(0, name.lastIndexOf("."));
        }
        return name;
    }

    /**
     *
     */
    public static String getParent(String path) {
        // TODO: Check if path is null  or empty ("") and return null and log a warning!
        if (path.indexOf("\\") >= 0) {
            Exception e = new Exception("Do not use backward slashes: " + path);
            log.error(e.getMessage(), e);
            return null;
        }
        if (path.equals("/")) {
            if (log.isDebugEnabled()) log.debug("Root / has no parent!");
            return null;
        }
        String parent = path;
        if (path.endsWith("/")) {
            parent = path.substring(0, path.length() - 1);
        }
        return parent.substring(0, parent.lastIndexOf("/") + 1);
    }

    /**
     * Return null if no suffix exists
     */
    public static String getSuffix(String path) {
        int lio = path.lastIndexOf(".");
        log.debug(new Integer(lio));
        if (lio < 0) return null;
        return path.substring(lio + 1);
    }

    /**
     *
     */
    public static String concat(String absolutePath, String relativePath) {
        String newPath = absolutePath;
        if (!new Path(absolutePath).isCollection()) {
            newPath = new Path(newPath).getParent().toString();
        }
        if (relativePath.startsWith("../")) {
            return concat(new Path(newPath).getParent().toString(), relativePath.substring(3));
        }
        return newPath + relativePath;
    }
}
