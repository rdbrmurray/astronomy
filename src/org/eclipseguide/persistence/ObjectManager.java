/* ObjectManager.java
 * Created on Oct 3, 2015 
 */
package org.eclipseguide.persistence;

public abstract class ObjectManager
{

    abstract boolean dropObjectTable();

    abstract Object get(int key);

    abstract boolean save(Object o, int key);

    abstract boolean update(Object o, int key);

    abstract boolean delete(int key);
    
    public static ObjectManager createObjectManager(Class type)
    {
        return FileObjectManager.createObjectManager(type);
    }

}