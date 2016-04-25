package studenttable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {

    private FileHandler fileHandler;
    private StudentTableWithPaging studentTableWithPaging;
    private JFrame frame;

    public MainWindow() {
        frame = new JFrame("Student Table");
        frame.setSize(850, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(createFileMenu());

        studentTableWithPaging = new StudentTableWithPaging();
        fileHandler = new FileHandler(this);
        frame.add(studentTableWithPaging, BorderLayout.CENTER);
        frame.setMinimumSize(new Dimension(1200, 350));
        frame.setVisible(true);
    }

    private JMenuBar createFileMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openFile = new JMenuItem("Open");
        openFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileHandler.openFile();
            }
        });
        fileMenu.add(openFile);
        JMenuItem saveFile = new JMenuItem("Save");
        saveFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileHandler.saveFile();
            }
        });
        fileMenu.add(saveFile);

        JMenuItem addDialog = new JMenuItem("Add");
        addDialog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                new AddDialog(studentTableWithPaging);
            }
        });
        fileMenu.add(addDialog);

        JMenuItem searchDialog = new JMenuItem("Search");
        searchDialog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                new SearchDialog(MainWindow.this, "REMOVE_MODE");
            }
        });
        fileMenu.add(searchDialog);

        fileMenu.addSeparator();
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exit);

        menuBar.add(fileMenu);
        return menuBar;
    }


    public StudentTableWithPaging getStudentTableWithPaging() {
        return studentTableWithPaging;
    }



    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        new MainWindow();
    }

}
