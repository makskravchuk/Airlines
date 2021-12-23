package graphics;

import action.DatabaseOperations;
import action.PlaneFactory;
import aircraft.Plane;

import javax.swing.*;

public class CreateAircraftFromKeyboardWindow extends Window{
    private JPanel panel;
    private JTextField textFieldWingspan;
    private JTextField textFieldPlaneName;
    private JTextField textFieldLength;
    private JTextField textFieldHeight;
    private JTextField textFieldMaxWeight;
    private JTextField textFieldPlaneWeight;
    private JTextField textFieldMaxSpeed;
    private JTextField textFieldCruisingSpeed;
    private JTextField textFieldEngines;
    private JTextField textFieldFuelConsumption;
    private JTextField textFieldLoadCapacity;
    private JTextField textFieldCapacity;
    private JTextField textFieldFlightRange;
    private JButton createAircraftButton;
    private JTextArea textArea;
    private JLabel loadCapacity;
    private JLabel capacity;
    private String planeType;
    private int airlineId;
    public CreateAircraftFromKeyboardWindow() {
        createAircraftButton.addActionListener(e -> {
            DatabaseOperations databaseOperations = new DatabaseOperations();
            textArea.setText("");
            try {
                Plane plane = PlaneFactory.createPlaneFromKeyboard(planeType,CreateAircraftFromKeyboardWindow.this);
                databaseOperations.savePlane(plane,airlineId);
                textArea.setText("The plane was successfully created");
            }
            catch(Exception exception){textArea.setText("Failed to create plane.\n Check the entered data.");}
        });
    }
    public void setAirlineId(int airlineId){
        this.airlineId = airlineId;
    }
    public void setPlaneType(String planeType){
        this.planeType = planeType;
    }

    public JTextField getTextFieldFlightRange() {
        return textFieldFlightRange;
    }


    public JTextField getTextFieldWingspan() {
        return textFieldWingspan;
    }

    public JTextField getTextFieldPlaneName() {
        return textFieldPlaneName;
    }

    public JTextField getTextFieldLength() {
        return textFieldLength;
    }

    public JTextField getTextFieldHeight() {
        return textFieldHeight;
    }

    public JTextField getTextFieldMaxWeight() {
        return textFieldMaxWeight;
    }

    public JTextField getTextFieldPlaneWeight() {
        return textFieldPlaneWeight;
    }

    public JTextField getTextFieldMaxSpeed() {
        return textFieldMaxSpeed;
    }

    public JTextField getTextFieldCruisingSpeed() {
        return textFieldCruisingSpeed;
    }

    public JTextField getTextFieldEngines() {
        return textFieldEngines;
    }

    public JTextField getTextFieldFuelConsumption() {
        return textFieldFuelConsumption;
    }

    public JTextField getTextFieldLoadCapacity() {
        return textFieldLoadCapacity;
    }

    public JTextField getTextFieldCapacity() {
        return textFieldCapacity;
    }
    public void openWindow() {
        JFrame jFrame = getFrame();
        if(planeType.equals("transport")){
            textFieldCapacity.setVisible(false);
            capacity.setVisible(false);
        }
        else if(planeType.equals("passenger")){
            textFieldLoadCapacity.setVisible(false);
            loadCapacity.setVisible(false);
        }
        jFrame.setContentPane(panel);
    }
    protected JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setBounds(dimension.width/2 - 300,dimension.height/2 - 300,600,600);
        jFrame.setResizable(false);
        jFrame.setIconImage(image);
        jFrame.setTitle("Create aircraft from keyboard");
        return jFrame;
    }
}
