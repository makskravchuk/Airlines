package graphics;

import action.DatabaseOperations;

import javax.swing.*;
import java.util.List;

public class AddPlaneWindow extends Window{
    private JButton createAnAirplaneButton;
    private JComboBox comboBoxNames;
    private JComboBox comboBoxTypes;
    private JRadioButton readyModelFromTheRadioButton;
    private JRadioButton enterDataFromTheRadioButton;
    private JTextArea textArea;
    private javax.swing.JPanel panel;


    public AddPlaneWindow() {
        createAnAirplaneButton.addActionListener(e -> {
            try {
                DatabaseOperations databaseOperations = new DatabaseOperations();
                String planeType = comboBoxTypes.getSelectedItem().toString();
                int airlineId = databaseOperations.getAirlineId(comboBoxNames.getSelectedItem().toString());
                if(readyModelFromTheRadioButton.isSelected()){
                     CreateReadyAircraftModelWindow createReadyAircraftModelWindow = new CreateReadyAircraftModelWindow();
                     createReadyAircraftModelWindow.setAirlineId(airlineId);
                     createReadyAircraftModelWindow.setPlaneType(planeType);
                     createReadyAircraftModelWindow.openWindow();
                }
                else if(enterDataFromTheRadioButton.isSelected()){
                    CreateAircraftFromKeyboardWindow createAircraftFromKeyboardWindow = new CreateAircraftFromKeyboardWindow();
                    createAircraftFromKeyboardWindow.setPlaneType(planeType);
                    createAircraftFromKeyboardWindow.setAirlineId(airlineId);
                    createAircraftFromKeyboardWindow.openWindow();
                }
                else textArea.setText("Select type of creation.");
            }
            catch(Exception exception) { textArea.setText("Failed to add plane.");}
        });
    }

    private void fillComboBoxTypes(){
        comboBoxTypes.addItem("transport");
        comboBoxTypes.addItem("passenger");
    }
    public void fillComboBoxNames(List<String> listOfName){
        for(String name : listOfName) comboBoxNames.addItem(name);
    }
    public void openWindow() {
        fillComboBoxTypes();
        ButtonGroup group = new ButtonGroup();
        group.add(readyModelFromTheRadioButton);
        group.add(enterDataFromTheRadioButton);
        JFrame jFrame = getFrame();
        jFrame.setContentPane(panel);
    }
    protected JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setBounds(dimension.width/2 - 300,dimension.height/2 - 200,600,400);
        jFrame.setResizable(false);
        jFrame.setIconImage(image);
        jFrame.setTitle("Add plane");
        return jFrame;
    }
}
