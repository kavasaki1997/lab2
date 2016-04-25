package studenttable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Arrays;
import java.util.List;


public class StudentTableWithPaging extends JComponent {

    private TableModel tableModel;
    private JScrollPane scrollTable;
    private int currentPage = 1;
    private int studentOnPage = 10;
    private int heightTable;

    public StudentTableWithPaging() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        tableModel = new TableModel();
        heightTable = 250;
        makePanel();
    }

    public void makePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(makeTable(), BorderLayout.NORTH);
        scrollTable = new JScrollPane(tablePanel);
        scrollTable.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent evt) {
                updateScrollTable();
            }
        });
        add(scrollTable);
        add(makeToolsPanel());
    }

    private JPanel makeTable() {
        JPanel table = new JPanel();
        table.setLayout(new GridBagLayout());

        List<Student> students = tableModel.getStudents();
        AddComponent.add(table, "Full Name", 0, 0, 1, 3);
        AddComponent.add(table, "Group", 1, 0, 1, 3);
        AddComponent.add(table, "opshch rabota", 2, 0, 10 * 2, 1);
        for (int i = 0, x = 2; i < 10; i++, x += 2) {
            AddComponent.add(table, Integer.toString(i + 1), x, 1, 2, 1);
            AddComponent.add(table, "vid", x, 2, 1, 1);
            AddComponent.add(table, "kolichestvo", x + 1, 2, 1, 1);
        }
        int firstStudentOnPage = studentOnPage * (currentPage - 1);
        int lineInHeaderTable = 3;
        for (int y = lineInHeaderTable, student = firstStudentOnPage;
             y < studentOnPage + lineInHeaderTable && student < students.size();
             y++, student++) {
            tableModel.setMaxNumberSemestr(students.get(student).getExaminations().size());
            for (int i = 0; i < 10 * 2 + 2; i++) {
                String write = getFieldForStudent(students.get(student), i);
                AddComponent.add(table, write, i, y, 1, 1);
            }
        }
        return table;
    }

    private JPanel makeToolsPanel() {
        int numberExaminations = tableModel.getNumberSemestr();
        List<Student> students = tableModel.getStudents();
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        String statusBar = "Page " + currentPage + "/" + getNumberMaxPage()
                + " Total records: " + students.size() + " ";
        panel.add(new JLabel(statusBar));
        JButton firstButton = new JButton("First");
        panel.add(firstButton);
        firstButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 1) {
                    currentPage = 1;
                    updateComponent();
                }
            }
        });
        JButton prevButton = new JButton("Prev");
        panel.add(prevButton);
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage > 1) {
                    currentPage--;
                    updateComponent();
                }
            }
        });
        JButton nextButton = new JButton("Next");
        panel.add(nextButton);
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableModel.getStudents().size() > studentOnPage * (currentPage - 1) + studentOnPage) {
                    currentPage++;
                    updateComponent();
                }
            }
        });
        JButton lastButton = new JButton("Last");
        panel.add(lastButton);
        lastButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPage != getNumberMaxPage()) {
                    currentPage = getNumberMaxPage();
                    updateComponent();
                }
            }
        });
        JLabel label = new JLabel(" Student on page: ");
        panel.add(label);
        String[] sizeStudent = {"10", "20", "30", "50", "100"};
        JComboBox sizeBox = new JComboBox(sizeStudent);
        sizeBox.setSelectedIndex(Arrays.asList(sizeStudent).indexOf(Integer.toString(studentOnPage)));
        sizeBox.setMaximumSize(sizeBox.getPreferredSize());
        sizeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeStudentOnPage(e);
            }
        });
        panel.add(sizeBox);
        panel.add(label);
        panel.setMaximumSize(new Dimension(840, 100));
        return panel;
    }

    public String getFieldForStudent(Student student, int i) {
        if (i == 0) return student.getLastName() + " " + student.getFirstName() + " " + student.getMiddleName();
        else if (i == 1) return student.getNumberGroup();
        else {
            int numberExamination = (i - 2) / 2;
            if (i % 2 == 0) {
                if (numberExamination < student.getExaminations().size()) {
                    return student.getExaminations().get(numberExamination).getExaminationName();
                } else return " - ";
            } else {
                if (numberExamination < student.getExaminations().size()) {
                    return student.getExaminations().get(numberExamination).getExaminationMark();
                } else return " - ";
            }
        }
    }


    private int getNumberMaxPage() {
        return (int) ((tableModel.getStudents().size() - 1) / studentOnPage) + 1;
    }

    public void changeStudentOnPage(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        String change = (String) cb.getSelectedItem();
        if (studentOnPage != Integer.parseInt(change)) {
            studentOnPage = Integer.parseInt(change);
            updateComponent();
        }
    }

    public TableModel getTableModel() {
        return tableModel;
    }


    public void updateComponent() {
        removeAll();
        makePanel();
        revalidate();
        repaint();
    }

    private void updateScrollTable() {
        scrollTable.revalidate();
        scrollTable.repaint();
    }
}

