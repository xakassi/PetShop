package zoo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Wolf extends Hunter {

    public enum WolfBreed implements AnimalBreed {
        ARCTIC, RED, TASMANIAN, SIBERIAN, STEPPE, CAUCASIAN;

        @Override
        public String getBreedName() {
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

    @JsonCreator
    public Wolf(@JsonProperty("breed") WolfBreed breed, @JsonProperty("name") String name,
                @JsonProperty("cost") Integer cost, @JsonProperty("character") AnimalCharacter character,
                @JsonProperty("degreeOfPollution") Integer degreeOfPollution) {
        super(breed, name, cost, character, degreeOfPollution);
    }

    public Wolf() {
        super(WolfBreed.values());
    }

    public static Wolf createRandomWolf() {
        return new Wolf();
    }


}
