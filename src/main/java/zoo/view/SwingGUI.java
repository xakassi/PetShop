package zoo.view;

import org.springframework.beans.factory.annotation.Autowired;
import zoo.controller.BuyAndSellService;
import zoo.controller.PrintingService;
import zoo.controller.RealLifeEmulator;
import zoo.controller.SearchService;
import zoo.model.*;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

@org.springframework.stereotype.Component
public class SwingGUI {
    @Autowired
    private PetShopStorage petShopStorage;
    @Autowired
    private SearchService searchService;
    @Autowired
    private BuyAndSellService buyAndSellService;
    @Autowired
    private PrintingService printingService;
    @Autowired
    private RealLifeEmulator realLifeEmulator;

    private String resourcesPath = "src\\main\\resources\\pictures\\";
    private DefaultListModel animalsDefaultListModel;
    private JFrame petShopFrame;
    private JTextArea infoArea;

    @PostConstruct
    private void start() {
        SwingUtilities.invokeLater(() -> {
            try {
                makePetShopMainFrame();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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

    private JPanel makeAnimalsScrollListWithButtons(JList animalsList) {
        JPanel panelButtonsAddAndRemoveAnimal = new JPanel();
        JButton addAnimalButton = new JButton();
        ImageIcon addAnimalIcon = new ImageIcon(resourcesPath + "plus.png");
        addAnimalButton.setIcon(addAnimalIcon);
        JButton removeAnimalButton = new JButton();
        ImageIcon removeAnimalIcon = new ImageIcon(resourcesPath + "minus.png");
        removeAnimalButton.setIcon(removeAnimalIcon);

        addAnimalButton.setBorderPainted(false);
        addAnimalButton.setContentAreaFilled(false);

        removeAnimalButton.setBorderPainted(false);
        removeAnimalButton.setContentAreaFilled(false);

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

        panelButtonsAddAndRemoveAnimal.add(addAnimalButton);
        panelButtonsAddAndRemoveAnimal.add(removeAnimalButton);

        animalsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        animalsList.setVisibleRowCount(0);
        JScrollPane animalsScroll = new JScrollPane(animalsList);
        animalsScroll.setPreferredSize(new Dimension(150, 100));
        JPanel panelAnimals = new JPanel();
        panelAnimals.setLayout(new BorderLayout());
        panelAnimals.add(animalsScroll);
        panelAnimals.add(panelButtonsAddAndRemoveAnimal, BorderLayout.SOUTH);

        return panelAnimals;
    }

    private JPanel makeInfoArea() {
        infoArea = new JTextArea();
        JScrollPane infoAreaScroll = new JScrollPane(infoArea);
        infoAreaScroll.setPreferredSize(new Dimension(100, 80));
        infoArea.setEditable(false);

        JPanel infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setPreferredSize(new Dimension(150, 120));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Информация: "));
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.add(infoAreaScroll);

        return infoPanel;
    }

    private void makePetShopMainFrame() throws Exception {
        makeDefaultMainWindow();
        animalsDefaultListModel = new DefaultListModel();
        JList animalsList = new JList(animalsDefaultListModel);
        setParametersForAnimalsList(animalsList);
        JPanel animalsPanel = makeAnimalsScrollListWithButtons(animalsList);
        petShopFrame.add(animalsPanel);

        JPanel infoPanel = makeInfoArea();
        petShopFrame.add(infoPanel, BorderLayout.SOUTH);

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

