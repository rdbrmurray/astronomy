/* FileObjectManager.java
 * Created on Sep 23, 2015 
 */
package org.eclipseguide.persistence;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.eclipseguide.astronomy.CelestialBody;
import org.eclipseguide.astronomy.Star;

public class FileObjectManager extends ObjectManager
{
    
    static Logger logger = Logger.getLogger(FileObjectManager.class);
    Collection fieldMap = null;
    Class classType = null;
    String className = null;

    private FileObjectManager()
    {
    }

    public static ObjectManager createObjectManager(Class type)
    {
       FileObjectManager om = new FileObjectManager();
       om.classType = type;
       om.className = type.getName();
       om.setFieldMap();
       return om;
    }

    /* (non-Javadoc)
     * @see org.eclipseguide.persistence.ObjectManager#dropObjectTable()
     */
    @Override
    public boolean dropObjectTable()
    {
       return FilePersistenceServices.drop(className);
    }

    /* (non-Javadoc)
     * @see org.eclipseguide.persistence.ObjectManager#get(int)
     */
    @Override
    public Object get(int key)
    {
       Object o = null;
       try
       {
          Vector v = FilePersistenceServices.read(className, key);
          Field field = null;
          int size;
          if (v != null && (size = fieldMap.size()) == v.size() && size > 0)
          {
             o = classType.newInstance();
             Iterator vIter = v.iterator();
             Iterator mIter = fieldMap.iterator();
             for (int i = 0; i < size; i++)
             {
                FieldMapEntry entry = (FieldMapEntry) mIter.next();
                field = classType.getField(entry.attributeName);
                Class fieldType = field.getType();
                Object value = typeMap(fieldType, vIter.next());
                field.set(o, value);
             }
          }
       }
       catch (Exception e)
       {
          logger.warn(e);
       }
       return o;
    }

    /* (non-Javadoc)
     * @see org.eclipseguide.persistence.ObjectManager#save(java.lang.Object, int)
     */
    @Override
    public boolean save(Object o, int key)
    {
       boolean success = false;
       if (!(classType.isInstance(o)))
       {
          return success;
       }

       Vector v = object2Vector(o);

       if (v.size() > 0)
       {
          success = FilePersistenceServices.write(className, key, v);
       }
       return success;
    }

    /* (non-Javadoc)
     * @see org.eclipseguide.persistence.ObjectManager#update(java.lang.Object, int)
     */
    @Override
    public boolean update(Object o, int key)
    {
       boolean success = false;
       if (!(classType.isInstance(o)))
       {
          return success;
       }
       Vector v = object2Vector(o);
       if (v.size() > 0)
       {
          success = FilePersistenceServices.update(className, key, v);
       }
       return success;
    }

    /* (non-Javadoc)
     * @see org.eclipseguide.persistence.ObjectManager#delete(int)
     */
    @Override
    public boolean delete(int key)
    {
       return FilePersistenceServices.delete(className, key);
    }

    Vector object2Vector(Object o)
    {
       Vector v = new Vector();
       for (Iterator iter = fieldMap.iterator(); iter.hasNext();)
       {
          FieldMapEntry entry = (FieldMapEntry) iter.next();
          Field field;
          try
          {
             field = classType.getField(entry.attributeName);
             v.addElement(field.get(o));
          }
          catch (NoSuchFieldException e)
          {
             logger.warn(e);
          }
          catch (IllegalAccessException e)
          {
             logger.warn(e);
          }
       }
       return v;
    }

    void setFieldMap()
    {
       Field[] f = classType.getFields();
       TreeMap map = new TreeMap();
       for (int i = 0; i < f.length; i++)
       {
          FieldMapEntry entry = new FieldMapEntry();
          entry.attributeName = f[i].getName();
          entry.attributeType = (Class<CelestialBody>)f[i].getType();
          map.put(entry.attributeName, entry);
       }
       fieldMap = map.values();
    }

    Object typeMap(Class type, Object val)
    {
       Object o = null;
       String typeName = type.getName();
       String valString = val.toString();
       if (typeName.equals("java.lang.String"))
       {
          if (((String) valString).equals("null"))
          {
             o = null;
          }
          else
          {
             o = valString;
          }
       }
       else if (typeName.equals("long"))
       {
          o = Long.valueOf(valString);
       }
       else if (typeName.equals("int"))
       {
          o = Integer.valueOf(valString);
       }
       else if (typeName.equals("double"))
       {
          o = Double.valueOf(valString);
       }
       return o;
    }
}

class FieldMapEntry
{
   String attributeName;
   Class<CelestialBody> attributeType;
}
