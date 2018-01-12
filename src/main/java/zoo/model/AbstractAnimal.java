package zoo.model;

import java.util.Random;

public abstract class AbstractAnimal implements Animal {
    protected AnimalBreed breed;
    protected String name;
    protected Integer cost;
    protected AnimalCharacter character;
    protected Integer degreeOfPollution;

    public enum AnimalType {
        CAT, DOG, WOLF;

        @Override
        public String toString() {
            switch (this) {
                case WOLF:
                    return "Волк";
                case CAT:
                    return "Кот";
                case DOG:
                    return "Собака";
                default:
                    return null;
            }
        }
    }

    public AbstractAnimal(AnimalBreed breed, String name, Integer cost, AnimalCharacter character, Integer degreeOfPollution) {
        this.breed = breed;
        this.name = name;
        this.cost = cost;
        this.character = character;
        this.degreeOfPollution = degreeOfPollution;
    }

    AbstractAnimal(AnimalBreed[] breeds) {
        AnimalCharacter[] characters = AnimalCharacter.values();
        Random random = new Random();

        AnimalBreed breed = breeds[random.nextInt(breeds.length)];
        String name = animalNames[random.nextInt(animalNames.length)];
        Integer cost = random.nextInt(3000) + 20;
        AnimalCharacter character = characters[random.nextInt(characters.length)];

        this.breed = breed;
        this.name = name;
        this.cost = cost;
        this.character = character;
        this.degreeOfPollution = 0;
    }

    @Override
    public AnimalBreed getBreed() {
        return breed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getCost() {
        return cost;
    }

    @Override
    public AnimalCharacter getCharacter() {
        return character;
    }

    @Override
    public Integer getDegreeOfPollution() {
        return degreeOfPollution;
    }

    public void setDegreeOfPollution(Integer degreeOfPollution) {
        this.degreeOfPollution = degreeOfPollution;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractAnimal animal = (AbstractAnimal) o;

        if (breed != null ? !breed.equals(animal.breed) : animal.breed != null) return false;
        if (name != null ? !name.equals(animal.name) : animal.name != null) return false;
        if (cost != null ? !cost.equals(animal.cost) : animal.cost != null) return false;
        if (character != animal.character) return false;
        return degreeOfPollution != null ? degreeOfPollution.equals(animal.degreeOfPollution) : animal.degreeOfPollution == null;
    }

    @Override
    public int hashCode() {
        int result = breed != null ? breed.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (character != null ? character.hashCode() : 0);
        result = 31 * result + (degreeOfPollution != null ? degreeOfPollution.hashCode() : 0);
        return result;
    }
}
