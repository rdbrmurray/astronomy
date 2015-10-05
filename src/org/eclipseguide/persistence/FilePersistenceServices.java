/* FilePersistenceServices.java
 * Created on Sep 20, 2015 
 */
package org.eclipseguide.persistence;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

public class FilePersistenceServices
{
    
    static Logger logger = Logger.getLogger(FilePersistenceServices.class) ; //Logger.getLogger(FilePersistenceServices.class);
    
    public static boolean write(String fileName, int key, Vector v)
    {
       boolean success = false;
       // make sure record does not already exist
       if(read(fileName, key) != null)
       {
           return success;
       }
       
       String s = vector2String(v, key);
       try
       {
           BufferedWriter out = 
                   new BufferedWriter(new FileWriter(fileName, true));
           out.write(s);
           out.newLine();
           out.close();
           success = true;
       }
       catch(IOException e)
       {
           success = false;
           logger.warn(e.getMessage());
       }
       
       return success;
    }
    
    public static Vector read(String fileName, int key)
    {
        logger.debug("Entering read()");
     
        Vector v = null;
        try
        {
            FileReader fr = new FileReader(fileName);
            BufferedReader in = new BufferedReader(fr);
            String str;
            boolean found = false;
            while((str = in.readLine()) != null
                    && !(found = (getKey(str) == key)))
            {                
            }
            in.close();
            
            if(found)
            {
                v = string2Vector(str);
            }
            else
            {
                logger.warn("Failed to find key: " + key);
            }
        }
        catch(IOException e){
            // do nothing
            logger.warn(e.getMessage());
        }
        
        return v;
    }

    public static String vector2String(Vector v, int key)
    {
        String s = null;
        StringBuffer buffer = new StringBuffer();
        // start with key
        buffer.append("\"" + Integer.toString(key) + "\",");
        // add comma, quote-delimited entry for each element in v
        for(int i = 0; i < v.size(); i++)
        {
            buffer.append("\"");
            buffer.append(v.elementAt(i));
            buffer.append("\"");
            if(i != (v.size() - 1))
            {
                buffer.append(",");
            }
        }
        
        s = buffer.toString();
        return s;
    }
    
    public static boolean update(String fileName, int key, Vector v)
    {
       boolean success = false;
       if (read(fileName, key) != null)
       {
          if (delete(fileName, key) && write(fileName, key, v))
          {
             success = true;
          }
       }
       return success;
    }

    public static Vector string2Vector(String s)
    {
        Vector v = new Vector();
        // use comma and double quotes as delimiters
        StringTokenizer st = new StringTokenizer(s, "\",");
        int counter  = 0;
        while(st.hasMoreTokens())
        {
            if(++counter == 1) { st.nextToken() ; continue;}
            v.addElement(st.nextToken());
        }
        return v;        
    }

    public static int getKey(String s)
    {
        // TODO Auto-generated method stub
        int key = -1;
        StringTokenizer st = new StringTokenizer(s, "\",");
        if(st.hasMoreTokens())
        {
            String token = st.nextToken();
            key = Integer.parseInt(token);
        }
        return key;
    }

    public static boolean drop(String fileName)
    {
        File f = new File(fileName);
        return f.delete();
    }

    public static boolean delete(String fileName, int key)
    {
        String buffer = null;
        try
        {
            RandomAccessFile file = new RandomAccessFile(fileName, "rw");
            
            boolean cont = true;
            while(cont)
            {
                // remember start of line
                long fp = file.getFilePointer();
                buffer = file.readLine();
                if(buffer != null)
                {
                    if(getKey(buffer) == key)
                    {
                        // return to beginning of line to delete
                        file.seek(fp);
                        file.writeBytes("\"0\"");
                        cont = false;
                    }
                }
                else 
                {
                    cont = false;
                }
            }
            
            file.close();
        }
        catch (FileNotFoundException e)
        { 
            logger.warn(e.getMessage());
        }
        catch (IOException e)
        {
            logger.warn(e.getMessage());
        }
        
        return buffer != null;
    }
}
