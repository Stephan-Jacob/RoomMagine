import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MeniuStart extends JFrame {
    private JPanel mainPanel;

    private Color buttonColor = Color.gray; // culoare butoane
    private Color buttonTextColor = Color.WHITE; // culoare text butoane

    private Color fundalColor = Color.LIGHT_GRAY;

    private JButton ButtonIntraInAplicatie;
    private JButton ButtonCredite;

    private JButton ButtonImportFisier;

    public MeniuStart() {
        setTitle("RoomMagine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        Color culoareFundal = fundalColor;

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBackground(culoareFundal);

        JLabel titleLabel = new JLabel("Meniu principal - RoomMagine");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        ButtonIntraInAplicatie = new JButton("Incepe un nou plan");
        ButtonIntraInAplicatie.setBackground(buttonColor);
        ButtonIntraInAplicatie.setForeground(buttonTextColor);
        ButtonIntraInAplicatie.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonImportFisier = new JButton("Load / Import Fișier");
        ButtonImportFisier.setBackground(buttonColor);
        ButtonImportFisier.setForeground(buttonTextColor);
        ButtonImportFisier.setAlignmentX(Component.CENTER_ALIGNMENT);

        ButtonCredite = new JButton("Credite");
        ButtonCredite.setBackground(buttonColor);
        ButtonCredite.setForeground(buttonTextColor);
        ButtonCredite.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(ButtonIntraInAplicatie);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(ButtonImportFisier);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(ButtonCredite);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel, BorderLayout.CENTER);

        ButtonIntraInAplicatie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Introduceți mărimea tabelei pe care veti proiecta cladirea:");
                try {
                    int marimeGrid = Integer.parseInt(input);
                    if(marimeGrid >= 10 && marimeGrid <= 30)
                    {
                        showAplicatie(marimeGrid, null);
                    }
                    else
                    {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Vă rugăm să introduceți un număr valid.", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        ButtonImportFisier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importaSiAfiseazaDinFisier();
            }
        });

        ButtonCredite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCredite();
            }
        });
    }

    private void showAplicatie(int marime_grid, String configuratie[][]) {
        RoomMagine app = new RoomMagine(marime_grid, configuratie);
        this.setVisible(false);
        app.setVisible(true);
    }

    private void importaSiAfiseazaDinFisier() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fișiere text (*.txt)", "txt");
        fileChooser.setFileFilter(filter);

        int returnVal = fileChooser.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                int i, j;
                String primaLinie = br.readLine();
                int marimeGrid = Integer.parseInt(primaLinie);
                String[][] configuratie = new String[marimeGrid][marimeGrid];
                for(i=0; i<marimeGrid; ++i)
                {
                    String urmatoareaLinie = br.readLine();
                    String[] obiecte = urmatoareaLinie.split(",");
                    for(j=0;j<marimeGrid; ++j)
                    {
                        configuratie[i][j] = obiecte[j];
                    }
                }



                if (marimeGrid >= 10 && marimeGrid <= 30) {
                    showAplicatie(marimeGrid, configuratie);
                }
                else
                {
                    throw new NumberFormatException();
                }
            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Eroare la citirea din fișier.", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showCredite() {
        JDialog crediteDialog = new JDialog(this, "Credite");
        crediteDialog.setSize(800, 600);
        crediteDialog.setLayout(new BorderLayout());

        crediteDialog.setBackground(fundalColor);
        crediteDialog.setModal(true);

        JLabel titleLabel = new JLabel("Credite", SwingConstants.CENTER);
        titleLabel.setForeground(buttonColor);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        crediteDialog.add(titleLabel, BorderLayout.NORTH);

        JLabel footerLabel = new JLabel("Proiect la materia Proiectarea Interfetelor Utilizator - Iacob Cosmin-Stefan - 1408B - 2023-2024 ", SwingConstants.CENTER);
        footerLabel.setForeground(Color.BLACK);
        crediteDialog.add(footerLabel, BorderLayout.CENTER);

        crediteDialog.setVisible(true);
        crediteDialog.setBackground(fundalColor);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MeniuStart app = new MeniuStart();
            app.setVisible(true);
        });
    }
}
