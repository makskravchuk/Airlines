package graphics;

import action.DatabaseOperations;
import action.PlanesInfoOperations;
import aircraft.Plane;
import airline.Airline;

import javax.swing.*;
import java.util.List;

public class InformationManagementWindow extends Window{
    private JPanel panel;
    private JButton passengerPlanesInformationButton;
    private JButton airlinesInformationButton;
    private JButton aircraftInformationButton;
    private JButton transportPlanesInformationButton;
    private JComboBox comboBoxAirlines;
    private JTextPane textPane;
    private JTextArea message;
    private JButton sortPlanesButton;
    private JButton calculateTotalCapacityButton;
    private JButton calculateTotalLoadCapacityButton;
    private JButton findPlanesByParameterRange;
    private JButton findPlanesByParameterValue;
    private JComboBox comboBoxParameters;
    private JComboBox comboBoxSortType;
    private JTextField textFieldValue;
    private JTextField textFieldMax;
    private JTextField textFieldMin;

    public InformationManagementWindow(){
        fillComboBoxes();
        airlinesInformationButton.addActionListener(e -> {
            DatabaseOperations databaseOperations = new DatabaseOperations();
            List<Airline> listOfAirline = databaseOperations.downloadAirlines();
            if(listOfAirline.isEmpty()) {
                textPane.setText("There are no airlines.");
                return;
            }
            textPane.setText("Information successfully received.");
            StringBuilder builder = new StringBuilder();
            builder.append("|\t[Company name]\t|\t\t[List of aircraft]\n");
            builder.append("-".repeat(320));
            builder.append("\n");
            for(Airline airline : listOfAirline){
                builder.append("|\t");
                builder.append(airline);
                builder.append("\t\t|\t");
                int count = 0;
                for(Plane plane : airline.getListOfPlane()){
                    if(count == 5) {
                        builder.append("\n|\t\t\t|\t");
                        count = 0;
                    }
                    builder.append(plane.getPlaneName());
                    builder.append("; ");
                    count++;
                }
                builder.append("\n");
                builder.append("-".repeat(320));
                builder.append("\n");
            }
            message.setText(builder.toString());
        });
        aircraftInformationButton.addActionListener(e -> {
            Airline airline = getAirline();
            printAircraftList(airline.getListOfPlane());
            textPane.setText("Information successfully received.");
        });
        transportPlanesInformationButton.addActionListener(e -> {
            try {
                Airline airline = getAirline();
                List<Plane> transportPlanes = airline.getTransportPlanes();
                printAircraftList(transportPlanes);
                textPane.setText("Information successfully received.");
            }
            catch (Exception exception){
                textPane.setText("Error.");
            }
        });
        passengerPlanesInformationButton.addActionListener(e -> {
            try {
                Airline airline = getAirline();
                List<Plane> passengerPlanes = airline.getPassengerPlanes();
               printAircraftList(passengerPlanes);
                textPane.setText("Information successfully received.");
            }
            catch (Exception exception){
                textPane.setText("Error.");
            }
        });
        sortPlanesButton.addActionListener(e -> {
                  boolean type = getSortType();
                  String parameter = getParameter();
                  Airline airline = getAirline();
                  PlanesInfoOperations.sortPlanes(airline.getListOfPlane(),parameter,type);
                  textPane.setText("List successfully sorted.");
                  printAircraftList(airline.getListOfPlane());
        });
        findPlanesByParameterValue.addActionListener(e -> {
            String parameter = getParameter();
            String value = textFieldValue.getText();
            if(value.equals("")){
                textPane.setText("Parameter value not specified");
                return;
            }
            Airline airline = getAirline();
           try {
               List<Plane> listOfPlane = PlanesInfoOperations.findPlanesByParameter(airline.getListOfPlane(), parameter, value);
               printAircraftList(listOfPlane);
               if(listOfPlane.isEmpty()) textPane.setText("Planes not found");
               else textPane.setText("Search successful");
           }
           catch(Exception exception){ textPane.setText("Error.");}
        });
        findPlanesByParameterRange.addActionListener(e -> {
            try{
            String parameter = getParameter();
            String sMin = textFieldMin.getText();
            String sMax = textFieldMax.getText();
                if(sMin.equals("") || sMax.equals("")){
                    textPane.setText("Parameter min or max not specified");
                    return;
                }
            double min = Double.parseDouble(sMin);
            double max = Double.parseDouble(sMax);
            Airline airline = getAirline();
                List<Plane> listOfPlane = PlanesInfoOperations.findPlanesByParameterRange(airline.getListOfPlane(), parameter, min,max);
                printAircraftList(listOfPlane);
                if(listOfPlane.isEmpty()) textPane.setText("Planes not found");
                else textPane.setText("Search successful");
            }
            catch(Exception exception){ textPane.setText("Error.");}
        });
        calculateTotalLoadCapacityButton.addActionListener(e -> {
            Airline airline = getAirline();
            int sum = PlanesInfoOperations.calculateTotalLoadCapacity(airline.getListOfPlane());
            String text = "The total carrying capacity of all aircraft of the airline: " + sum + " tons.";
            message.setText(text);
            textPane.setText("Information successfully received.");
        });
        calculateTotalCapacityButton.addActionListener(e -> {
            Airline airline = getAirline();
            int sum = PlanesInfoOperations.calculateTotalCapacity(airline.getListOfPlane());
            String text = "The total capacity of all aircraft of the airline: " + sum + " seats.";
            message.setText(text);
            textPane.setText("Information successfully received.");
        });
    }
    private void printAircraftList(List<Plane> planes){
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("\t\t\t\t\t[ List of aircraft ]\n");
            builder.append("-".repeat(420));
            builder.append("\n");
            for(Plane plane : planes){
                builder.append(plane);
                builder.append("-".repeat(420));
                builder.append("\n");
            }
            message.setText(builder.toString());
        }
        catch (Exception exception){
            textPane.setText("Error.");
        }
    }
    private Airline getAirline(){
        DatabaseOperations databaseOperations = new DatabaseOperations();
        String companyName = comboBoxAirlines.getSelectedItem().toString();
        return databaseOperations.downloadAirline(companyName);
    }
    private boolean getSortType(){
        String type = comboBoxSortType.getSelectedItem().toString();
        if(type.equals("sort ascending")) return true;
        else if(type.equals("sort descending")) return false;
        return true;
    }
    private String getParameter(){
        return comboBoxParameters.getSelectedItem().toString();
    }
    private void fillComboBoxes(){
        DatabaseOperations databaseOperations = new DatabaseOperations();
        List<String> companyNames = databaseOperations.getAirlinesName();
        if (companyNames.isEmpty()) textPane.setText("There are no airlines.");
        for(String name : companyNames) comboBoxAirlines.addItem(name);
        for(String parameter : PlanesInfoOperations.planeParameters()) comboBoxParameters.addItem(parameter);
        comboBoxSortType.addItem("sort ascending");
        comboBoxSortType.addItem("sort descending");
    }
    public void openWindow() {
        JFrame jFrame = getFrame();
        jFrame.setContentPane(panel);
    }
    protected JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setBounds(dimension.width/2 - 550,dimension.height/2 - 420,1100,800);
        jFrame.setResizable(true);
        jFrame.setIconImage(image);
        jFrame.setTitle("Information management");
        return jFrame;
    }
}
