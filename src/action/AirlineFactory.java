package action;

import airline.Airline;
public class AirlineFactory {
    public static Airline createAirline(String airlineName)  {
        return new Airline(airlineName,null);
    }
}
