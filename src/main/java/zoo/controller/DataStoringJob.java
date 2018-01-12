package zoo.controller;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zoo.model.Animal;
import zoo.model.PetShopStorage;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class DataStoringJob {
    @Autowired
    private CaesarCipher caesarCipher;
    private final static String fullPath = "animals.json";

    public void writeAnimalsToFile(PetShopStorage storage) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (storage != null && !storage.getAnimals().isEmpty()) {
                String animalToJsonString = mapper.writeValueAsString(storage);
                ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
                writer.writeValue(new File(fullPath), caesarCipher.encrypt(animalToJsonString));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Animal> readAnimalsFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String encryptedAnimalFromJson = mapper.readValue(new File(fullPath), String.class);
            PetShopStorage storageFromFile = mapper.readValue(caesarCipher.decrypt(encryptedAnimalFromJson), PetShopStorage.class);
            return storageFromFile.getAnimals();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
