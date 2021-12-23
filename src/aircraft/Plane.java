package aircraft;


public abstract class Plane  {
    protected int planeId;  //ID літака
    protected String planeName;  //назва літака
    protected double wingspan;   //розмах крила
    protected double length;     //довжина
    protected double height;      //висота
    protected int maxTakeoffWeight; //максимальна злітна маса
    protected int planeWeight;       //маса літака
    protected int maxSpeed;        //максимальна швидкість
    protected int cruisingSpeed;   //крейсерська швидкість
    protected int flightRange;    //дальність польоту
    protected String engines;          //двигуни
    protected int fuelConsumption;   //розхід пального

    public Plane(int planeId,String PlaneName, double wingspan, double length, double height, int maxTakeoffWeight, int planeWeight, int maxSpeed, int cruisingSpeed, int flightRange, String engines, int fuelConsumption) {
        this.planeId = planeId;
        this.planeName = PlaneName;
        this.wingspan = wingspan;
        this.length = length;
        this.height = height;
        this.maxTakeoffWeight = maxTakeoffWeight;
        this.planeWeight = planeWeight;
        this.maxSpeed = maxSpeed;
        this.cruisingSpeed = cruisingSpeed;
        this.flightRange = flightRange;
        this.engines = engines;
        this.fuelConsumption = fuelConsumption;
    }

    public int getPlaneId() {
        return planeId;
    }
    public String getPlaneName() {
        return planeName;
    }
    public int getFlightRange() { return flightRange; }
    public int getFuelConsumption() {
        return fuelConsumption;
    }
    public double getWingspan() {
        return wingspan;
    }
    public double getLength() {
        return length;
    }
    public double getHeight() {
        return height;
    }
    public int getMaxTakeoffWeight() {
        return maxTakeoffWeight;
    }
    public int getPlaneWeight() {
        return planeWeight;
    }
    public int getMaxSpeed() {
        return maxSpeed;
    }
    public int getCruisingSpeed() {
        return cruisingSpeed;
    }
    public String getEngines() {
        return engines;
    }
    @Override
    public String toString() {
        return   "|" + "plane id: " + planeId   + " |  plane name:" + planeName
                +  " |  wingspan:" + wingspan  + "m |"  + "  length:" + length + "m |"  + "  height:" + height +
                 "m |"   + "  max take off weight:" + maxTakeoffWeight  + "t |"  +"  plane weight:" + planeWeight +
                "t |"  + "  max speed:" + maxSpeed  + "km/h |" + "  cruising speed:" + cruisingSpeed +
                 "km/h |" +"  flight range:" + flightRange +  "km |"   + "  engines:" + engines +
                  " |  fuel consumption:" + fuelConsumption + "kg/h |";

    }
}
