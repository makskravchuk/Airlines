package action;

import aircraft.PassengerPlane;
import aircraft.Plane;
import aircraft.TransportPlane;
import graphics.CreateAircraftFromKeyboardWindow;
import graphics.CreateReadyAircraftModelWindow;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class PlaneFactory {
    private static final String fileWithPlanes = "resources\\PlanesModel_data.txt";
    private static String findPlaneInFileByName(String name, String planeType) throws IOException {
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileWithPlanes)));
        while (fileReader.ready()) {
            String buffer = fileReader.readLine();
            String[] fields = buffer.split(";");
            if (fields[0].equals(planeType) && fields[1].equals(name)) {
                fileReader.close();
                return buffer;
            }
        }
        return null;
    }
    private static Plane getPlaneFromString(String data) {
        String[] result = data.split(";");
        try {
            String planeType = result[0];  //тип
            String planeName = result[1];  //назва літака
            double wingspan = Double.parseDouble(result[2]);   //розмах крила
            double length = Double.parseDouble(result[3]);     //довжина
            double height = Double.parseDouble(result[4]);      //висота
            int maxTakeoffWeight = Integer.parseInt(result[5]); //максимальна злітна маса
            int planeWeight = Integer.parseInt(result[6]);       //маса літака
            int maxSpeed = Integer.parseInt(result[7]);        //максимальна швидкість
            int cruisingSpeed = Integer.parseInt(result[8]);   //крейсерська швидкість
            int flightRange = Integer.parseInt(result[9]);    //дальність польоту
            String engines = result[10];          //двигуни
            int fuelConsumption = Integer.parseInt(result[11]);   //розхід пального
            switch (planeType) {
                case "transport":
                    return new TransportPlane(0,planeName, wingspan, length, height, maxTakeoffWeight, planeWeight,
                            maxSpeed, cruisingSpeed, flightRange, engines, fuelConsumption, Integer.parseInt(result[12]));
                case "passenger":
                    return new PassengerPlane(0,planeName, wingspan, length, height, maxTakeoffWeight, planeWeight,
                            maxSpeed, cruisingSpeed, flightRange, engines, fuelConsumption, Integer.parseInt(result[12]));
            }
        }
        catch(Exception e){ System.out.println("Error.");}
        return null;
    }
    public static void setListOfAircraftModels(CreateReadyAircraftModelWindow window,String planeType) {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileWithPlanes)));
            while (fileReader.ready()) {
                String[] info = fileReader.readLine().split(";");
                if(info[0].equals(planeType)) {
                    window.getComboBox().addItem(info[1]);
                }
            }
        }
        catch(Exception e){ e.printStackTrace();}
    }
    public static Plane createPlaneFromFile(String planeType,String planeName) throws IOException{
        return getPlaneFromString(findPlaneInFileByName(planeName, planeType));
    }
    public static Plane createPlaneFromKeyboard(String type,CreateAircraftFromKeyboardWindow createAircraftFromKeyboardWindow){

        String planeName = createAircraftFromKeyboardWindow.getTextFieldPlaneName().getText();  //назва літака
        double wingspan = Double.parseDouble(createAircraftFromKeyboardWindow.getTextFieldWingspan().getText());   //розмах крила
        double length = Double.parseDouble(createAircraftFromKeyboardWindow.getTextFieldLength().getText());     //довжина
        double height = Double.parseDouble(createAircraftFromKeyboardWindow.getTextFieldHeight().getText());      //висота
        int maxTakeoffWeight = Integer.parseInt(createAircraftFromKeyboardWindow.getTextFieldMaxWeight().getText()); //максимальна злітна маса
        int planeWeight = Integer.parseInt(createAircraftFromKeyboardWindow.getTextFieldPlaneWeight().getText());       //маса літака
        int maxSpeed = Integer.parseInt(createAircraftFromKeyboardWindow.getTextFieldMaxSpeed().getText());        //максимальна швидкість
        int cruisingSpeed = Integer.parseInt(createAircraftFromKeyboardWindow.getTextFieldCruisingSpeed().getText());   //крейсерська швидкість
        int flightRange = Integer.parseInt(createAircraftFromKeyboardWindow.getTextFieldFlightRange().getText());    //дальність польоту
        String engines = createAircraftFromKeyboardWindow.getTextFieldEngines().getText();          //двигуни
        int fuelConsumption = Integer.parseInt(createAircraftFromKeyboardWindow.getTextFieldFuelConsumption().getText());   //розхід пального
        switch (type) {
            case "transport" -> {
                int LoadCapacity = Integer.parseInt(createAircraftFromKeyboardWindow.getTextFieldLoadCapacity().getText());
                return new TransportPlane(0,planeName, wingspan, length, height, maxTakeoffWeight, planeWeight,
                        maxSpeed, cruisingSpeed, flightRange, engines, fuelConsumption,LoadCapacity);
            }
            case "passenger" -> {
                int Capacity = Integer.parseInt(createAircraftFromKeyboardWindow.getTextFieldCapacity().getText());
                return new PassengerPlane(0,planeName, wingspan, length, height, maxTakeoffWeight, planeWeight,
                        maxSpeed, cruisingSpeed, flightRange, engines, fuelConsumption, Capacity);
            }
        }
        return null;
    }
}
