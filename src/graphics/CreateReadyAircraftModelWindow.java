package graphics;

import action.DatabaseOperations;
import action.PlaneFactory;
import aircraft.Plane;

import javax.swing.*;

public class CreateReadyAircraftModelWindow extends Window{
    private JPanel panel;
    private JButton createAircraftButton;
    private JTextArea textArea;
    private JComboBox comboBox;
    private JLabel helpMessage;
    private String planeType;
    private int airlineId;

    public JComboBox getComboBox() {
        return comboBox;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    public void setAirlineId(int airlineId) {
        this.airlineId = airlineId;
    }

    public CreateReadyAircraftModelWindow() {
        createAircraftButton.addActionListener(e -> {
            DatabaseOperations databaseOperations = new DatabaseOperations();
            textArea.setText("");
            try {
                String planeName = comboBox.getSelectedItem().toString();
                Plane plane = PlaneFactory.createPlaneFromFile(planeType,planeName);
                databaseOperations.savePlane(plane,airlineId);
                textArea.setText("The plane was successfully created");
            }
            catch(Exception exception){textArea.setText("Failed to create plane.");}
        });
    }

    public void openWindow() {
        PlaneFactory.setListOfAircraftModels(this,planeType);
        helpMessage.setText("Choose a " + planeType + " plane:");
        JFrame jFrame = getFrame();
        jFrame.setContentPane(panel);
    }
    protected JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setBounds(dimension.width/2 - 250,dimension.height/2 - 250,500,500);
        jFrame.setResizable(false);
        jFrame.setIconImage(image);
        jFrame.setTitle("Create a ready-made model of the aircraft");
        return jFrame;
    }
}
