package cn.cutemc.autostreamingassistant.camera;

public enum BindResult {
    NOT_FOUND_PLAYER,
    NOT_AT_NEAR_BY,
    WORLD_IS_NULL,
    PLAYER_IS_NULL,
    SUCCESS;

    @Override
    public String toString() {
        return name();
    }
}
