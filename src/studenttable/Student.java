package studenttable;

import java.util.List;


public class Student {

    private String lastName;
    private String firstName;
    private String middleName;
    private String numberGroup;
    private double middleMark;
    private List<OpshchRab> opshchRobota;

    public Student(String lastName, String firstName, String middleName,
                   String numberGroup, List<OpshchRab> opshchRobota) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.numberGroup = numberGroup;
        this.opshchRobota = opshchRobota;
        this.middleMark = solveMiddleMark();
    }

    private double solveMiddleMark() {
        int val = 0;
        if (opshchRobota.size() == 0) return 0;
        for (OpshchRab exam: opshchRobota){
            val += Integer.parseInt(exam.getOpshchRabotaMap());
        }
        return val/ opshchRobota.size();
    }

    public String getFirstName() {
        return firstName;
    }



    public String getLastName() {
        return lastName;
    }


    public String getMiddleName() {
        return middleName;
    }


    public String getNumberGroup() {
        return numberGroup;
    }


    public List<OpshchRab> getOpshchRobota() {
        return opshchRobota;
    }


    public double getMiddleMark() {
        return middleMark;
    }

}
