package org.wyona.commons.xml;

import org.apache.log4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Utilities for XHTML manipulation
 */
public class XHTMLUtil {

    private static Logger log = Logger.getLogger(XHTMLUtil.class);

    /**
     * Set the page title (&lt;title&gt;) AND the main header (&lt;h1&gt;) in an XHTML document with another title
     * @param doc Document containing XHTML
     * @param title Title to be set
     */
    public static void setTitle(Document doc, String title) {
        Element rootElement = doc.getDocumentElement();
        if (log.isDebugEnabled()) log.debug("Root element: " + rootElement.getTagName());
        if (log.isDebugEnabled()) log.debug("Title/Header to be set: " + title);

        String[] nodes = {"title", "h1"};
        for (String node : nodes) {
            NodeList elements = rootElement.getElementsByTagName(node);
            for (int i = 0; i < elements.getLength(); i++) {
                //elements.item(i).getFirstChild();
                String elementName = ((Element)elements.item(i)).getFirstChild().getNodeName();
                if (log.isDebugEnabled()) log.debug("Current Node: " + ((Element)elements.item(i)).getTagName() + "/" + elementName);
                if (log.isDebugEnabled()) log.debug("Current (old) Value: " + ((Element)elements.item(i)).getFirstChild().getNodeValue());
                if (log.isDebugEnabled()) log.debug("Setting Title/Header");
                if (elementName == "#text") {
                    ((Element)elements.item(i)).getFirstChild().setNodeValue(title);
                    log.debug("Setting title '" + title + "' inside element: " + elements.item(i).getNodeName());
                } else {
                  log.error("Title not set inside element: " + elements.item(i).getNodeName());
                }
                if (log.isDebugEnabled()) log.debug("Current (new) Value: " + ((Element)elements.item(i)).getFirstChild().getNodeValue());
            }
        }
    }
}
