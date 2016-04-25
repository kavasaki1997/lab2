package studenttable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.regex.Pattern;


public class SearchDialog {

    private StudentTableWithPaging studentTableWithPaging;
    private TableModel tableModel;
    private String mode;
    private JTextField lastName;
    private JTextField group;
    private JTextField vid;
    private JComboBox minMiddleMark;
    private JComboBox maxMiddleMark;
      private JFrame frame;
    private StudentTableWithPaging searchPanel;

    public SearchDialog(MainWindow mainWindow, String mode) {
        this.studentTableWithPaging = mainWindow.getStudentTableWithPaging();
        this.mode = mode;
        tableModel = studentTableWithPaging.getTableModel();
        frame = createFrame();
        frame.pack();
        frame.setLocationRelativeTo(studentTableWithPaging);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private JFrame createFrame(){
        JFrame frame;
        JLabel labelText;
        if (mode.equals("SEARCH_MODE")) {
            frame = new JFrame("Search student");
            labelText = new JLabel("Search student");
        }
        else {
            frame = new JFrame("Remove student");
            labelText = new JLabel("Remove Student");
        }
        JPanel jPanelID = new JPanel();
        jPanelID.setLayout(new GridBagLayout());
        labelText.setHorizontalAlignment(JLabel.CENTER);
        AddComponent.add(jPanelID,labelText, 0, 0, 3, 1);
        String[] labelString = {"Last Name:", "Group:", "Vid:"};
        labelText = new JLabel(labelString[0]);
        AddComponent.add(jPanelID,labelText, 0, 1, 1, 1);
        lastName = new JTextField(30);
        AddComponent.add(jPanelID, lastName, 1, 1, 3, 1);
        labelText = new JLabel(labelString[1]);
        AddComponent.add(jPanelID, labelText, 0, 2, 1, 1);
        group = new JTextField(30);
        AddComponent.add(jPanelID, group, 1, 2, 3, 1);

        vid = new JTextField(30);
        AddComponent.add(jPanelID, vid, 1, 4, 3, 1);
        labelText = new JLabel(labelString[2]);
        AddComponent.add(jPanelID, labelText, 0, 4, 1, 1);

        String[] markString = {"-","1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        labelText = new JLabel("Middle mark(less/great)");
        labelText.setHorizontalAlignment(JLabel.CENTER);
        AddComponent.add(jPanelID,labelText, 0, 3, 1, 1);
        minMiddleMark = new JComboBox(markString);
        AddComponent.add(jPanelID, minMiddleMark, 1, 3, 1, 1);
        maxMiddleMark = new JComboBox(markString);
        AddComponent.add(jPanelID, maxMiddleMark, 2, 3, 1, 1);
        frame.add(jPanelID, BorderLayout.NORTH);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clickButton();
            }
        });
        frame.add(okButton, BorderLayout.SOUTH);
        return frame;
    }

    private void clickButton() {
        if (mode.equals("SEARCH_MODE")) searchStudent();
        else removeStudent();
    }

    private void searchStudent(){
        if (isAllCorrect()){
            if (searchPanel != null) frame.remove(searchPanel);
            searchPanel = new StudentTableWithPaging();
            searchPanel.getTableModel().setNumberSemestr(studentTableWithPaging.getTableModel().getNumberSemestr());
            for (Student student: tableModel.getStudents()) {
                if (compliesTemplate(student)) {
                    searchPanel.getTableModel().getStudents().add(student);
                }
            }
            searchPanel.updateComponent();
            frame.add(searchPanel, BorderLayout.CENTER);
            frame.setSize(new Dimension(850,350));
            frame.revalidate();
            frame.repaint();
        } else {
            JOptionPane.showMessageDialog
                    (null, "Not correct student information", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeStudent(){
        if (isAllCorrect()){
            int counterStudent = 0;
            Iterator<Student> itr = tableModel.getStudents().iterator();
            while (itr.hasNext()) {
                Student student = itr.next();
                if (compliesTemplate(student)) {
                    itr.remove();
                    counterStudent++;
                }
            }
            tableModel.setMaxNumberSemestr();
            studentTableWithPaging.updateComponent();
            if (counterStudent > 0) {
                JOptionPane.showMessageDialog
                        (null, "Delete " + counterStudent + " student", "INFO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog
                        (null, "Student not find", "WARNING", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog
                    (null, "Not correct student information", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean compliesTemplate(Student student) {
        if (!lastName.getText().equals("") && !lastName.getText().equals(student.getLastName())) return false;
        if (!group.getText().equals("") && !group.getText().equals(student.getNumberGroup())) return false;
        if (!getStudentMarkNull(minMiddleMark) && !isaCompliesMiddleMark(student)) return false;

        return true;
    }

    private boolean isaCompliesMiddleMark(Student student) {
        return  getStudentMarkInt(minMiddleMark) <= student.getMiddleMark() &&
                getStudentMarkInt(maxMiddleMark) >= student.getMiddleMark();
    }



    private boolean isAllCorrect() {
        return (!(isNotCorrectLastName() || isNotCorrectGroup() ||
                isNotCorrectMark(minMiddleMark, maxMiddleMark)));
    }

    private boolean isNotCorrectGroup() {
        Pattern p = Pattern.compile("[0-9]+");
        return (!p.matcher(group.getText()).matches() ^ group.getText().equals(""));
    }

    private boolean isNotCorrectLastName() {
        return (lastName.getText().length() > 0 && lastName.getText().charAt(0) == ' ');
    }

    private boolean isNotCorrectMark(JComboBox min, JComboBox max) {
        return ((getStudentMarkNull(min) && !getStudentMarkNull(max)) ||
                (getStudentMarkNull(max) && !getStudentMarkNull(min)) ||
                (getStudentMarkInt(min) > getStudentMarkInt(max)));
    }

    private boolean getStudentMarkNull(JComboBox markBox){
        return markBox.getSelectedItem().equals("-");
    }

    private int getStudentMarkInt(JComboBox markBox){
        if (getStudentMarkNull(markBox)) return 0;
        return Integer.parseInt((String)markBox.getSelectedItem());
    }

}
