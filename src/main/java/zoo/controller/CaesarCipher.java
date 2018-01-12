package zoo.controller;

import org.springframework.stereotype.Component;

@Component
public class CaesarCipher {
    public String encrypt(String text) {
        String cryptogram = "";
        for (int i = 0; i < text.length(); i++) {
            int enc = text.charAt(i) + 3;
            cryptogram += (char) (enc);
        }
        return cryptogram;
    }

    public String decrypt(String cryptogram) {
        String text = "";
        for (int i = 0; i < cryptogram.length(); i++) {
            int dec = cryptogram.charAt(i) - 3;
            text += (char) (dec);
        }
        return text;
    }
}

