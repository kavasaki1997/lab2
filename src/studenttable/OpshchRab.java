package studenttable;


public class OpshchRab {

    private String opshchRabotaName;
    private int opshchRabotaMark;

    public OpshchRab(String opshchRabotaName, int opshchRab) {
        this.opshchRabotaName = opshchRabotaName;
        this.opshchRabotaMark = opshchRab;
    }

    public String getOpshchRabotaMap() {
        return Integer.toString(opshchRabotaMark);
    }
    public String getOpshchRabotaName() {
        return opshchRabotaName;
    }
}
