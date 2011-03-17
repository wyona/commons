package org.wyona.commons.clazz;

import org.apache.log4j.Logger;

/**
 * Class utilities
 */
public class ClazzUtil {

    private static Logger log = Logger.getLogger(ClazzUtil.class);

    /**
     * Check whether class implements a particular interface
     * @param object Class which is supposed to implement a particular interface
     * @param iface (including package, e.g. org.wyona.yarep.core.attributes.LuceneSearchableV1)
     */
    public static boolean implementsInterface(Object object, String iface) {
        boolean implemented = false;
        Class clazz = object.getClass();

        while (!clazz.getName().equals("java.lang.Object") && !implemented) {
            Class[] interfaces = clazz.getInterfaces();
            for (int i = 0; i < interfaces.length; i++) {

                if (interfaces[i].getName().equals(iface)) {
                    implemented = true;
                    break;
                }
                // TODO: Why does this not work?
                //if (interfaces[i].isInstance(iface)) implemented = true;
            }
            clazz = clazz.getSuperclass();
        }

        if (implemented) {
            if (log.isDebugEnabled()) log.debug(object.getClass().getName() + " does implement '" + iface + "' interface!");
        } else {
            if (log.isDebugEnabled()) log.debug(object.getClass().getName() + " does NOT implement '" + iface + "' interface!");
        }

        return implemented;
    }
}
