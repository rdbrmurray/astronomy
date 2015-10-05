/* AllTests.java
 * Created on Oct 2, 2015 
 */
package org.eclipseguide.persistence;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase
{

    public static Test suite()
    {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        //$JUnit-BEGIN$
        suite.addTestSuite(FilePersistenceServicesTest.class);
        suite.addTestSuite(ObjectManagerTest.class);
        //$JUnit-END$
        return suite;
    }

}
