package zoo.model;

/**
 * Created by Taisia on 24.12.2017.
 */
public abstract class Hunter extends AbstractAnimal {
    Hunter(AnimalBreed breed, String name, Integer cost, AnimalCharacter character) {
        super(breed, name, cost, character);
    }

    Hunter(AnimalBreed[] breeds) {
        super(breeds);
    }
}
