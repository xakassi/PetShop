package zoo.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PetShopStorage {

    private List<Animal> animals;

    public List<Animal> getAnimals() {
        if (animals == null) {
            animals = new ArrayList<>();
            // получать животных из файла
            animals.add(new Dog());
            animals.add(new Cat());
            animals.add(new Wolf());
            animals.add(new Dog());
            animals.add(new Cat());
            animals.add(new Cat());
            animals.add(new Cat());
            animals.add(new Cat());
            animals.add(new Cat());
            animals.add(new Cat());
        }

        return animals;

        /*SearchService ss = new SearchService(this);
        animals.add(new Cat(Cat.CatBreed.BRITISH, "Fluffy", 1000, AnimalCharacter.SMART));
        System.out.println(ss.searchAnimalByBreed(Cat.CatBreed.BRITISH));*/
    }
}
