import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class MeniuPanel extends JPanel {

    private RoomMagine roomMagine;
    private Color buttonColor = Color.gray;
    private Color buttonTextColor = Color.WHITE;
    public MeniuPanel(RoomMagine roomMagine, Component gridPanel) {
        this.roomMagine = roomMagine;
        int marime_grid = this.roomMagine.getterMarimeMatrice();
        String[][] config = this.roomMagine.getterVectorPereti();

        JButton exportButton = new JButton("Export JPG");
        JButton saveButton = new JButton("Save / Export TXT");
        JButton backButton = new JButton("Iesire la meniu principal");
        JButton exitButton = new JButton("Iesire din aplicatie");
        exportButton.setBackground(buttonColor);
        exportButton.setForeground(buttonTextColor);

        saveButton.setBackground(buttonColor);
        saveButton.setForeground(buttonTextColor);

        backButton.setBackground(Color.pink);
        backButton.setForeground(buttonTextColor);

        exitButton.setBackground(Color.RED);
        exitButton.setForeground(buttonTextColor);

        add(exportButton);
        setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

        add(saveButton);
        setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

        add(backButton);
        setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

        add(exitButton);
        setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fileChooser.setDialogTitle("Selectează locația și numele fișierului");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Fișiere JPEG (*.jpg)", "jpg"));

                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".jpg";
                    ExportJPG.exportGridToJPG(gridPanel, filePath);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                fileChooser.setDialogTitle("Selectează locația și numele fișierului");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Fișiere text (*.txt)", "txt"));

                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath() + ".txt";
                    ExportTXT.exportGridToTXT(marime_grid, config,filePath);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var valoare_atentionare =JOptionPane.showConfirmDialog(gridPanel, "Aveti grija! Progresul va fi pierdut daca nu a fost salvat inainte de aceasta actiune! Apasati \"Yes\" pentru a iesi.", "Aveti grija!", JOptionPane.ERROR_MESSAGE);
                if(valoare_atentionare == 0)
                {
                    stopAndOpenMenu();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var valoare_atentionare = JOptionPane.showConfirmDialog(gridPanel, "Aveti grija! Progresul va fi pierdut daca nu a fost salvat inainte de aceasta actiune! Apasati \"Yes\" pentru a iesi.", "Aveti grija!", JOptionPane.ERROR_MESSAGE);
                if(valoare_atentionare == 0)
                {
                    System.exit(0);
                }

            }
        });


    }

    private void stopAndOpenMenu() {
        roomMagine.dispose();
        SwingUtilities.invokeLater(() -> {
            MeniuStart newMenu = new MeniuStart();
            newMenu.setVisible(true);
        });
    }
}
