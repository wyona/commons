package org.wyona.commons.xml;
import static org.junit.Assert.*;
import java.util.Iterator;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.ri.model.NodePointer;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLHelperTest {

    @Test
    public void testToStringDocumentBooleanBooleanString() {
        final String expectedXML = "<wyona><employees><employee>employee0</employee><employee>employee1</employee><employee>employee2</employee><employee>employee3</employee><employee>employee4</employee><employee>employee5</employee><employee>employee6</employee><employee>employee7</employee><employee>employee8</employee><employee>employee9</employee></employees></wyona>";
        
        try {
            Document doc = XMLHelper.createDocument( "wyona");
            Element root = doc.getDocumentElement();
            root.appendChild( getEmployees( doc ) );
            String result = XMLHelper.documentToString( doc, true, false, null );
            assertEquals("Resultant xml was not as expected \nExpected:"+expectedXML+"\nActual:"+result, expectedXML, result ); 
        } catch (RuntimeException e) {
            fail( e.getLocalizedMessage() );
        }
    }

    @Test
    public void testCreateDOM() {
        final String rootTagname = "root";
        try {
            Document doc = XMLHelper.createDocument( rootTagname );
            assertNotNull( "Document could not be created", doc );
            Element root = doc.getDocumentElement();
            assertNotNull( "Document root element should not be null",root );
            String tagName = root.getTagName();
            assertEquals("Root element did not have the expected tagname of "+rootTagname+" instead it was "+tagName, rootTagname, tagName );
        } catch ( RuntimeException e) {
            fail( e.getLocalizedMessage() );
        }
    }

    @Test
    public void testCreateTextElement() {
        final String path =  "//employees/employee";
        try {
            Document doc = XMLHelper.createDocument( "wyona");
            Element root = doc.getDocumentElement();
            root.appendChild( getEmployees( doc ) );
            boolean located = false;
            NodePointer pointer;
            JXPathContext context = JXPathContext.newContext(doc);
            Iterator<NodePointer> projectList = context.iteratePointers( path );
            while ((pointer = (NodePointer)projectList.next())!=null){
                located = true;
                break;
            }
            assertTrue( "Element with path "+path +" not found", located );
        } catch (RuntimeException e) {
            fail( e.getLocalizedMessage() );
        }
    }

    private Node getEmployees( Document doc ){
        final String employee = "employee";
        Element elist = doc.createElement("employees");
        for( int j=0; j<10; j++ ){
            Node newNode = XMLHelper.createTextElement( doc, employee, employee+j, null );
            elist.appendChild( newNode );
        }
        return elist;
    }
}
