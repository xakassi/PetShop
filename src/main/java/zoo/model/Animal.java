package zoo.model;

/**
 * Created by Taisia on 24.12.2017.
 */
public interface Animal {
    String[] animalNames = {"Fluffy", "Betty", "Flung", "Rex",
            "Petty", "Hedwig", "Akella", "Max", "Tobby", "Charlie",
            "Jack", "Ruby", "Luna", "Coco", "Jasper", "Shadow", "Olli"};

    /**
     * Getter для состояния breed класса AbstractAnimal
     */
    AnimalBreed getBreed();

    /**
     * Getter для состояния name класса AbstractAnimal
     */
    String getName();

    /**
     * Getter для состояния cost класса AbstractAnimal
     */
    Integer getCost();

    /**
     * Getter для состояния character класса AbstractAnimal
     */
    AnimalCharacter getCharacter();

}
