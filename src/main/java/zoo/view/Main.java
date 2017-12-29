package zoo.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import zoo.config.PetShopConfig;

/**
 * Created by Taisia on 29.12.2017.
 */
public class Main {
    @Autowired
    private SwingGUI swingGUI;

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(PetShopConfig.class);
    }
}
