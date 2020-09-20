package mperic.unizd.imenik106.domain;
public enum Sex {
    ENUM1("male"),
    ENUM2("female");

    private String friendlyName;

    private Sex(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override public String toString(){
        return friendlyName;
    }
}