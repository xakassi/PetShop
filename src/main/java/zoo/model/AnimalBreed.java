package zoo.model;

import java.io.Serializable;

public interface AnimalBreed extends Serializable {
    /**
     * Getter для получения названия породы (breed) от конкретных животных
     */
    String getBreedName();
}
