package zoo.model;

import java.util.Random;

/**
 * Created by Taisia on 24.12.2017.
 */
public abstract class AbstractAnimal implements Animal {
    protected AnimalBreed breed;
    protected String name;
    protected Integer cost;
    protected AnimalCharacter character;

    public AbstractAnimal(AnimalBreed breed, String name, Integer cost, AnimalCharacter character) {
        this.breed = breed;
        this.name = name;
        this.cost = cost;
        this.character = character;
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
        return character == animal.character;
    }

    @Override
    public int hashCode() {
        int result = breed != null ? breed.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        result = 31 * result + (character != null ? character.hashCode() : 0);
        return result;
    }
}
