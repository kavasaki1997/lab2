package studenttable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class AddDialog {

    private static final String LAST_NAME = "Last Name";
    private static final String FIRST_NAME = "First Name";
    private static final String MIDDLE_NAME = "Middle Name";
    private static final String GROUP = "Group";
    private StudentTableWithPaging studentTableWithPaging;
    private TableModel tableModel;
    private Map<String, JTextField> fieldID = new HashMap<String, JTextField>();
    private Map<JTextField, JComboBox> opshchRabotaMap = new HashMap<JTextField, JComboBox>();

    public AddDialog(StudentTableWithPaging studentTableWithPaging) {
        this.studentTableWithPaging = studentTableWithPaging;
        tableModel = studentTableWithPaging.getTableModel();

        JFrame frame = createFrame();
        frame.pack();
        frame.setLocationRelativeTo(studentTableWithPaging);
        /*frame.setResizable(false);*/
        frame.setVisible(true);
        frame.pack();
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Add student");
        JPanel jPanelID = new JPanel();
        jPanelID.setLayout(new GridBagLayout());
        JLabel labelText = new JLabel("Add new student");
        labelText.setHorizontalAlignment(JLabel.CENTER);
        AddComponent.add(jPanelID, labelText, 0, 0, 2, 1);
        String[] labelString = {LAST_NAME, FIRST_NAME, MIDDLE_NAME, GROUP};
        for (int field = 0; field < labelString.length; field++) {
            labelText = new JLabel(labelString[field]);
            AddComponent.add(jPanelID, labelText, 0, field + 1, 1, 1);
            JTextField jtfField = new JTextField(30);
            fieldID.put(labelString[field], jtfField);
            AddComponent.add(jPanelID, jtfField, 1, field + 1, 1, 1);
        }
        labelText = new JLabel("Obshch rab");
        labelText.setHorizontalAlignment(JLabel.CENTER);
        AddComponent.add(jPanelID, labelText, 0, 5, 2, 1);
        labelText = new JLabel("Vid");
        labelText.setHorizontalAlignment(JLabel.CENTER);
        AddComponent.add(jPanelID, labelText, 0, 6, 1, 1);
        labelText = new JLabel("Kollichestvo");
        labelText.setHorizontalAlignment(JLabel.CENTER);
        AddComponent.add(jPanelID, labelText, 1, 6, 1, 1);
        String[] markString = {"-", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        for (int exam = 0; exam < 10; exam++) {
            JTextField jtfName = new JTextField(30);
            JComboBox jcbMark = new JComboBox(markString);
             opshchRabotaMap.put(jtfName, jcbMark);
            AddComponent.add(jPanelID, jtfName, 0, exam + 7, 1, 1);
            AddComponent.add(jPanelID, jcbMark, 1, exam + 7, 2, 1);
        }
        frame.add(jPanelID, BorderLayout.NORTH);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNewStudent();
            }
        });
        frame.add(okButton, BorderLayout.SOUTH);
        return frame;
    }

    private void createNewStudent() {
        if (!isAllCorrect()) {
            JOptionPane.showMessageDialog
                    (null, "Not correct student information", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            List<OpshchRab> opshchRabs = new ArrayList<>();
            for (Map.Entry exam :  opshchRabotaMap.entrySet()) {
                JTextField name = (JTextField) exam.getKey();
                JComboBox mark = (JComboBox) exam.getValue();
                if (!name.getText().equals("")) {
                    opshchRabs.add(new OpshchRab(name.getText(),
                            Integer.parseInt((String) mark.getSelectedItem())));
                }
            }
            tableModel.getStudents().add(new Student(getTextID(LAST_NAME),
                    getTextID(FIRST_NAME),
                    getTextID(MIDDLE_NAME),
                    getTextID(GROUP),
                    opshchRabs));
            studentTableWithPaging.updateComponent();
        }
    }

    private boolean isAllCorrect() {
        return (!(isNotCorrectID(LAST_NAME) || isNotCorrectID(FIRST_NAME) || isNotCorrectID(MIDDLE_NAME) ||
                isNotCorrectGroup() || isNotCorrectExaminations()));
    }

    private boolean isNotCorrectGroup() {
        Pattern p = Pattern.compile("[0-9]+");
        return !p.matcher(fieldID.get(GROUP).getText()).matches();
    }

    private String getTextID(String key) {
        return fieldID.get(key).getText();
    }

    private boolean isNotCorrectID(String key) {
        return ((fieldID.get(key).getText().equals("")) ||
                (fieldID.get(key).getText().length() > 0 && fieldID.get(key).getText().charAt(0) == ' '));
    }

    private boolean isNotCorrectExaminations() {
        for (Map.Entry exam :  opshchRabotaMap.entrySet()) {
            JTextField name = (JTextField) exam.getKey();
            JComboBox mark = (JComboBox) exam.getValue();
            if (isNotCorrectExamination(name.getText(), (String) mark.getSelectedItem())) return true;
        }
        return false;
    }

    private boolean isNotCorrectExamination(String name, String mark) {
        return ((name.equals("") && !mark.equals("-")) ||
                (mark.equals("-") && !name.equals("")) ||
                (name.length() > 0 && name.charAt(0) == ' '));
    }

}
