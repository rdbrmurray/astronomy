package org.eclipseguide.astronomy;

public class Star extends CelestialBody
{

   public String catalogNumber;
   public double absoluteMagnitude;
   public String spectralType;
   public String constellation;
   public String galaxy;

   public boolean equals(Object o)
   {
      boolean equal = false;

      if (o instanceof Star)
      {
         Star star = (Star) o;
         equal =
            cmpStrings(catalogNumber, star.catalogNumber)
               && absoluteMagnitude == star.absoluteMagnitude
               && cmpStrings(spectralType, star.spectralType)
               && cmpStrings(constellation, star.constellation)
               && cmpStrings(galaxy, star.galaxy);
         equal = super.equals(o) && equal;
      }

      return equal;
   }
}
