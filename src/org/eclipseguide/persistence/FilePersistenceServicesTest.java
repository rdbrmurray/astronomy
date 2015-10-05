/* FilePersistenceServicesTest.java
 * Created on Sep 20, 2015 
 */
package org.eclipseguide.persistence;

import java.util.StringTokenizer;
import java.util.Vector;

import junit.framework.TestCase;

public class FilePersistenceServicesTest extends TestCase
{

    Vector<String> v1, v2; 
    
    String s1 , s2;
    
    String _file = "TestTable";
    
    /**
     * Constructor for FilePersistenceServicesTest.
     * @param arg0
     */
    public FilePersistenceServicesTest(String arg0)
    {
       super(arg0);
    }
    
    protected void setUp() throws Exception
    {
        super.setUp();
        boolean f = FilePersistenceServices.drop(_file);
        v1 = new Vector<String>();
        v1.addElement("One");
        v1.addElement("Two");
        v1.addElement("Three");
        
        v2 = new Vector<String>();
        v2.addElement("A");
        v2.addElement("B");
        v2.addElement("C");
        
        s1 = "\"1\",\"One\",\"Two\",\"Three\"";
        s2 = "\"1\",\"A\",\"B\",\"C\"";        
        
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
    }

    public void testWrite()
    {   
        assertTrue(FilePersistenceServices.write( _file, 1, v1));
        assertFalse(FilePersistenceServices.write(_file, 1, v1));
        assertTrue(FilePersistenceServices.write( _file, 2, v2));
    }

    public void testRead()
    {
        FilePersistenceServices.write(_file, 1, v1);
        FilePersistenceServices.write(_file, 2, v2);
        Vector<String> w;
        w = FilePersistenceServices.read(_file, 1);
        assertEquals(w, v1);
        w = FilePersistenceServices.read(_file, 2);
        assertEquals(w, v2);
    }
    
    public void testVector2String()
    {
        assertEquals(s1, FilePersistenceServices.vector2String(v1, 1));
    }
    
    public void testString2Vector()
    {
        assertEquals(FilePersistenceServices.string2Vector(s1), v1);
    }
    
    public void testGetKey()
    {
        assertEquals(1, FilePersistenceServices.getKey(s1));       
    }
    
    public void testDrop()
    {
        FilePersistenceServices.write(_file, 1, v1);
        FilePersistenceServices.write(_file, 2, v2);
        assertNotNull(FilePersistenceServices.read(_file, 1));
        assertNotNull(FilePersistenceServices.read(_file, 2));
        assertTrue(FilePersistenceServices.drop(_file));
        assertNull(FilePersistenceServices.read(_file, 1));
        assertNull(FilePersistenceServices.read(_file, 2));
    }
    
    public void testDelete()
    {
        FilePersistenceServices.write(_file, 1, v1);
        FilePersistenceServices.write(_file, 2, v2);
        assertNotNull(FilePersistenceServices.read(_file, 1));
        assertNotNull(FilePersistenceServices.read(_file, 2));
        assertTrue(FilePersistenceServices.delete(_file, 1));
        assertNull(FilePersistenceServices.read(_file, 1));
        Vector<String> w = FilePersistenceServices.read(_file, 2);
        assertEquals(w, v2);        
    }

}
