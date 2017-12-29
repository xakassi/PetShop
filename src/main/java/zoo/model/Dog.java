package zoo.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Taisia on 24.12.2017.
 */
public class Dog extends Home {
    Merit merit;

    public enum DogBreed implements AnimalBreed {
        MONGREL, SHEPHERD, ROTTWEILER, BASSET, PEKINGESE;

        @Override
        public String getTypeName() {
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

    public Dog(AnimalBreed breed, String name, Integer cost, AnimalCharacter character, Merit merit) {
        super(breed, name, cost, character);
        this.merit = merit;
    }

    public Dog() {
        super(DogBreed.values());
    }

    public class Merit{
        private List<String> awards;
        private Map<String, Integer> competitions;

        public Merit(List<String> awards, Map<String, Integer> competitions) {
            this.awards = awards;
            this.competitions = competitions;
        }

        public List<String> getAwards() {
            return awards;
        }

        public void setAwards(List<String> awards) {
            this.awards = awards;
        }

        public Map<String, Integer> getCompetitions() {
            return competitions;
        }

        public void setCompetitions(Map<String, Integer> competitions) {
            this.competitions = competitions;
        }
    }

}
