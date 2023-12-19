package cn.cutemc.autostreamingassistant.camera;

public enum UnbindResult {
    NOT_BOUND_CAMERA,
    SUCCESS;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static UnbindResult fromString(String str) {
        return valueOf(str.toUpperCase());
    }
}
