package zoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zoo.model.AbstractAnimal;
import zoo.model.PetShopStorage;

@Component
public class BuyAndSellService {
    @Autowired
    private PetShopStorage storage;

    public void buy(AbstractAnimal animal) {
        storage.getAnimals().add(animal);
    }

    public void sell(AbstractAnimal animal) {
        storage.getAnimals().remove(animal);
    }
}
