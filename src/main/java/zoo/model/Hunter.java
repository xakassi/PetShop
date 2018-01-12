package zoo.model;

public abstract class Hunter extends AbstractAnimal {
    Hunter(AnimalBreed breed, String name, Integer cost, AnimalCharacter character, Integer degreeOfPollution) {
        super(breed, name, cost, character, degreeOfPollution);
    }

    Hunter(AnimalBreed[] breeds) {
        super(breeds);
    }
}
