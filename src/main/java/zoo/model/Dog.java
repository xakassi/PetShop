package zoo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Dog extends Home {
    Merit merit;

    public static class Merit {
        private List<String> awards;
        private Map<String, Integer> competitions;

        @JsonCreator
        public Merit(@JsonProperty("awards") List<String> awards, @JsonProperty("competitions") Map<String, Integer> competitions) {
            this.awards = awards;
            this.competitions = competitions;
        }

        public List<String> getAwards() {
            return awards;
        }

        public Map<String, Integer> getCompetitions() {
            return competitions;
        }
    }

    public enum DogBreed implements AnimalBreed {
        MONGREL, SHEPHERD, ROTTWEILER, BASSET, PEKINGESE;

        @Override
        public String getBreedName() {
            return this.toString();
        }

        @Override
        public String toString() {
            switch (this) {
                case MONGREL:
                    return "дворняга";
                case SHEPHERD:
                    return "овчарка";
                case ROTTWEILER:
                    return "ротвейлер";
                case BASSET:
                    return "такса";
                case PEKINGESE:
                    return "пекинес";
                default:
                    return "-";
            }
        }
    }

    @JsonCreator
    public Dog(@JsonProperty("breed") DogBreed breed, @JsonProperty("name") String name, @JsonProperty("cost") Integer cost,
               @JsonProperty("character") AnimalCharacter character,
               @JsonProperty("degreeOfPollution") Integer degreeOfPollution, @JsonProperty("merit") Merit merit) {
        super(breed, name, cost, character, degreeOfPollution);
        this.merit = merit;
    }

    public Merit getMerit() {
        return merit;
    }

    public Dog() {
        super(DogBreed.values());
        List<String> awards = new ArrayList<>();
        Map<String, Integer> competitions = new HashMap<>();
        Merit merit = new Merit(awards, competitions);

        int code = new Random().nextInt(10);
        switch (code) {
            case 1:
            case 3:
            case 5:
                awards.add("Самая быстрая собака");
                awards.add("Самая умная собака");
                competitions.put("Саратовская выставка", 2015);
                competitions.put("Московская выставка", 2017);
                this.merit = merit;
                break;
            case 2:
            case 4:
                awards.add("Самая быстрая собака");
                competitions.put("Саратовская выставка", 2015);
                this.merit = merit;
                break;
        }
    }

    public static Dog createRandomDog() {
        return new Dog();
    }

}
