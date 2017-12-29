package zoo.model;

/**
 * Created by Taisia on 24.12.2017.
 */
public class Wolf extends Hunter {
    public Wolf(WolfBreed breed, String name, Integer cost, AnimalCharacter character) {
        super(breed, name, cost, character);
        this.breed = breed;
    }

    public Wolf() {
        super(WolfBreed.values());
    }

    public enum WolfBreed implements AnimalBreed {
        ARCTIC, RED, TASMANIAN, SIBERIAN, STEPPE, CAUCASIAN;

        @Override
        public String getTypeName() {
            return this.toString();
        }

        @Override
        public String toString() {
            switch (this) {
                case ARCTIC:
                    return "арктический (полярный)";
                case RED:
                    return "красный";
                case TASMANIAN:
                    return "тасманский";
                case SIBERIAN:
                    return "сибирский лесной";
                case STEPPE:
                    return "степной";
                case CAUCASIAN:
                    return "кавказский";
                default:
                    return "-";
            }
        }
    }
}
