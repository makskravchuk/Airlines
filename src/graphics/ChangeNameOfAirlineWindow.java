package graphics;

import action.DatabaseOperations;

import javax.swing.*;
import java.util.List;

public class ChangeNameOfAirlineWindow extends Window{
    private JPanel panel;
    private JButton changeTheNameButton;
    private JTextField textField;
    private JComboBox comboBox;
    private JTextArea textArea;

    public ChangeNameOfAirlineWindow() {

        changeTheNameButton.addActionListener(e -> {
            try{
                DatabaseOperations databaseOperations = new DatabaseOperations();
                List<String> listOfName = databaseOperations.getAirlinesName();
                String oldName = comboBox.getSelectedItem().toString();
                String newName = textField.getText();
                if(newName.equals("")) {
                    textArea.setText("Enter a new company name.");
                    return;
                }
                for (String name : listOfName) {
                    if (name.equalsIgnoreCase(newName)){
                        textArea.setText("Airline with that name already exists.");
                        return;
                    }
                }
                databaseOperations.setAirlineName(oldName,newName);
                fillComboBox();
                textArea.setText("Name changed successfully.");

            }
            catch(Exception exception) {
                textArea.setText("Failed to change company name.");
            }
        });
    }
    private void fillComboBox(){
        comboBox.removeAllItems();
        DatabaseOperations databaseOperations = new DatabaseOperations();
        List<String> listOfName = databaseOperations.getAirlinesName();
        for(String name : listOfName) comboBox.addItem(name);
    }
    public void openWindow() {
        fillComboBox();
        JFrame jFrame = getFrame();
        jFrame.setContentPane(panel);
    }
    protected JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setBounds(dimension.width/2 - 200,dimension.height/2 - 150,400,300);
        jFrame.setResizable(false);
        jFrame.setIconImage(image);
        jFrame.setTitle("Editing company names");
        return jFrame;
    }
}
