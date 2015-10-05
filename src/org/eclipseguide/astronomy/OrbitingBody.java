package org.eclipseguide.astronomy;

import java.util.Vector;

abstract public class OrbitingBody extends CelestialBody
{

   public long meanOrbit;
   public double eccentricity;
   public long revolutionPeriod;
   public long atmosphericPressure;
   public Vector atmosphericComposition;

}
