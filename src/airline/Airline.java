package airline;

import aircraft.*;

import java.util.ArrayList;
import java.util.List;

public class Airline {
    private String companyName;
    private List<Plane> listOfPlane;
    public Airline(String companyName, List<Plane> listOfPlane){
        this.companyName = companyName;
        this.listOfPlane = listOfPlane;
    }
    public String getCompanyName() {
        return companyName;
    }
    public List<Plane> getListOfPlane() {
        return listOfPlane;
    }
    public List<Plane> getTransportPlanes() {
        List<Plane> transportPlanes = new ArrayList<>();
        for(Plane plane : this.listOfPlane){
            if(plane instanceof TransportPlane) transportPlanes.add(plane);
        }
        return transportPlanes;
    }
    public List<Plane> getPassengerPlanes() {
        List<Plane> passengerPlanes = new ArrayList<>();
        for(Plane plane : this.listOfPlane){
            if(plane instanceof PassengerPlane) passengerPlanes.add(plane);
        }
        return passengerPlanes;
    }

    @Override
    public String toString() {
        return companyName;
    }
}
