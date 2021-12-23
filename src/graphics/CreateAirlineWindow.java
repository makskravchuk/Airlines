package graphics;

import action.AirlineFactory;
import action.DatabaseOperations;
import airline.Airline;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class CreateAirlineWindow extends Window{
    private JTextField textField;
    private JButton createAirlineButton;
    private JTextArea textArea;
    private JPanel panel;
    private JCheckBox answerCheckBox;

    public CreateAirlineWindow() {
        createAirlineButton.addActionListener(e -> {
            try {
                DatabaseOperations databaseOperations = new DatabaseOperations();
                List<String> listOfName = databaseOperations.getAirlinesName();
                String airlineName = textField.getText();
                if(airlineName.equals(""))  {
                    textArea.setText("Name not entered.");
                    return;
                }
                for (String name : listOfName) {
                    if (name.equalsIgnoreCase(airlineName)){
                        textArea.setText("Airline with that name already exists.");
                        return;
                    }
                }
                Airline airline = AirlineFactory.createAirline(airlineName);
                databaseOperations.saveAirline(airline);
                if(answerCheckBox.isSelected()){
                    List<String> name = new ArrayList<>();
                    name.add(airline.getCompanyName());
                    AddPlaneWindow addPlaneWindow = new AddPlaneWindow();
                    addPlaneWindow.fillComboBoxNames(name);
                    addPlaneWindow.openWindow();
                }
                textArea.setText("Airline successfully established.");
            }
            catch(Exception exception){textArea.setText("Failed to create an airline.");}
        });
    }

    public void openWindow() {
        JFrame jFrame = getFrame();
        jFrame.setContentPane(panel);
    }
    protected JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setBounds(dimension.width/2 - 200,dimension.height/2 - 200,400,400);
        jFrame.setResizable(false);
        jFrame.setIconImage(image);
        jFrame.setTitle("Create airline");
        return jFrame;
    }
}
