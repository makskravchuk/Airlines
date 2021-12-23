package action;
import java.lang.reflect.Method;
import java.util.*;

import aircraft.*;

public class PlanesInfoOperations {
    private static class PlaneComparator implements Comparator<Plane>{
        private final String parameter;
        public PlaneComparator(String parameter){
            this.parameter = parameter;
        }
        @Override
        public int compare(Plane plane1, Plane plane2) {
            try {
                Method method = plane1.getClass().getMethod(parameter);
                Object object1 = method.invoke(plane1);
                Object object2 = method.invoke(plane2);

                if(object1 instanceof Integer) return ((Integer)object1).compareTo((Integer)object2);
                if(object1 instanceof Double) return ((Double)object1).compareTo((Double)object2);
                if(object1 instanceof String) return ((String)object1).compareTo((String)object2);
            }
            catch(Exception e){ return 0;}
            return 0;
        }
    }
    public static int calculateTotalCapacity(List<Plane> planes){
        int capacity = 0;
        for(Plane plane : planes) if(plane instanceof PassengerPlane) capacity += ((PassengerPlane) plane).getCapacity();
        return capacity;
    }
    public static int calculateTotalLoadCapacity(List<Plane> planes){
       int loadCapacity = 0;
       for(Plane plane : planes) if(plane instanceof TransportPlane) loadCapacity += ((TransportPlane) plane).getLoadCapacity();
       return loadCapacity;
    }
    public static void sortPlanes(List<Plane> planes, String parameter, boolean growth){
         PlaneComparator planeComparator = new PlaneComparator(parameter);
         planes.sort(planeComparator);
         if(!growth){
             Collections.reverse(planes);
         }
    }
    public static List<Plane> findPlanesByParameterRange(List<Plane> planes, String parameter,double min, double max) {
        List<Plane> suitablePlanes = new ArrayList<>();
        for (Plane plane : planes) {
            try {
                Method method = plane.getClass().getMethod(parameter);
                Object object = method.invoke(plane);
                if (object instanceof Integer) {
                    if ((int) object >= min && (int) object <= max) suitablePlanes.add(plane);
                }
                else if (object instanceof Double) {
                    if ((double) object >= min && (double) object <= max) suitablePlanes.add(plane);
                }
            }
            catch(Exception e){continue;}
        }
        return suitablePlanes;
    }
    public static List<Plane> findPlanesByParameter(List<Plane> planes, String parameter, String value) {
        List<Plane> suitablePlanes = new ArrayList<>();

            for (Plane plane : planes) {
                try {
                    Method method = plane.getClass().getMethod(parameter);
                    Object object = method.invoke(plane);
                    if (object instanceof Integer) {
                        if ((int) object == Integer.parseInt(value)) suitablePlanes.add(plane);
                    } else if (object instanceof Double) {
                        if ((double) object == Double.parseDouble(value)) suitablePlanes.add(plane);
                    } else if (object instanceof String) {
                        if (object.equals(value)) suitablePlanes.add(plane);
                    }
                }
                catch(Exception e){ continue;}
            }

        return suitablePlanes;
    }
    public static List<String> planeParameters(){
        List<String> parameters = new ArrayList<>();
        parameters.add("getPlaneId");
        parameters.add("getPlaneName");
        parameters.add("getWingspan");
        parameters.add("getLength");
        parameters.add("getHeight");
        parameters.add("getMaxTakeoffWeight");
        parameters.add("getPlaneWeight");
        parameters.add("getMaxSpeed");
        parameters.add("getMaxSpeed");
        parameters.add("getCruisingSpeed");
        parameters.add("getFlightRange");
        parameters.add("getEngines");
        parameters.add("getFuelConsumption");
        parameters.add("getCapacity");
        parameters.add("getLoadCapacity");
        return parameters;
    }
}
