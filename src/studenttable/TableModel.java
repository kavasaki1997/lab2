package studenttable;

import java.util.ArrayList;
import java.util.List;


public class TableModel {

    private int numberSemestr = 5;
    private int numberMaxNumberSemestr;
    private List<Student> students;

    public TableModel() {
        students = new ArrayList<Student>();
    }

    public int getNumberSemestr() {
        return numberSemestr;
    }

    public void setNumberSemestr(int numberExaminations) {
        this.numberSemestr = numberExaminations;
    }



    public void setMaxNumberSemestr(int maxExaminations) {
        numberMaxNumberSemestr = (maxExaminations > numberMaxNumberSemestr) ? maxExaminations : numberMaxNumberSemestr;
    }

    public void setMaxNumberSemestr() {
        numberMaxNumberSemestr = 5;
    }

    public List<Student> getStudents() {
        return students;
    }



}
