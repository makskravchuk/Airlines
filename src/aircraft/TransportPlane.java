package aircraft;

public class TransportPlane extends Plane{
    private int loadCapacity; //вантажопідйомність
    public TransportPlane(int planeId, String PlaneName,double wingspan, double length, double height,
                          int maxTakeoffWeight, int planeWeight, int maxSpeed, int cruisingSpeed,
                          int flightRange, String engines, int fuelConsumption, int LoadCapacity) {
        super(planeId,PlaneName,wingspan, length, height, maxTakeoffWeight, planeWeight, maxSpeed, cruisingSpeed, flightRange, engines, fuelConsumption);
        this.loadCapacity = LoadCapacity;
    }
    public int getLoadCapacity(){ return loadCapacity;}
    @Override
    public String toString(){
        return super.toString()  + "  load capacity:" + loadCapacity + "t"  + " |\n";
    }
}
