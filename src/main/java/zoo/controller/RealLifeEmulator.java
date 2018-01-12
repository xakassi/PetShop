package zoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zoo.model.*;

import java.util.ArrayList;
import java.util.Random;

@Component
public class RealLifeEmulator {
    @Autowired
    private BuyAndSellService buyAndSellService;
    @Autowired
    private PetShopStorage petShopStorage;

    private AbstractAnimal createRandomAnimal() {
        AbstractAnimal.AnimalType[] types = AbstractAnimal.AnimalType.values();
        Random random = new Random();
        AbstractAnimal.AnimalType type = types[random.nextInt(types.length)];
        switch (type) {
            case CAT:
                return Cat.createRandomCat();
            case DOG:
                return Dog.createRandomDog();
            case WOLF:
                return Wolf.createRandomWolf();
            default:
                return null;
        }
    }

    private AbstractAnimal getRandomAnimalFromStorage() {
        ArrayList<Animal> animals = (ArrayList<Animal>) petShopStorage.getAnimals();
        if (animals != null && animals.size() > 0) {
            int randomIndex = new Random().nextInt(animals.size());
            return (AbstractAnimal) animals.get(randomIndex);
        } else return null;
    }

    public AbstractAnimal buyAnimal() {
        AbstractAnimal animal = createRandomAnimal();
        if (animal != null) {
            buyAndSellService.buy(animal);
        }
        return animal;
    }

    public void sellAnimal(AbstractAnimal animal) {
        if (animal != null) {
            buyAndSellService.sell(animal);
        }
    }

    public AbstractAnimal runAway() {
        AbstractAnimal animal = getRandomAnimalFromStorage();
        if (animal != null) {
            buyAndSellService.sell(animal);
        }
        return animal;
    }
}
