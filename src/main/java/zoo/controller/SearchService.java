package zoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zoo.model.Animal;
import zoo.model.AnimalBreed;
import zoo.model.AnimalCharacter;
import zoo.model.PetShopStorage;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchService {
    @Autowired
    private PetShopStorage storage;

    public List<Animal> searchAnimalByBreed(AnimalBreed breed) {
        ArrayList<Animal> searchableAnimals = new ArrayList<>();

        for (Animal animal : storage.getAnimals())
            if (animal.getBreed() == breed)
                searchableAnimals.add(animal);

        return searchableAnimals;
    }

    public List<Animal> searchAnimalByName(String name) {
        ArrayList<Animal> searchableAnimals = new ArrayList<>();

        for (Animal animal : storage.getAnimals())
            if (animal.getName().equals(name))
                searchableAnimals.add(animal);

        return searchableAnimals;
    }

    public List<Animal> searchAnimalByCost(Integer cost) {
        ArrayList<Animal> searchableAnimals = new ArrayList<>();

        for (Animal animal : storage.getAnimals())
            if (animal.getCost() <= cost)
                searchableAnimals.add(animal);

        return searchableAnimals;
    }

    public List<Animal> searchAnimalByCharacter(AnimalCharacter character) {
        ArrayList<Animal> searchableAnimals = new ArrayList<>();

        for (Animal animal : storage.getAnimals())
            if (animal.getCharacter() == character)
                searchableAnimals.add(animal);

        return searchableAnimals;
    }
}
