package cn.cutemc.autostreamingassistant;

public enum ClientStatus {

    READY,
    BOUND;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static ClientStatus fromString(String str) {
        return valueOf(str.toUpperCase());
    }
}
