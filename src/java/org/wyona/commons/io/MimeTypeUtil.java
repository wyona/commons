package org.wyona.commons.io;

/**
 * This class contains some methods related to mime-types.
 * Note that the list of used mime-types is incomplete.
 *
 */
public class MimeTypeUtil {
    
    /**
     * Indicates whether a mimet-ype represents a textual document, 
     * i.e. a document which has an encoding and possiby line-breaks.
     * @param mimeType
     * @return true if mime-type is textual.
     */
    public static boolean isTextual(String mimeType) {
        if (mimeType.startsWith("text/") 
                || mimeType.equals("application/x-javascript")
                || mimeType.equals("application/javascript")
                || mimeType.equals("application/json")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Indicates whether a mimet-ype represents an XML document
     * @param mimeType
     * @return true if mime-type is XML.
     */
    public static boolean isXML(String mimeType) {
        if (mimeType.equals("application/xml")
                || mimeType.endsWith("+xml")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Indicates whether a mime-type represents an document containing html
     * or xhtml.
     * @param mimeType
     * @return true if mime-type represents a html document.
     */
    public static boolean isHTML(String mimeType) {
        if (mimeType.equals("text/html") || mimeType.equals("application/xhtml+xml")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Returns a default extension for a given mime-type, or an 
     * empty string if the mimetype is unknown.
     * @param type
     * @return extension
     */
    public static String getExtension(String type) {
        if (type.equals("application/msword")) return "doc";
        if (type.equals("application/pdf")) return "pdf";
        if (type.equals("application/vnd.oasis.opendocument.text")) return "odt";
        if (type.equals("application/vnd.sun.xml.calc")) return "sxc";
        if (type.equals("application/x-javascript") || type.equals("text/javascript")) return "js";
        if (type.equals("application/x-xpinstall")) return "xpi";
        if (type.equals("application/zip")) return "zip";
        if (type.equals("image/bmp")) return "bmp";
        if (type.equals("image/gif")) return "gif";
        if (type.equals("image/tiff")) return "tif";
        if (type.equals("image/jpeg")) return "jpg";
        if (type.equals("image/png")) return "png";
        if (type.equals("text/css")) return "css";
        if (type.equals("text/html") || type.equals("application/xhtml+xml")) return "html";
        if (type.equals("text/plain")) return "txt";
        if (type.equals("text/xml") || type.equals("application/xml")) return "xml";
        if (type.equals("video/quicktime")) return "mov";
        if (type.equals("")) return "";
        // TODO: add more
        return "";
    }
    
    /**
     * Returns the mime-type according to the given file extension.
     * Default is application/octet-stream.
     * @param extension
     * @return mime-type
     */
    public static String guessMimeType(String extension) {
        if(extension != null){
            String ext = extension.toLowerCase();
            
            if (ext.equals("css")) return "text/css";
            if (ext.equals("doc")) return "application/msword";
            
            if (ext.equals("html") || ext.equals("htm")) return "text/html";
            
            if (ext.equals("gif")) return "image/gif";
            if (ext.equals("jpg") || ext.equals("jpeg")) return "image/jpeg";
            if (ext.equals("bmp")) return "image/bmp";
            if (ext.equals("tiff") || ext.equals("tif")) return "image/tiff";
            if (ext.equals("png")) return "image/png";
            
            if (ext.equals("js")) return "application/x-javascript";
            if (ext.equals("mov")) return "video/quicktime";
            if (ext.equals("odt")) return "application/vnd.oasis.opendocument.text";
            if (ext.equals("pdf")) return "application/pdf";
            if (ext.equals("sxc")) return "application/vnd.sun.xml.calc";
            if (ext.equals("txt")) return "text/plain";
            if (ext.equals("xhtml")) return "application/xhtml+xml";
            if (ext.equals("xml")) return "application/xml";
            if (ext.equals("xpi")) return "application/x-xpinstall";
            if (ext.equals("zip")) return "application/zip";
            // TODO: add more
        }
        return "application/octet-stream"; // default
    }

}