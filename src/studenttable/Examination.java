package studenttable;


public class Examination {

    private String examinationName;
    private int examinationMark;

    public Examination(String examinationName, int examinationMark) {
        this.examinationName = examinationName;
        this.examinationMark = examinationMark;
    }

    public String getExaminationMark() {
        return Integer.toString(examinationMark);
    }

    public int getExaminationMarkInt() {
        return examinationMark;
    }



    public String getExaminationName() {
        return examinationName;
    }


}
