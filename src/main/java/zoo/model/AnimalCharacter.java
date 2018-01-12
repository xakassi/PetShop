package zoo.model;

import java.io.Serializable;

public enum AnimalCharacter implements Serializable {
    SOFT, ACTIVE, FIDGET, SMART;

    @Override
    public String toString() {
        switch (this) {
            case SOFT:
                return "ласковый";
            case ACTIVE:
                return "активный";
            case FIDGET:
                return "игривый";
            case SMART:
                return "умный";
            default:
                return "-";
        }
    }
}
