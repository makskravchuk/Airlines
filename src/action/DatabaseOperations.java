package action;

import databaseconnection.*;
import aircraft.PassengerPlane;
import aircraft.Plane;
import aircraft.TransportPlane;
import airline.Airline;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    private final DatabaseHandler databaseHandler;
    public DatabaseOperations(){
        databaseHandler = new DatabaseHandler();
    }
    public void saveAirline(Airline airline) throws Exception{
        String insert = "INSERT INTO " + TablesConst.AIRLINES_INFO_TABLE + "(" + AirlinesInfoTableConst.COMPANY_NAME + ")" +
                "VALUES(?)";
        String get = "SELECT " + AirlinesInfoTableConst.AIRLINE_ID +  " FROM " + TablesConst.AIRLINES_INFO_TABLE  +
                " ORDER BY " + AircraftTableConst.AIRLINE_ID + " DESC LIMIT 1";

            PreparedStatement preparedStatement = databaseHandler.getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1,airline.getCompanyName());
            preparedStatement.executeUpdate();
            if(airline.getListOfPlane() == null) {
            databaseHandler.closeDbConnection();
            return;
            }
            Statement statement = databaseHandler.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(get);
            int airlineId;
            if(resultSet.next()) {
                airlineId = resultSet.getInt(AirlinesInfoTableConst.AIRLINE_ID);
                for (Plane plane : airline.getListOfPlane()) savePlane(plane, airlineId);
            }
            databaseHandler.closeDbConnection();
    }
    public void savePlane(Plane plane,int airlineId) {
        String insertPlane = "INSERT INTO " + TablesConst.AIRCRAFT_TABLE + "(" + AircraftTableConst.PLANE_NAME + "," +
                AircraftTableConst.WINGSPAN +"," + AircraftTableConst.LENGTH + "," +AircraftTableConst.HEIGHT +"," +
                AircraftTableConst.MAX_TAKEOFF_WEIGHT +"," + AircraftTableConst.PLANE_WEIGHT + "," +
                AircraftTableConst.MAX_SPEED +"," + AircraftTableConst.CRUISING_SPEED +"," +
                AircraftTableConst.FLIGHT_RANGE + "," +AircraftTableConst.ENGINES +"," +
                AircraftTableConst.FUEL_CONSUMPTION +"," + AircraftTableConst.AIRLINE_ID + ")" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        String get = "SELECT " + AircraftTableConst.PLANE_ID +  " FROM " + TablesConst.AIRCRAFT_TABLE  +
                " ORDER BY " + AircraftTableConst.PLANE_ID + " DESC LIMIT 1";
        try{
            PreparedStatement preparedStatement = databaseHandler.getDbConnection().prepareStatement(insertPlane);
            preparedStatement.setString(1,plane.getPlaneName());
            preparedStatement.setDouble(2,plane.getWingspan());
            preparedStatement.setDouble(3,plane.getLength());
            preparedStatement.setDouble(4,plane.getHeight());
            preparedStatement.setInt(5,plane.getMaxTakeoffWeight());
            preparedStatement.setInt(6,plane.getPlaneWeight());
            preparedStatement.setInt(7,plane.getMaxSpeed());
            preparedStatement.setInt(8,plane.getCruisingSpeed());
            preparedStatement.setInt(9,plane.getFlightRange());
            preparedStatement.setString(10,plane.getEngines());
            preparedStatement.setInt(11,plane.getFuelConsumption());
            preparedStatement.setInt(12,airlineId);
            preparedStatement.executeUpdate();
            //------------------------------------------------------------//
            Statement statement = databaseHandler.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(get);
            int planeId;
            if(resultSet.next()) {
                planeId = resultSet.getInt(AircraftTableConst.PLANE_ID);
                if(plane instanceof TransportPlane) saveTransportPlane(plane,planeId);
                else if(plane instanceof PassengerPlane) savePassengerPlane(plane,planeId);
            }
            databaseHandler.closeDbConnection();
        }
        catch(Exception e) {e.printStackTrace();}
    }
    private void saveTransportPlane(Plane plane,int planeId){
        String insertTransportPlane  = "INSERT INTO " + TablesConst.TRANSPORT_PLANES_TABLE + "(" +
                TransportPlanesTableConst.PLANE_ID + ", " + TransportPlanesTableConst.LOAD_CAPACITY + ") " +
                "VALUES(?,?)";
        try {
            PreparedStatement preparedStatement = databaseHandler.getDbConnection().prepareStatement(insertTransportPlane);
            preparedStatement.setInt(1, planeId);
            preparedStatement.setInt(2, ((TransportPlane)plane).getLoadCapacity());
            preparedStatement.executeUpdate();
            databaseHandler.closeDbConnection();
        }
        catch(Exception e){ e.printStackTrace();}
    }
    private void savePassengerPlane(Plane plane,int planeId){
        String insertPassengerPlane  = "INSERT INTO " + TablesConst.PASSENGER_PLANES_TABLE + "(" +
                PassengerPlanesTableConst.PLANE_ID + ", " + PassengerPlanesTableConst.CAPACITY + ") " +
                "VALUES(?,?)";
        try {
            PreparedStatement preparedStatement = databaseHandler.getDbConnection().prepareStatement(insertPassengerPlane);
            preparedStatement.setInt(1, planeId);
            preparedStatement.setInt(2, ((PassengerPlane)plane).getCapacity());
            preparedStatement.executeUpdate();
            databaseHandler.closeDbConnection();
        }
        catch(Exception e){ e.printStackTrace();}
    }
    public Airline downloadAirline(String companyName){
        String getAirline = "SELECT * FROM " + TablesConst.AIRLINES_INFO_TABLE + " WHERE " +
                AirlinesInfoTableConst.COMPANY_NAME + " = " + "'" + companyName + "'";
        try {
            Statement statement = databaseHandler.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(getAirline);
            if(resultSet.next()){
                int airlineId = resultSet.getInt(AirlinesInfoTableConst.AIRLINE_ID);
                List<Plane> planeList = new ArrayList<>(downloadPlanes(airlineId));
                return new Airline(companyName,planeList);
            }
            databaseHandler.closeDbConnection();
        }catch(Exception e){e.printStackTrace();}
        return null;
    }
    public List<Plane> downloadPlanes(int airlineId) {
        List<Plane> planeList = new ArrayList<>();
        String getPlanes = " SELECT * FROM " + TablesConst.AIRCRAFT_TABLE +
                " WHERE " + AircraftTableConst.AIRLINE_ID + " = " + airlineId;
        String getPassengerPlanes = " SELECT * FROM " +  TablesConst.PASSENGER_PLANES_TABLE  +
                " WHERE " + PassengerPlanesTableConst.PLANE_ID + " = ";
        String getTransportPlanes = " SELECT * FROM " +  TablesConst.TRANSPORT_PLANES_TABLE  +
                " WHERE " + TransportPlanesTableConst.PLANE_ID + " = ";

        try {
            Statement statement = databaseHandler.getDbConnection().createStatement();
            Statement tempStatement = databaseHandler.getDbConnection().createStatement();
            ResultSet resultSetPlane = statement.executeQuery(getPlanes);
            while(resultSetPlane.next()){
                int planeId = resultSetPlane.getInt(1);
                String planeName = resultSetPlane.getString(2);
                double wingspan = resultSetPlane.getDouble(3);
                double length = resultSetPlane.getDouble(4);
                double height = resultSetPlane.getDouble(5);
                int maxTakeoffWeight = resultSetPlane.getInt(6);
                int planeWeight = resultSetPlane.getInt(7);
                int maxSpeed = resultSetPlane.getInt(8);
                int cruisingSpeed = resultSetPlane.getInt(9);
                int flightRange = resultSetPlane.getInt(10);
                String engines = resultSetPlane.getString(11);
                int fuelConsumption = resultSetPlane.getInt(12);
                ResultSet resultSetPassengerPlane = tempStatement.executeQuery(getPassengerPlanes + planeId);
                if(resultSetPassengerPlane.next()) {
                    planeList.add(new PassengerPlane(planeId,planeName,wingspan,length,height,maxTakeoffWeight,planeWeight,maxSpeed,
                            cruisingSpeed,flightRange,engines,fuelConsumption,resultSetPassengerPlane.getInt(2)));
                    continue;
                }
                ResultSet resultSetTransportPlane = tempStatement.executeQuery(getTransportPlanes + planeId);
                if(resultSetTransportPlane.next()){
                    planeList.add(new TransportPlane(planeId,planeName,wingspan,length,height,maxTakeoffWeight,planeWeight,maxSpeed,
                            cruisingSpeed,flightRange,engines,fuelConsumption,resultSetTransportPlane.getInt(2)));
                }
            }
        }catch(Exception e){e.printStackTrace();}
        return planeList;
    }
    public List<Airline> downloadAirlines(){
        String getAirlines = "SELECT * FROM " + TablesConst.AIRLINES_INFO_TABLE;
        List<Airline> airlineList = new ArrayList<>();
        try {
            Statement statement = databaseHandler.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(getAirlines);
            while(resultSet.next()){
                int airlineId = resultSet.getInt(AirlinesInfoTableConst.AIRLINE_ID);
                String companyName = resultSet.getString(AirlinesInfoTableConst.COMPANY_NAME);
                List<Plane> planeList = new ArrayList<>(downloadPlanes(airlineId));
                //planeList.addAll();
                airlineList.add(new Airline(companyName,planeList));
            }
            databaseHandler.closeDbConnection();
        }catch(Exception e){e.printStackTrace();}
        return airlineList;
    }
    public List<String> getAirlinesName(){
        String query = "SELECT " + AirlinesInfoTableConst.COMPANY_NAME  + " FROM " + TablesConst.AIRLINES_INFO_TABLE;
        List<String> names = new ArrayList<>();
        try {
            Statement statement = databaseHandler.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
               names.add(resultSet.getString(1));
            }
            databaseHandler.closeDbConnection();
        }catch(Exception e){e.printStackTrace();}
        return names;
    }
    public int getAirlineId(String companyName){
        String query = "SELECT " + AirlinesInfoTableConst.AIRLINE_ID  + " FROM " + TablesConst.AIRLINES_INFO_TABLE +
                " WHERE " + AirlinesInfoTableConst.COMPANY_NAME + " = " + "'" + companyName + "'";
        int id = -1;
        try {
            Statement statement = databaseHandler.getDbConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
               id = resultSet.getInt(1);
            }
            databaseHandler.closeDbConnection();
        }catch(Exception e){e.printStackTrace();}
        return id;
    }
    public void deletePlane(int planeId) {
        try {
            String query1 = " DELETE FROM " + TablesConst.PASSENGER_PLANES_TABLE +
                    " WHERE " + PassengerPlanesTableConst.PLANE_ID + " = " + planeId;
            String query2 = " DELETE FROM " + TablesConst.TRANSPORT_PLANES_TABLE +
                    " WHERE " + TransportPlanesTableConst.PLANE_ID + " = " + planeId;
            String query3 = " DELETE FROM " + TablesConst.AIRCRAFT_TABLE +
                    " WHERE " + AircraftTableConst.PLANE_ID + " = " + planeId + ";";
            Statement statement = databaseHandler.getDbConnection().createStatement();
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            statement.executeUpdate(query3);
            databaseHandler.closeDbConnection();
        }catch(Exception e) {e.printStackTrace();}
    }
    public void deleteAirline(int AirlineId) {
       String query1 = "DELETE FROM " + TablesConst.PASSENGER_PLANES_TABLE + " WHERE " + PassengerPlanesTableConst.PLANE_ID + " IN " +
            "(SELECT " + AircraftTableConst.PLANE_ID + " FROM " + TablesConst.AIRCRAFT_TABLE +
               " WHERE " + AircraftTableConst.AIRLINE_ID + " = " + AirlineId + ")";
       String query2 = "DELETE FROM " + TablesConst.TRANSPORT_PLANES_TABLE + " WHERE " + TransportPlanesTableConst.PLANE_ID + " IN " +
               "(SELECT " + AircraftTableConst.PLANE_ID + " FROM " + TablesConst.AIRCRAFT_TABLE +
               " WHERE " + AircraftTableConst.AIRLINE_ID + " = " + AirlineId + ")";
       String query3 = " DELETE FROM " + TablesConst.AIRCRAFT_TABLE + " WHERE " + AircraftTableConst.AIRLINE_ID  + " = " + AirlineId;
       String query4 =" DELETE FROM " + TablesConst.AIRLINES_INFO_TABLE + " WHERE " + AirlinesInfoTableConst.AIRLINE_ID  + " = " + AirlineId;
       try{
       Statement statement = databaseHandler.getDbConnection().createStatement();
        statement.executeUpdate(query1);
        statement.executeUpdate(query2);
        statement.executeUpdate(query3);
        statement.executeUpdate(query4);
        databaseHandler.closeDbConnection();
        }catch(Exception e) {e.printStackTrace();}
    }
    public void setAirlineName(String oldName,String newName){
        String query = "UPDATE " + TablesConst.AIRLINES_INFO_TABLE + " SET " +
                AirlinesInfoTableConst.COMPANY_NAME + " = '" + newName + "' WHERE "
                + AirlinesInfoTableConst.COMPANY_NAME + " = '" + oldName + "'";
        try{
            Statement statement = databaseHandler.getDbConnection().createStatement();
            statement.executeUpdate(query);
            databaseHandler.closeDbConnection();
        }catch(Exception e) {e.printStackTrace();}
    }
}
