package zoo.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Dog.class),
        @JsonSubTypes.Type(value = Cat.class),
        @JsonSubTypes.Type(value = Wolf.class),
})
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

    /**
     * Getter для состояния degreeOfPollution класса AbstractAnimal
     */
    Integer getDegreeOfPollution();
}
