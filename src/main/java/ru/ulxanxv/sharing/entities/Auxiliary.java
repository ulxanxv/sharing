package ru.ulxanxv.sharing.entities;

public class Auxiliary {

    private Long diskId;
    private String diskName;
    private  String debtor;

    private Auxiliary() {
    }

    public static Auxiliary getInstance(Object[] data) {
        Auxiliary auxiliary = new Auxiliary();
        auxiliary.setDiskId(Long.valueOf(data[0].toString()));
        auxiliary.setDiskName(data[1].toString());
        auxiliary.setDebtor(data[2].toString());

        return auxiliary;
    }

    public Long getDiskId() {
        return diskId;
    }

    public void setDiskId(Long diskId) {
        this.diskId = diskId;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }
}
