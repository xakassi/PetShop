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
        if (animal instanceof Cat) type = "Кот";
        else if (animal instanceof Dog) type = "Собака";
        else if (animal instanceof Wolf) type = "Волк";

        String characteristic = "Порода: " + animal.getBreed().getBreedName() + "\n" +
                "Имя: " + animal.getName() + "\n" +
                "Цена: " + animal.getCost() + "\n" +
                "Характер: " + animal.getCharacter() + "\n" +
                "Степень загрязнённости: " + animal.getDegreeOfPollution() + "%";

        if (animal instanceof Dog) {
            Dog.Merit merit = ((Dog) animal).getMerit();
            if (merit != null) {
                String merits = "Имеет награды:\n";
                for (String award : merit.getAwards()) {
                    merits += "  " + award + "\n";
                }
                characteristic += "\n" + merits;
            }
        }

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
