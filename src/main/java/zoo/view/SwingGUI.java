package zoo.view;

import org.springframework.beans.factory.annotation.Autowired;
import zoo.controller.*;
import zoo.model.*;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Hashtable;
import java.util.Random;

@org.springframework.stereotype.Component
public class SwingGUI {
    @Autowired
    private PetShopStorage petShopStorage;
    @Autowired
    private SearchService searchService;
    @Autowired
    private PrintingService printingService;
    @Autowired
    private RealLifeEmulator realLifeEmulator;
    @Autowired
    private DataStoringJob dataStoringJob;
    @Autowired
    private PollutionJob pollutionJob;
    @Autowired
    private CleaningJob cleaningJob;

    private String resourcesPath = "src\\main\\resources\\pictures\\";
    private DefaultListModel animalsDefaultListModel, findingAnimalsDefaulListModel;
    private JFrame petShopFrame;

    @PostConstruct
    private void start() {
        SwingUtilities.invokeLater(() -> {
            try {
                makePetShopMainFrame();
                startRunAwayThread();
                startSaveAnimalsToFileThread();
                startCleanAndPolluteThread();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void startRunAwayThread() {
        Thread runningThread = new Thread(() -> {
            while (true) {
                int code = new Random().nextInt(10);
                switch (code) {
                    case 7:
                        AbstractAnimal runningAnimal = realLifeEmulator.runAway();
                        if (runningAnimal != null) {
                            animalsDefaultListModel.removeElement(runningAnimal);
                            JOptionPane.showMessageDialog(petShopFrame, printingService.printRunningAwayInfo(runningAnimal));
                        }
                        break;
                }
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        runningThread.setDaemon(true);
        runningThread.start();
    }

    private void startSaveAnimalsToFileThread() {
        Thread savingThread = new Thread(() -> {
            while (true) {
                dataStoringJob.writeAnimalsToFile(petShopStorage);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        savingThread.setDaemon(true);
        savingThread.start();
    }

    private void startCleanAndPolluteThread() {
        Thread polluteThread = new Thread(pollutionJob);
        polluteThread.setDaemon(true);
        polluteThread.start();

        Thread cleanThread = new Thread(cleaningJob);
        cleanThread.setDaemon(true);
        cleanThread.start();
    }

    private void makeDefaultMainWindow() {
        petShopFrame = new JFrame("Магазин животных");
        petShopFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        petShopFrame.setPreferredSize(new Dimension(400, 460));
        petShopFrame.setLocationRelativeTo(null);
        petShopFrame.pack();
        petShopFrame.setVisible(true);

        petShopFrame.setLayout(new BorderLayout());

        petShopFrame.setIconImage(new ImageIcon(resourcesPath + "animal.png").getImage());
        petShopFrame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                try {
                    if (petShopStorage.getAnimals().isEmpty())
                        petShopStorage.getAnimals();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (Animal animal : petShopStorage.getAnimals()) {
                    animalsDefaultListModel.addElement(animal);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

    private void setParametersForAnimalsList(JList animalsList) {
        animalsList.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                JLabel label = (JLabel) component;
                Icon icon;
                if (value instanceof Dog) {
                    icon = new ImageIcon(resourcesPath + "dog.png");
                    if (((Dog) value).getMerit() != null) icon = new ImageIcon(resourcesPath + "awardedDog.png");
                } else if (value instanceof Cat) {
                    icon = new ImageIcon(resourcesPath + "cat.png");
                } else if (value instanceof Wolf) {
                    icon = new ImageIcon(resourcesPath + "wolf.png");
                } else icon = new ImageIcon(resourcesPath + "animal.png");

                if (((AbstractAnimal) value).getName().length() > 10) {
                    label.setText(((Animal) value).getName().substring(0, 10) + "...");
                }
                label.setIcon(icon);
                label.setVerticalTextPosition(JLabel.BOTTOM);
                label.setHorizontalTextPosition(JLabel.CENTER);

                Border border = BorderFactory.createLineBorder(Color.WHITE, 5);
                label.setBorder(border);

                setToolTipText(((Animal) value).getName());

                return label;
            }
        });
    }

    private JButton makeAddAnimalButton(JList animalsList, JTextArea infoArea) {
        JButton addAnimalButton = new JButton();

        ImageIcon addAnimalIcon = new ImageIcon(resourcesPath + "plus.png");
        addAnimalButton.setIcon(addAnimalIcon);
        addAnimalButton.setBorderPainted(false);
        addAnimalButton.setContentAreaFilled(false);

        addAnimalButton.setToolTipText("Купить животное");
        addAnimalButton.addActionListener(e -> {
            animalsList.clearSelection();
            infoArea.setText("");
            try {
                AbstractAnimal animal = realLifeEmulator.buyAnimal();
                animalsDefaultListModel.addElement(animal);
                JOptionPane.showMessageDialog(petShopFrame, printingService.printBuyingInfo(animal));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        return addAnimalButton;
    }

    private JButton makeRemoveAnimalButton(JList animalsList, JTextArea infoArea) {
        JButton removeAnimalButton = new JButton();

        ImageIcon removeAnimalIcon = new ImageIcon(resourcesPath + "minus.png");
        removeAnimalButton.setIcon(removeAnimalIcon);
        removeAnimalButton.setBorderPainted(false);
        removeAnimalButton.setContentAreaFilled(false);

        removeAnimalButton.setToolTipText("Продать животное");
        removeAnimalButton.addActionListener(e -> {
            infoArea.setText("");
            AbstractAnimal animal = (AbstractAnimal) animalsList.getSelectedValue();
            if (animal != null) {
                Object[] options = {"Да", "Нет"};
                int areYouSureAns = JOptionPane.showOptionDialog(petShopFrame,
                        "Вы уверены, что хотите продать это животное?\n" +
                                printingService.printInformationAboutAnimal(animal),
                        "Подтверждение", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (areYouSureAns == 0) {
                    int index = animalsList.getSelectedIndex();
                    realLifeEmulator.sellAnimal(animal);
                    JOptionPane.showMessageDialog(petShopFrame, printingService.printSellingInfo(animal));
                    animalsDefaultListModel.remove(index);
                }
            }
        });

        return removeAnimalButton;
    }

    private JPanel makeAnimalsScrollList(JList animalsList) {
        animalsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        animalsList.setVisibleRowCount(0);
        JScrollPane animalsScroll = new JScrollPane(animalsList);
        animalsScroll.setPreferredSize(new Dimension(150, 100));
        JPanel panelAnimals = new JPanel();
        panelAnimals.setLayout(new BorderLayout());
        panelAnimals.add(animalsScroll);

        return panelAnimals;
    }

    private JPanel makeAnimalsScrollListWithButtons(JList animalsList, JTextArea infoArea) {
        JPanel panelButtonsAddAndRemoveAnimal = new JPanel();
        JButton addAnimalButton = makeAddAnimalButton(animalsList, infoArea);
        JButton removeAnimalButton = makeRemoveAnimalButton(animalsList, infoArea);

        panelButtonsAddAndRemoveAnimal.add(addAnimalButton);
        panelButtonsAddAndRemoveAnimal.add(removeAnimalButton);
        JPanel panelAnimals = makeAnimalsScrollList(animalsList);
        panelAnimals.add(panelButtonsAddAndRemoveAnimal, BorderLayout.SOUTH);

        return panelAnimals;
    }

    private JPanel makeInfoArea(JTextArea infoArea) {
        JScrollPane infoAreaScroll = new JScrollPane(infoArea);
        infoAreaScroll.setPreferredSize(new Dimension(100, 80));
        infoArea.setEditable(false);

        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setPreferredSize(new Dimension(150, 160));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Информация: "));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.add(infoAreaScroll);

        return infoPanel;
    }

    private void makeFrameForFindingAnimals(JPanel searchingByPanel, String searchBy) {
        JFrame findingAnimalsFrame = new JFrame(searchBy);
        findingAnimalsFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        findingAnimalsFrame.setPreferredSize(new Dimension(400, 460));
        findingAnimalsFrame.setLocationRelativeTo(null);
        findingAnimalsFrame.pack();
        findingAnimalsFrame.setVisible(true);
        findingAnimalsFrame.setLayout(new BorderLayout());
        findingAnimalsFrame.setIconImage(new ImageIcon(resourcesPath + "animal.png").getImage());

        findingAnimalsDefaulListModel = new DefaultListModel<>();
        JList animalsList = new JList(findingAnimalsDefaulListModel);
        setParametersForAnimalsList(animalsList);
        findingAnimalsFrame.add(makeAnimalsScrollList(animalsList));
        findingAnimalsFrame.add(searchingByPanel, BorderLayout.NORTH);
        JTextArea infoArea = new JTextArea("");
        findingAnimalsFrame.add(makeInfoArea(infoArea), BorderLayout.SOUTH);

        animalsList.addListSelectionListener(e -> {
            infoArea.setText("");
            int index = animalsList.getSelectedIndex();
            if (index != -1) {
                AbstractAnimal animal = (AbstractAnimal) animalsList.getSelectedValue();
                infoArea.setText(printingService.printInformationAboutAnimal(animal));
            }
        });
    }

    private JButton makeSearchByCostButton(JTextArea searchArea) {
        JButton searchAnimalButton = new JButton();
        ImageIcon removeAnimalIcon = new ImageIcon(resourcesPath + "search.png");
        searchAnimalButton.setIcon(removeAnimalIcon);
        searchAnimalButton.setBorderPainted(false);
        searchAnimalButton.setContentAreaFilled(false);

        searchAnimalButton.setToolTipText("Поиск");
        searchAnimalButton.addActionListener(e -> {
            try {
                Integer cost = new Integer(searchArea.getText());
                findingAnimalsDefaulListModel.clear();
                for (Animal animal : searchService.searchAnimalByCost(cost)) {
                    findingAnimalsDefaulListModel.addElement(animal);
                }
            } catch (NumberFormatException e1) {

            }
        });

        return searchAnimalButton;
    }

    private JPanel makeSearchByCostPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Введите верхнюю границу цены:");
        JTextArea searchArea = new JTextArea("");
        searchArea.setPreferredSize(new Dimension(80, 20));
        searchArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        searchPanel.add(searchLabel);
        searchPanel.add(searchArea);
        searchPanel.add(makeSearchByCostButton(searchArea));

        return searchPanel;
    }

    private JButton makeSearchByNameButton(JTextArea searchArea) {
        JButton searchAnimalButton = new JButton();
        ImageIcon removeAnimalIcon = new ImageIcon(resourcesPath + "search.png");
        searchAnimalButton.setIcon(removeAnimalIcon);
        searchAnimalButton.setBorderPainted(false);
        searchAnimalButton.setContentAreaFilled(false);

        searchAnimalButton.setToolTipText("Поиск");
        searchAnimalButton.addActionListener(e -> {
            String name = searchArea.getText();
            findingAnimalsDefaulListModel.clear();
            for (Animal animal : searchService.searchAnimalByName(name)) {
                findingAnimalsDefaulListModel.addElement(animal);
            }

        });

        return searchAnimalButton;
    }

    private JPanel makeSearchByNamePanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Введите имя питомца:");
        JTextArea searchArea = new JTextArea("");
        searchArea.setPreferredSize(new Dimension(80, 20));
        searchArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        searchPanel.add(searchLabel);
        searchPanel.add(searchArea);
        searchPanel.add(makeSearchByNameButton(searchArea));

        return searchPanel;
    }

    private JButton makeSearchByCharacterButton(JComboBox searchByCharacterComboBox) {
        JButton searchAnimalButton = new JButton();
        ImageIcon removeAnimalIcon = new ImageIcon(resourcesPath + "search.png");
        searchAnimalButton.setIcon(removeAnimalIcon);
        searchAnimalButton.setBorderPainted(false);
        searchAnimalButton.setContentAreaFilled(false);

        searchAnimalButton.setToolTipText("Поиск");
        searchAnimalButton.addActionListener(e -> {
            AnimalCharacter character = (AnimalCharacter) searchByCharacterComboBox.getSelectedItem();
            findingAnimalsDefaulListModel.clear();
            for (Animal animal : searchService.searchAnimalByCharacter(character)) {
                findingAnimalsDefaulListModel.addElement(animal);
            }
        });

        return searchAnimalButton;
    }

    private JPanel makeSearchByCharacterPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Выберите характер:");
        JComboBox searchByComboBox = new JComboBox(AnimalCharacter.values());

        searchPanel.add(searchLabel);
        searchPanel.add(searchByComboBox);
        searchPanel.add(makeSearchByCharacterButton(searchByComboBox));

        return searchPanel;
    }

    private JButton makeSearchByBreedButton(JComboBox searchByBreedComboBox) {
        JButton searchAnimalButton = new JButton();
        ImageIcon removeAnimalIcon = new ImageIcon(resourcesPath + "search.png");
        searchAnimalButton.setIcon(removeAnimalIcon);
        searchAnimalButton.setBorderPainted(false);
        searchAnimalButton.setContentAreaFilled(false);

        searchAnimalButton.setToolTipText("Поиск");
        searchAnimalButton.addActionListener(e -> {
            AnimalBreed breed = (AnimalBreed) searchByBreedComboBox.getSelectedItem();
            findingAnimalsDefaulListModel.clear();
            for (Animal animal : searchService.searchAnimalByBreed(breed)) {
                findingAnimalsDefaulListModel.addElement(animal);
            }
        });

        return searchAnimalButton;
    }

    private JPanel makeSearchByBreeadPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Поиск:");
        JComboBox<AbstractAnimal.AnimalType> mainComboBox = new JComboBox(AbstractAnimal.AnimalType.values());
        JComboBox<AbstractAnimal.AnimalType> subComboBox = new JComboBox<>();

        Hashtable<AbstractAnimal.AnimalType, AnimalBreed[]> subItems = new Hashtable();
        subItems.put(AbstractAnimal.AnimalType.DOG, Dog.DogBreed.values());
        subItems.put(AbstractAnimal.AnimalType.CAT, Cat.CatBreed.values());
        subItems.put(AbstractAnimal.AnimalType.WOLF, Wolf.WolfBreed.values());

        mainComboBox.addActionListener(e -> {
            AbstractAnimal.AnimalType item = (AbstractAnimal.AnimalType) mainComboBox.getSelectedItem();
            Object o = subItems.get(item);

            if (o == null) {
                subComboBox.setModel(new DefaultComboBoxModel());
            } else {
                subComboBox.setModel(new DefaultComboBoxModel((AnimalBreed[]) o));
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(mainComboBox);
        searchPanel.add(subComboBox);
        searchPanel.add(makeSearchByBreedButton(subComboBox));

        return searchPanel;
    }

    private JButton makeSearchButton(JComboBox searchByComboBox) {
        JButton searchAnimalButton = new JButton();
        ImageIcon removeAnimalIcon = new ImageIcon(resourcesPath + "search.png");
        searchAnimalButton.setIcon(removeAnimalIcon);
        searchAnimalButton.setBorderPainted(false);
        searchAnimalButton.setContentAreaFilled(false);

        searchAnimalButton.setToolTipText("Поиск");
        searchAnimalButton.addActionListener(e -> {
            String searchBy = (String) searchByComboBox.getSelectedItem();
            switch (searchBy) {
                case "по породе":
                    makeFrameForFindingAnimals(makeSearchByBreeadPanel(), "Поиск по породе");
                    break;
                case "по имени":
                    makeFrameForFindingAnimals(makeSearchByNamePanel(), "Поиск по имени");
                    break;
                case "по цене":
                    makeFrameForFindingAnimals(makeSearchByCostPanel(), "Поиск по цене");
                    break;
                case "по характеру":
                    makeFrameForFindingAnimals(makeSearchByCharacterPanel(), "Поиск по характеру");
                    break;

            }
        });

        return searchAnimalButton;
    }

    private JPanel makeSearchPanel() {
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Поиск:");
        String[] searchBy = {"по породе", "по имени", "по цене", "по характеру"};
        JComboBox searchByComboBox = new JComboBox(searchBy);

        searchPanel.add(searchLabel);
        searchPanel.add(searchByComboBox);
        searchPanel.add(makeSearchButton(searchByComboBox));

        return searchPanel;
    }

    private void makePetShopMainFrame() throws Exception {
        makeDefaultMainWindow();
        animalsDefaultListModel = new DefaultListModel();
        JList animalsList = new JList(animalsDefaultListModel);
        setParametersForAnimalsList(animalsList);
        JTextArea infoArea = new JTextArea("");
        petShopFrame.add(makeAnimalsScrollListWithButtons(animalsList, infoArea));
        petShopFrame.add(makeSearchPanel(), BorderLayout.NORTH);
        petShopFrame.add(makeInfoArea(infoArea), BorderLayout.SOUTH);

        animalsList.addListSelectionListener(e -> {
            infoArea.setText("");
            int index = animalsList.getSelectedIndex();
            if (index != -1) {
                AbstractAnimal animal = (AbstractAnimal) animalsList.getSelectedValue();
                infoArea.setText(printingService.printInformationAboutAnimal(animal));
            }
        });
    }
}

