package me.karamujo.regionprotection.model;

/**
 *
 * @author Enzo
 */
public class Flag {

    private FlagType type;
    private boolean allow;

    public Flag(FlagType type, boolean allow) {
        this.type = type;
        this.allow = allow;
    }

    public FlagType getType() {
        return type;
    }

    public boolean isAllow() {
        return allow;
    }

    public void setType(FlagType type) {
        this.type = type;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }

}
