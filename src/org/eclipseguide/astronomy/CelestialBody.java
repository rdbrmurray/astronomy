package org.eclipseguide.astronomy;

abstract public class CelestialBody
{

   public String name;
   public long radius;
   public long mass;
   public long rotationPeriod;
   public long surfaceTemperature;

   public boolean equals(Object o)
   {
      boolean equal = false;

      if (o instanceof CelestialBody)
      {
         CelestialBody body = (CelestialBody) o;
         equal =
            cmpStrings(name, body.name)
               && radius == body.radius
               && mass == body.mass
               && rotationPeriod == body.rotationPeriod
               && surfaceTemperature == body.surfaceTemperature;

      }
      System.out.println("leaving CelestialBody equals()");
      return equal;
   }

   public static boolean cmpStrings(String a, String b)
   {
      if (a == null && b == null) // both null
      {
         return true;
      }
      if (a == null || b == null) // one null
      {
         return false;
      }
      return a.equals(b); // ok to test
   }

}
