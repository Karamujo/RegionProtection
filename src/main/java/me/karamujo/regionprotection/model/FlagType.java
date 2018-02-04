package me.karamujo.regionprotection.model;

/**
 *
 * @author Enzo
 */
public enum FlagType {

    PVP,
    MOBS,
    BUILD;

    private FlagType() {
    }

    public String getName() {
        return name().toLowerCase();
    }

}
