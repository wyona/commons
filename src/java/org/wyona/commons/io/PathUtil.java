package org.wyona.commons.io;

import org.apache.log4j.Category;

/**
 *
 */
public class PathUtil {

    private static Category log = Category.getInstance(PathUtil.class);

    /**
     *
     */
    public static String getName(String path) {
        // Quick and dirty
        return new java.io.File(path).getName();
    }

    /**
     *
     */
    public static String getParent(String path) {
        // Quick and dirty
        String parent = new java.io.File(path).getParent();
        return parent;
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

}
