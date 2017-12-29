package zoo.model;

/**
 * Created by Taisia on 24.12.2017.
 */
public enum AnimalCharacter {
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
