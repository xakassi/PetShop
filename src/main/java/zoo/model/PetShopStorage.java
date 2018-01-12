package zoo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zoo.controller.DataStoringJob;

import java.util.ArrayList;
import java.util.List;

@Component
public class PetShopStorage {
    @Autowired
    private DataStoringJob dataStoringJob;

    private List<Animal> animals;

    public List<Animal> getAnimals() {
        if (animals == null) {
            animals = dataStoringJob.readAnimalsFromFile();
            if (animals == null) {
                animals = new ArrayList<>();
            }
        }

        return animals;
    }
}
