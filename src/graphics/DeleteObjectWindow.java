package graphics;

import action.DatabaseOperations;

import javax.swing.*;
import java.util.List;

public class DeleteObjectWindow extends Window{
    private JPanel panel;
    private JComboBox comboBox;
    private JTextField textField;
    private JButton removeThePlaneButton;
    private JButton deleteAirlineButton;
    private JTextArea textArea;

    public DeleteObjectWindow() {
        deleteAirlineButton.addActionListener(e -> {
            try {
                DatabaseOperations databaseOperations = new DatabaseOperations();
                int id = databaseOperations.getAirlineId(comboBox.getSelectedItem().toString());
                if(id == -1) throw new Exception();
                databaseOperations.deleteAirline(id);
                fillComboBox();
                textArea.setText("Removal successful.");
            }
            catch(Exception exception) { textArea.setText("Failed to delete airline.");}
        });
        removeThePlaneButton.addActionListener(e -> {
            try {
                DatabaseOperations databaseOperations = new DatabaseOperations();
                int id = Integer.parseInt(textField.getText());
                databaseOperations.deletePlane(id);
                textArea.setText("Removal successful.");
            }
            catch(Exception exception) {textArea.setText("Failed to delete plane.");}
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
        jFrame.setBounds(dimension.width/2 - 250,dimension.height/2 - 150,500,300);
        jFrame.setResizable(false);
        jFrame.setIconImage(image);
        jFrame.setTitle("Delete Object");
        return jFrame;
    }
}
