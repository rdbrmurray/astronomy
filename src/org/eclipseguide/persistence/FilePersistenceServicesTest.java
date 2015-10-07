/* FilePersistenceServicesTest.java
 * Created on Sep 20, 2015 
 */
package org.eclipseguide.persistence;

import java.util.StringTokenizer;
import java.util.Vector;

import junit.framework.TestCase;

public class FilePersistenceServicesTest extends TestCase
{

    Vector v1, v2; 
    
    String s1 , s2;
    
    String _file = "TestTable";
    
    FilePersistenceServices fsp = new FilePersistenceServices(_file);
    
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
        boolean f = fsp.drop();
        v1 = new Vector();
        v1.addElement("One");
        v1.addElement("Two");
        v1.addElement("Three");
        
        v2 = new Vector();
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
        assertTrue(fsp.write(1, v1));
        assertFalse(fsp.write(1, v1));
        assertTrue(fsp.write(2, v2));
    }

    public void testRead()
    {
        fsp.write(1, v1);
        fsp.write(2, v2);
        Vector w;
        w = fsp.read(1);
        assertEquals(w, v1);
        w = fsp.read(2);
        assertEquals(w, v2);
    }
    
    public void testVector2String()
    {
        assertEquals(s1, fsp.vector2String(v1, 1));
    }
    
    public void testString2Vector()
    {
        assertEquals(fsp.string2Vector(s1), v1);
    }
    
    public void testGetKey()
    {
        assertEquals(1, fsp.getKey(s1));       
    }
    
    public void testDrop()
    {
        fsp.write(1, v1);
        fsp.write(2, v2);
        assertNotNull(fsp.read(1));
        assertNotNull(fsp.read(2));
        assertTrue(fsp.drop());
        assertNull(fsp.read(1));
        assertNull(fsp.read(2));
    }
    
    public void testDelete()
    {
        fsp.write(1, v1);
        fsp.write(2, v2);
        assertNotNull(fsp.read(1));
        assertNotNull(fsp.read(2));
        assertTrue(fsp.delete(1));
        assertNull(fsp.read(1));
        Vector w = fsp.read(2);
        assertEquals(w, v2);        
    }

}
