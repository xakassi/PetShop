package zoo.model;

public abstract class Home extends AbstractAnimal {
    Home(AnimalBreed breed, String name, Integer cost, AnimalCharacter character, Integer degreeOfPollution) {
        super(breed, name, cost, character, degreeOfPollution);
    }

    Home(AnimalBreed[] breeds) {
        super(breeds);
    }
}
