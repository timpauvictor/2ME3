package model;


/**
 *
 */
public enum Setting {
    None, Empty, Red, Blue;

    public static Setting fromString(String setting) {
        switch(setting) {
            case "Red": return Red;
            case "Blue": return Blue;
            case "Black": return Empty;
            default: return None;
        }
    }
}