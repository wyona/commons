package org.wyona.commons.xml;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class XMLHelper {
    private static final Logger log = Logger.getLogger( XMLHelper.class );
    private static final String YES = "yes";
    private static final String NO = "no";
    private static final String DEFAULT_ENCODING = "iso-8859-1";
    
    /**
     * Convert a Document into something human readable.
     * 
     * @param document The document to be displayed
     * @param isFragment
     * @param indent
     * @param charset
     * @return
     */
    public static final String documentToString( Document document, boolean isFragment, boolean indent, String charset ) {
        if( document == null ){
            log.warn("document to string called with a null document!, don't do this.");
            return null;
        }
        StringWriter strWtr = new StringWriter();
        StreamResult strResult = new StreamResult(strWtr);
        TransformerFactory tfac = TransformerFactory.newInstance();
        try {
            Transformer t = tfac.newTransformer();
            if( isFragment )
                t.setOutputProperty( OutputKeys.OMIT_XML_DECLARATION, YES );
            
            if( charset == null)
                t.setOutputProperty(OutputKeys.ENCODING, DEFAULT_ENCODING);
            else 
                t.setOutputProperty(OutputKeys.ENCODING, charset );
            t.setOutputProperty(OutputKeys.INDENT, (indent) ? YES : NO);
            t.setOutputProperty(OutputKeys.METHOD, "xml"); //xml, html, text
            t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            t.transform( new DOMSource(document.getDocumentElement()), strResult);
        } catch (Exception e) {
            log.error( e.getLocalizedMessage() );
        }
        return strResult.getWriter().toString();
    }
           
    /**
     * Creates a Document instance ( DOM )
     * 
     * @param rootName Name of the root element
     * @return Document object representing the rootname
     * @throws RuntimeException
     */
    public static final Document  createDocument( String rootName ) throws RuntimeException {
        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement( rootName );
            doc.appendChild(root);
            return doc;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException( "Unable to create a document builder, check your xml configuration" );
        }
    }
    
    /**
     * Utility method to create a text element. This method handles escaping if xml special characters.
     * 
     * @param doc the document the owns the element
     * @param name element name
     * @param value The text contained by the element 
     * @param attribs Hashmap of element attributes (optional)
     * @return Fully formatted xml element 
     */
    public static final Element createTextElement( Document doc, String name, String value, HashMap<String, String> attribs ){
        Element child = doc.createElement( name );
        if( attribs !=null ){
            Iterator<Entry<String,String>> iter = attribs.entrySet().iterator();
            while( iter.hasNext() ){
                Entry<String, String> attr = iter.next();
                child.setAttribute( attr.getKey(), attr.getValue() );    
            }
        }
        Text text = doc.createTextNode( value );
        child.appendChild(text);
        return child;
    }
}
