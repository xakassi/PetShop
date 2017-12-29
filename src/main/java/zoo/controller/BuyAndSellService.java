package zoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zoo.model.Animal;
import zoo.model.PetShopStorage;

@Component
public class BuyAndSellService {
    @Autowired
    private PetShopStorage storage;

    public void buy(Animal animal) {
        storage.getAnimals().add(animal);
    }

    public void sell(Animal animal) {
        storage.getAnimals().remove(animal);
    }
}
