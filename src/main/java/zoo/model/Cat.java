package zoo.model;

/**
 * Created by Taisia on 24.12.2017.
 */
public class Cat extends Home {
    public enum CatBreed implements AnimalBreed {
        MONGREL, PERSIAN, BRITISH, MAINECOON, SPHINX, SIAMESE;

        @Override
        public String getTypeName() {
            return this.toString();
        }

        @Override
        public String toString() {
            switch (this) {
                case MONGREL:
                    return "беспородная";
                case PERSIAN:
                    return "персидская";
                case BRITISH:
                    return "британская";
                case MAINECOON:
                    return "мейн-кун";
                case SPHINX:
                    return "сфинкс";
                case SIAMESE:
                    return "сиамская";
                default:
                    return "-";
            }
        }
    }

    public Cat(CatBreed breed, String name, Integer cost, AnimalCharacter character) {
        super(breed, name, cost, character);
    }

    public Cat() {
        super(CatBreed.values());
    }

}
