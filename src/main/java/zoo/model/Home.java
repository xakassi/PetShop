package zoo.model;

/**
 * Created by Taisia on 24.12.2017.
 */
public abstract class Home extends AbstractAnimal {
    Home(AnimalBreed breed, String name, Integer cost, AnimalCharacter character) {
        super(breed, name, cost, character);
    }

    Home(AnimalBreed[] breeds) {
        super(breeds);
    }
}
