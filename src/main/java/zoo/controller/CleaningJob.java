package zoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zoo.model.AbstractAnimal;
import zoo.model.Animal;
import zoo.model.PetShopStorage;

@Component
public class CleaningJob implements Runnable {
    @Autowired
    private PetShopStorage storage;

    @Override
    public void run() {
        while (true) {
            for (Animal animal : storage.getAnimals()) {
                Integer pollution = (int) (Math.random() * 10) + 20;
                int previousDegree = animal.getDegreeOfPollution();
                int newDegree = (previousDegree - pollution) < 0 ? 0 : (previousDegree - pollution) % 100;
                ((AbstractAnimal) animal).setDegreeOfPollution(newDegree);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
