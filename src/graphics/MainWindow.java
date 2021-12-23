package graphics;

import action.DatabaseOperations;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainWindow extends Window{
    private JPanel panel;
    private JLabel picture;
    private JButton informationManagementButton;
    private JButton createAirlineButton;
    private JButton deleteObjectButton;
    private JButton addPlaneButton;
    private JButton changeAirlineNameButton;

    public MainWindow(){
        createAirlineButton.addActionListener(e -> {
           CreateAirlineWindow createAirlineWindow = new CreateAirlineWindow();
           createAirlineWindow.openWindow();
        });
        addPlaneButton.addActionListener(e -> {
            DatabaseOperations databaseOperations = new DatabaseOperations();
            List<String> listOfNames = databaseOperations.getAirlinesName();
            AddPlaneWindow addPlaneWindow = new AddPlaneWindow();
            addPlaneWindow.fillComboBoxNames(listOfNames);
            addPlaneWindow.openWindow();
        });
        deleteObjectButton.addActionListener(e -> {
            DeleteObjectWindow deleteObjectWindow = new DeleteObjectWindow();
            deleteObjectWindow.openWindow();
        });
        changeAirlineNameButton.addActionListener(e -> {
            ChangeNameOfAirlineWindow changeNameOfAirline = new ChangeNameOfAirlineWindow();
            changeNameOfAirline.openWindow();
        });
        informationManagementButton.addActionListener(e -> {
           InformationManagementWindow infoManipulationMenu = new InformationManagementWindow();
           infoManipulationMenu.openWindow();
        });

    }
    public void openWindow() {
        JFrame jFrame = getFrame();
        jFrame.add(panel);
        picture.setIcon(new ImageIcon(new ImageIcon("resources\\picture.png").getImage().getScaledInstance
                (1351,801, Image.SCALE_SMOOTH)));
    }

    protected JFrame getFrame() {
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
        jFrame.setResizable(false);
        jFrame.setIconImage(image);
        jFrame.setTitle("Airlines");
        return jFrame;
    }
}
