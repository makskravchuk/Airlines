package aircraft;

public class PassengerPlane extends Plane{
    private int capacity;  //місткість
    public PassengerPlane(int planeId,String PlaneName, double wingspan, double length, double height, int maxTakeoffWeight, int planeWeight, int maxSpeed, int cruisingSpeed, int flightRange, String engines, int fuelConsumption, int capacity) {
        super(planeId,PlaneName,wingspan, length, height, maxTakeoffWeight, planeWeight, maxSpeed, cruisingSpeed, flightRange, engines, fuelConsumption);
        this.capacity = capacity;
    }
    public int getCapacity() {
        return capacity;
    }

    @Override
    public String toString(){
        return super.toString()   + "  capacity:" + capacity  + " |\n";
    }
}
