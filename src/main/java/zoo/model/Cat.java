package zoo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Cat extends Home {

    public enum CatBreed implements AnimalBreed {
        MONGREL, PERSIAN, BRITISH, MAINECOON, SPHINX, SIAMESE;

        @Override
        public String getBreedName() {
            return this.toString();
        }

        @Override
        public String toString() {
            switch (this) {
                case MONGREL:
                    return "беспородная";
                case PERSIAN:
                    return "персидская";
                case BRITISH:
                    return "британская";
                case MAINECOON:
                    return "мейн-кун";
                case SPHINX:
                    return "сфинкс";
                case SIAMESE:
                    return "сиамская";
                default:
                    return "-";
            }
        }
    }

    @JsonCreator
    public Cat(@JsonProperty("breed") CatBreed breed, @JsonProperty("name") String name, @JsonProperty("cost") Integer cost,
               @JsonProperty("character") AnimalCharacter character, @JsonProperty("degreeOfPollution") Integer degreeOfPollution) {
        super(breed, name, cost, character, degreeOfPollution);
    }

    public Cat() {
        super(CatBreed.values());
    }

    public static Cat createRandomCat() {
        return new Cat();
    }
}
