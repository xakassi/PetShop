package zoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zoo.model.*;

import java.util.Random;

@Component
public class RealLifeEmulator {
    @Autowired
    private BuyAndSellService buyAndSellService;

    enum AnimalType {
        CAT, DOG, WOLF
    }

    private AbstractAnimal getRandomAnimal() {
        AnimalType[] types = AnimalType.values();
        Random random = new Random();
        AnimalType type = types[random.nextInt(types.length)];
        switch (type) {
            case CAT:
                return new Cat();
            case DOG:
                return new Dog();
            case WOLF:
                return new Wolf();
            default:
                return null;
        }
    }

    public AbstractAnimal buyAnimal() {
        AbstractAnimal animal = getRandomAnimal();
        if (animal != null)
            buyAndSellService.buy(animal);
        return animal;
    }

    public void sellAnimal(Animal animal) {
        if (animal != null)
            buyAndSellService.sell(animal);
    }

    public AbstractAnimal runAway() {
        AbstractAnimal animal = getRandomAnimal();
        if (animal != null)
            buyAndSellService.sell(animal);
        return animal;
    }
}
