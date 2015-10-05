/* ObjectManagerTest.java
 * Created on Sep 23, 2015 
 */
package org.eclipseguide.persistence;

import static org.junit.Assert.*;

import org.eclipseguide.astronomy.Star;
import junit.framework.TestCase;


/**
 * @author rdbrmurray
 *
 */
public class ObjectManagerTest extends TestCase
{

   
    public void testStar()
    {
        // start fresh
        ObjectManager starMgr = FileObjectManager.createObjectManager(Star.class);
        
        starMgr.dropObjectTable();
        
        Star s = new Star();
        s.catalogNumber = "HD358";
        s.absoluteMagnitude = 2.1;
        s.spectralType = "B8IVp";
        s.constellation = "Andromeda";
        s.galaxy = "Milky Way'";
        
        s.name = "Alpheratz";
        s.radius = 5;
        s.mass = 0;
        s.rotationPeriod = 0;
        s.surfaceTemperature = 9100;
        
        assertTrue(starMgr.save(s, 1));
        
        Star newStar = (Star)starMgr.get(1);
        assertNotNull(newStar);
        assertEquals(s, newStar);
        
        newStar.absoluteMagnitude = 123;
        newStar.radius = 500;
        starMgr.update(newStar, 1);
        
        Star modStar = (Star)starMgr.get(1);
        assertNotSame(newStar, modStar);
        
        assertTrue(starMgr.delete(1));
        assertNull(starMgr.get(1));
    }

}
