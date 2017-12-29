package zoo.controller;

import org.springframework.stereotype.Component;
import zoo.model.AbstractAnimal;
import zoo.model.Cat;
import zoo.model.Dog;
import zoo.model.Wolf;

@Component
public class PrintingService {
    public String printInformationAboutAnimal(AbstractAnimal animal) {
        String type = "";
        if (animal.getBreed() instanceof Cat.CatBreed) type = "Кот";
        else if (animal.getBreed() instanceof Dog.DogBreed) type = "Собака";
        else if (animal.getBreed() instanceof Wolf.WolfBreed) type = "Волк";

        String characteristic = "Порода: " + animal.getBreed().getTypeName() + "\n" +
                "Имя: " + animal.getName() + "\n" +
                "Цена: " + animal.getCost() + "\n" +
                "Характер: " + animal.getCharacter();

        return type + "\n" + characteristic;
    }

    public String printBuyingInfo(AbstractAnimal animal) {
        return "Магазин купил новое животное!\n" + printInformationAboutAnimal(animal);
    }

    public String printSellingInfo(AbstractAnimal animal) {
        return "Магазин продал животное!\n" + printInformationAboutAnimal(animal);
    }

    public String printRunningAwayInfo(AbstractAnimal animal) {
        return "Из магазина убежало животное!\n" + printInformationAboutAnimal(animal);
    }
}
