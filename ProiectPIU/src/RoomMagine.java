import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class RoomMagine extends JFrame {
    private JPanel mainPanel;
    private JPanel gridPanel;

    //declaratie butoane + zona text de atentionare
    private JButton mainMenuButton;
    private JButton addStructureButton;
    private JButton addObjectButton;
    private JButton nightModeButton;
    private JButton viewButton;

    //private JTextArea warningTextArea;
    private JLabel warningTextArea;
    private int gridSize;

    //variabile de stare

    private boolean nightMode = false;
    private boolean planView = false;
    private String selectedObject = null;

    private String selectedStructure = null;
    private String imageDirectory = "imagini/";

    private Color buttonColor = Color.gray; //culoare butoane
    private Color buttonTextColor = Color.WHITE; //culoare text butoane

    private String[] objectNames = {"pat", "blat_bucatarie", "frigider", "aragaz" ,"scaun", "raft", "ceas_mare", "canapea", "dulap", "toaleta", "chiuveta", "dus", "masa"};
    private String[] array_obiecte_lungi ={"frigider", "pat", "ceas_mare", "canapea", "dus"};

    private static boolean click_apasat_perete;
    private static int linie_perete;
    private static int coloana_perete;

    private String[][] config;



    public RoomMagine(int marime_grid, String[][] configuratie) {
        setTitle("RoomMagine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        Color culoareFundal = Color.LIGHT_GRAY, culoareGrid = Color.BLACK;
        this.gridSize = marime_grid;

        String[][] vectorPereti = new String[marime_grid][marime_grid];
        int[][] vectorPozitiiCorecteStructuri = new int[marime_grid][marime_grid];




        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(culoareFundal);

        //creaza butoane
        mainMenuButton = new JButton("Meniu");
        addStructureButton = new JButton("Adauga Structura");
        addObjectButton = new JButton("Adauga Obiecte");
        nightModeButton = new JButton("Nightmode ☒");
        viewButton = new JButton("View ☒");
        warningTextArea = new JLabel("");


        //seteaza culoare butoane + culoare text butoane
        mainMenuButton.setBackground(buttonColor);
        mainMenuButton.setForeground(buttonTextColor);

        addStructureButton.setBackground(buttonColor);
        addStructureButton.setForeground(buttonTextColor);

        addObjectButton.setBackground(buttonColor);
        addObjectButton.setForeground(buttonTextColor);

        nightModeButton.setBackground(buttonColor);
        nightModeButton.setForeground(buttonTextColor);

        viewButton.setBackground(buttonColor);
        viewButton.setForeground(buttonTextColor);

        //warningTextArea.setEditable(false);
        //warningTextArea.setColor
        //warningTextArea.setSize(30, 30);

        // pune butoanele in panel + spatiere cu glue
        mainPanel.add(mainMenuButton);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(addStructureButton);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(addObjectButton);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(nightModeButton);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(viewButton);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(warningTextArea);

        //listenerele pentru butoane
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainMenu();
            }
        });

        addStructureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStructureMenu();
            }
        });

        addObjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showObjectMenu();
            }
        });

        nightModeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleNightMode();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeView();
            }
        });

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(gridSize, gridSize));

        // adaugarea liniilor de delimitare pentru fiecare patrat
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JPanel cell = new JPanel();
                cell.setToolTipText(i+","+j);
                cell.setBackground(culoareFundal);
                cell.setBorder(BorderFactory.createLineBorder(culoareGrid));
                gridPanel.add(cell);


                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        if (SwingUtilities.isRightMouseButton(e))
                        {
                            deleteObjectFromGrid(cell);
                        }
                        if(SwingUtilities.isMiddleMouseButton(e))
                        {
                            String object_name = null;
                            Component[] components = cell.getComponents();
                            for (Component component : components) {
                                if (component instanceof JLabel) {
                                    object_name = ((JLabel) component).getToolTipText();
                                }
                            }
                            if(Arrays.asList(array_obiecte_lungi).contains(object_name))
                            {
                                warningTextArea.setForeground(Color.RED);
                                warningTextArea.setText(Errors.showErrorRotationLongObject());
                                timerEroare.start();
                            }
                            else
                            {
                                rotateObjectToGrid(cell, object_name);
                            }
                        }
                        if(SwingUtilities.isLeftMouseButton(e))
                        {
                            if (selectedObject != null) {
                                addObjectToGrid(cell);
                            }
                            if (selectedStructure != null)
                            {
                                if(selectedStructure == "Perete")
                                {
                                    while(true)
                                    {
                                        if (click_apasat_perete == false) {
                                            click_apasat_perete = true;
                                            cell.setBackground(Color.black);
                                            String[] vector_coordonate = cell.getToolTipText().split(",");
                                            linie_perete = Integer.valueOf(vector_coordonate[0]);
                                            coloana_perete = Integer.valueOf(vector_coordonate[1]);

                                            vectorPereti[linie_perete][coloana_perete] = "Perete";
                                            config = vectorPereti;
                                            vectorPozitiiCorecteStructuri[linie_perete][coloana_perete] = 1;
                                            break;
                                        }
                                        if (click_apasat_perete == true) {
                                            cell.setBackground(Color.black);
                                            String[] vector_coordonate = cell.getToolTipText().split(",");
                                            int linie = Integer.valueOf(vector_coordonate[0]);
                                            int coloana = Integer.valueOf(vector_coordonate[1]);
                                            vectorPereti[linie][coloana] = "Perete";
                                            config = vectorPereti;
                                            vectorPozitiiCorecteStructuri[linie][coloana] = 1;
                                            if (linie_perete == linie) {
                                                int perete_mare = Math.max(coloana, coloana_perete);
                                                int perete_mic = Math.min(coloana, coloana_perete) + 1;
                                                int marime_perete = perete_mare - perete_mic;
                                                String coordonate_perete[] = new String[marime_perete];
                                                int i;
                                                int j = 0;
                                                for(i = perete_mic; i < perete_mare; i++)
                                                {
                                                    coordonate_perete[j] = linie + "," + i;
                                                    j++;
                                                    vectorPereti[linie][i] = "Perete";
                                                    config = vectorPereti;
                                                    vectorPozitiiCorecteStructuri[linie][i] = 1;
                                                }

                                                for(i=0;i<coordonate_perete.length;++i) {
                                                    for (Component component : gridPanel.getComponents()) {
                                                        if (component instanceof JPanel) {
                                                            JPanel celula = (JPanel) component;
                                                            if (coordonate_perete[i].equals(celula.getToolTipText())) {
                                                                celula.setBackground(Color.black);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (coloana_perete == coloana) {
                                                int perete_mare = Math.max(linie, linie_perete);
                                                int perete_mic = Math.min(linie, linie_perete) + 1;
                                                int marime_perete = perete_mare - perete_mic;
                                                String coordonate_perete[] = new String[marime_perete];
                                                int i;
                                                int j = 0;
                                                for(i = perete_mic; i < perete_mare; i++)
                                                {
                                                    coordonate_perete[j] = i + "," + coloana;
                                                    vectorPereti[i][coloana] = "Perete";
                                                    config = vectorPereti;
                                                    vectorPozitiiCorecteStructuri[i][coloana] = 1;
                                                    j++;
                                                }

                                                for(i=0;i<coordonate_perete.length;++i) {
                                                    for (Component component : gridPanel.getComponents()) {
                                                        if (component instanceof JPanel) {
                                                            JPanel celula = (JPanel) component;
                                                            if (coordonate_perete[i].equals(celula.getToolTipText())) {
                                                                celula.setBackground(Color.black);
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            linie_perete = -1;
                                            coloana_perete = -1;
                                            click_apasat_perete = false;
                                            break;
                                        }
                                    }
                                }
                                if(selectedStructure == "Usa")
                                {
                                    String[] vector_coordonate = cell.getToolTipText().split(",");
                                    int linie = Integer.valueOf(vector_coordonate[0]);
                                    int coloana = Integer.valueOf(vector_coordonate[1]);
                                    if(vectorPozitiiCorecteStructuri[linie][coloana] == 1)
                                    {
                                        cell.setBackground(Color.orange);
                                    }
                                    else
                                    {
                                        warningTextArea.setForeground(Color.RED);
                                        warningTextArea.setText(Errors.showErrorDoor());
                                        timerEroare.start();
                                    }
                                }
                                if(selectedStructure == "Fereastra")
                                {
                                    String[] vector_coordonate = cell.getToolTipText().split(",");
                                    int linie = Integer.valueOf(vector_coordonate[0]);
                                    int coloana = Integer.valueOf(vector_coordonate[1]);
                                    if(vectorPozitiiCorecteStructuri[linie][coloana] == 1)
                                    {
                                        cell.setBackground(Color.cyan);
                                    }
                                    else
                                    {
                                        warningTextArea.setForeground(Color.RED);
                                        warningTextArea.setText(Errors.showErrorWindow());
                                        timerEroare.start();
                                    }
                                }

                            }

                        }
                    }

                });
            }
        }

        // adaugare panele la frame
        add(mainPanel, BorderLayout.WEST);
        add(gridPanel, BorderLayout.CENTER);

        if(configuratie != null)
        {
            int i, j;
            for(i=0;i<marime_grid;++i)
            {
                for(j=0;j<marime_grid;++j)
                {
                    vectorPereti[i][j] = configuratie[i][j];
                    config = vectorPereti;
                }
            }

            for (i = 0; i < marime_grid; ++i) {
                for (j = 0; j < marime_grid; ++j) {
                    JPanel celula = (JPanel) gridPanel.getComponent(i * marime_grid + j);
                    if (vectorPereti[i][j].equals("Perete")) {
                        celula.setBackground(Color.black);
                    }
                    else if (vectorPereti[i][j].equals("Fereastra")) {
                        celula.setBackground(Color.cyan);
                    }
                    else if (vectorPereti[i][j].equals("Usa")) {
                        celula.setBackground(Color.orange);
                    }
                    else if (Arrays.asList(objectNames).contains(vectorPereti[i][j]))
                    {
                        selectedObject = vectorPereti[i][j];
                        addObjectToGridFromTxt(celula);
                        selectedObject = null;
                    }

                }
            }


        }


    }

    private void showMainMenu()
    {
        JDialog meniuDialog = new JDialog(this, "Meniu");
        meniuDialog.setSize(400, 200);

        MeniuPanel exportJPGPanel = new MeniuPanel(this ,gridPanel);
        meniuDialog.add(exportJPGPanel);
        exportJPGPanel.setBackground(Color.darkGray);
        meniuDialog.setModal(true);

        meniuDialog.setVisible(true);
    }

    private void showStructureMenu() {
        JDialog structureDialog = new JDialog(this, "Selecteaza Obiectul");
        structureDialog.setLayout(new FlowLayout());

        // imaginile pentru structuri
        String[] structureNames = {"Perete", "Usa", "Fereastra"};
        for (String structureName : structureNames) {
            JButton structureButton = new JButton(structureName);
            structureButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedStructure = structureName;
                    selectedObject = null;
                    structureDialog.dispose();
                }
            });
            structureDialog.add(structureButton);
        }

        structureDialog.setSize(100, 200);
        structureDialog.setVisible(true);
    }

    private void showObjectMenu() {
        JDialog objectDialog = new JDialog(this, "Selecteaza Obiectul");
        objectDialog.setLayout(new FlowLayout());

        for (String objectName : objectNames) {
            ImageIcon objectImage;
            if(Arrays.asList(array_obiecte_lungi).contains(objectName))
            {
                objectImage = new ImageIcon(imageDirectory + objectName+ "_ok" + ".png");
            }
            else
            {
                objectImage = new ImageIcon(imageDirectory + objectName + ".png");
            }

            JButton objectButton = new JButton(objectImage);
            objectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedStructure = null;
                    selectedObject = objectName;
                    objectDialog.dispose();
                }
            });
            objectDialog.add(objectButton);
        }

        objectDialog.setSize(800, 600);
        objectDialog.setVisible(true);
    }

    private void toggleNightMode() {
        if (nightMode == false) {
            nightMode = true;
            nightModeButton.setText("Nightmode ☑");
        } else {
            nightMode = false;
            nightModeButton.setText("Nightmode ☒");
        }

        Color culoareFundal, culoareGrid, culoareFundalGrid;
        if (nightMode == false) {
            culoareFundal = Color.LIGHT_GRAY;
            culoareGrid = Color.BLACK;
        } else {
            culoareFundal = Color.DARK_GRAY;
            culoareGrid = Color.WHITE;
        }

        culoareFundalGrid = culoareFundal;
        mainPanel.setBackground(culoareFundal);
        if(planView)
        {
            culoareFundalGrid = Color.WHITE;
            culoareGrid = Color.BLACK;
        }
        for (Component component : gridPanel.getComponents()) {
            if (component instanceof JPanel) {
                Color culoare_block = component.getBackground();
                if(culoare_block != Color.black && culoare_block != Color.cyan && culoare_block != Color.orange)
                {
                    component.setBackground(culoareFundalGrid);
                }

                ((JPanel) component).setBorder(BorderFactory.createLineBorder(culoareGrid));
            }
        }
        revalidate();
        repaint();
    }

    private void changeView() {
        Color culoareFundal, culoareGrid;
        if (planView) {
            imageDirectory = "imagini/";
            viewButton.setText("View ☒");

            if (nightMode == false) {
                culoareFundal = Color.LIGHT_GRAY;
                culoareGrid = Color.BLACK;
            } else {
                culoareFundal = Color.DARK_GRAY;
                culoareGrid = Color.WHITE;
            }

        }
        else
        {
            imageDirectory = "imagini_plan/";
            viewButton.setText("View ☑");
            culoareFundal = Color.WHITE;
            culoareGrid = Color.BLACK;
        }


        gridPanel.setBackground(culoareFundal);
        for (Component component : gridPanel.getComponents()) {
            if (component instanceof JPanel) {
                Color culoare_block = component.getBackground();
                if(culoare_block != Color.black && culoare_block != Color.cyan && culoare_block != Color.orange)
                {
                    component.setBackground(culoareFundal);
                }
                ((JPanel) component).setBorder(BorderFactory.createLineBorder(culoareGrid));
            }
        }

        for (Component component : gridPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel cell = (JPanel) component;
                Component[] components = cell.getComponents();
                for (Component subComponent : components) {
                    if (subComponent instanceof JLabel) {
                        JLabel objectLabel = (JLabel) subComponent;

                        String currentIconPath = objectLabel.getToolTipText();

                        int cate_plusuri=0;
                        if(currentIconPath.contains("+"))
                        {
                            cate_plusuri = countChar(currentIconPath, '+');
                            currentIconPath = currentIconPath.replace("+", "");
                        }

                        String newPath = imageDirectory + currentIconPath;

                        try {
                            deleteObjectFromGrid(cell);

                            addObjectToGrid(cell, newPath);
                            if(Arrays.asList(array_obiecte_lungi).contains(currentIconPath))
                            {
                                String newPathBigObj = newPath+"_jos";
                                String coordonatele = cell.getToolTipText();
                                String[] vector_coordonate = coordonatele.split(",");
                                int linia_de_interes = Integer.valueOf(vector_coordonate[0]) + 1;
                                int coloana_de_interes = Integer.valueOf(vector_coordonate[1]);
                                String coordonatele_de_interes = linia_de_interes+","+coloana_de_interes;
                                for (Component celulaa : gridPanel.getComponents()) {
                                    if (celulaa instanceof JPanel) {
                                        JPanel celula = (JPanel) celulaa;
                                        if(coordonatele_de_interes.equals(((JPanel) celula).getToolTipText()))
                                        {
                                            deleteObjectFromGrid(celula);
                                            addObjectToGrid(celula, newPathBigObj);
                                        }
                                    }
                                }

                            }
                            if(cate_plusuri>0)
                            {
                                rotateObjectToGrid(cell, currentIconPath, cate_plusuri);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();

        planView = !planView;
    }


    Timer timerEroare = new Timer(3000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            warningTextArea.setText(""); // Ștergem textul
            ((Timer) e.getSource()).stop(); // Oprim timerul
        }
    });

    private void addObjectToGrid(JPanel cell, String imaginea) {
        if (selectedObject != null) {
            if(cell.getComponentCount() == 0)
            {
                ImageIcon objectImageIcon;
                if(imaginea == null)
                {
                     objectImageIcon = new ImageIcon(imageDirectory + selectedObject + ".png");
                }
                else
                {
                    objectImageIcon = new ImageIcon(imaginea+".png");
                }

                Image objectImage = objectImageIcon.getImage();

                int cellWidth, cellHeight;

                cellWidth = cell.getWidth();
                cellHeight = cell.getHeight();

                Image scaledObjectImage = objectImage.getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);

                ImageIcon scaledObjectImageIcon;

                scaledObjectImageIcon = new ImageIcon(scaledObjectImage);

                JLabel objectLabel = new JLabel(scaledObjectImageIcon);

                if(imaginea == null)
                {
                    objectLabel.setToolTipText(selectedObject);
                }
                else
                {
                    String[] textDePusInToolTip = imaginea.split("/");
                    objectLabel.setToolTipText(textDePusInToolTip[1]);
                }

                // centram imaginea in celula
                objectLabel.setHorizontalAlignment(JLabel.CENTER);
                objectLabel.setVerticalAlignment(JLabel.CENTER);

                cell.add(objectLabel);
                cell.revalidate();
                cell.repaint();
            }
            else
            {
                warningTextArea.setForeground(Color.RED);
                warningTextArea.setText(Errors.showErrorOverObject());
                timerEroare.start();
            }
        }
    }

    private void rotateObjectToGrid(JPanel cell, String imaginea) {
        rotateObjectToGrid(cell, imaginea, 1);
    }
    private void rotateObjectToGrid(JPanel cell, String imaginea, int cate_ori) {
        deleteObjectFromGrid(cell);

        int n = countChar(imaginea, '+');
        String imagineaa = imaginea.replace("+", "");
        String replacement = imaginea + "+";

        ImageIcon objectImageIcon = new ImageIcon(imageDirectory + imagineaa + ".png");

        Image objectImage = objectImageIcon.getImage();

        BufferedImage originalImage = new BufferedImage(objectImageIcon.getIconWidth(), objectImageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = originalImage.createGraphics();
        g2d.drawImage(objectImage, 0, 0, null);
        g2d.dispose();

        BufferedImage rotatedImage = new BufferedImage(originalImage.getHeight(), originalImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = rotatedImage.createGraphics();

        g.rotate(Math.toRadians(90*(n+1)*cate_ori), rotatedImage.getWidth() / 2, rotatedImage.getHeight() / 2);
        g.drawImage(originalImage, (rotatedImage.getWidth() - originalImage.getWidth()) / 2, (rotatedImage.getHeight() - originalImage.getHeight()) / 2, null);
        g.dispose();

        Image scaledRotatedImage = rotatedImage.getScaledInstance(cell.getWidth(), cell.getHeight(), Image.SCALE_SMOOTH);

        ImageIcon scaledRotatedImageIcon = new ImageIcon(scaledRotatedImage);

        JLabel objectLabel = new JLabel(scaledRotatedImageIcon);
        if(n<3)
        {
            objectLabel.setToolTipText(replacement);
        }
        else
        {
            objectLabel.setToolTipText(imagineaa);
        }

        objectLabel.setHorizontalAlignment(JLabel.CENTER);
        objectLabel.setVerticalAlignment(JLabel.CENTER);

        cell.add(objectLabel);
        cell.revalidate();
        cell.repaint();
    }

    private void addObjectToGrid(JPanel cell)
    {
        addObjectToGrid(cell, null);

        if(Arrays.asList(array_obiecte_lungi).contains(selectedObject)) {
            String string_coordonate = cell.getToolTipText();
            String[] array_coordonate = string_coordonate.split(",");
            int linie = Integer.valueOf(array_coordonate[0]);
            int coloana = Integer.valueOf(array_coordonate[1]);
            int linie_target = linie + 1;
            String linie_coloana_celula_target = linie_target + "," + coloana;
            String nume_obiect_jos = imageDirectory + "/" + selectedObject + "_jos";

            JPanel celula_target = null;
            for (Component component : gridPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel celula = (JPanel) component;
                    if (linie_coloana_celula_target.equals(celula.getToolTipText())) {
                        celula_target = celula;
                        break;
                    }
                }
            }

            if (celula_target != null) {
                addObjectToGrid(celula_target, nume_obiect_jos);
            }
            else
            {
                deleteObjectFromGrid(cell);
                Errors.showErrorLongObject();
            }
        }
    }

    private void deleteObjectFromGrid(JPanel cell) {
        if(cell.getBackground() == Color.black || cell.getBackground() == Color.cyan || cell.getBackground() == Color.orange)
        {
            if(planView==true)
            {
                cell.setBackground(Color.white);
            }
            else
            {
                if(nightMode==false)
                {
                    cell.setBackground(Color.lightGray);
                }
                else
                {
                    cell.setBackground(Color.darkGray);
                }
            }

        }

        Component[] components = cell.getComponents();
        String coordonatele = cell.getToolTipText();
        String[] vector_coordonate = coordonatele.split(",");
        int linia_de_interes = Integer.valueOf(vector_coordonate[0]) + 1;
        int coloana_de_interes = Integer.valueOf(vector_coordonate[1]);
        String coordonate_de_interes = linia_de_interes+","+coloana_de_interes;

        for (Component component : components) {
            if (component instanceof JLabel) {
                String nume_obiect = ((JLabel) component).getToolTipText();
                if(Arrays.asList(array_obiecte_lungi).contains(nume_obiect))
                {
                    for (Component celulaa : gridPanel.getComponents()) {
                        if (celulaa instanceof JPanel) {
                            JPanel celula = (JPanel) celulaa;
                            if(coordonate_de_interes.equals(((JPanel) celula).getToolTipText()))
                            {
                                deleteObjectFromGrid(celula);
                            }
                        }
                    }
                }
                cell.remove(component);
            }
        }
        cell.revalidate();
        cell.repaint();
    }

    private void addObjectToGridFromTxt(JPanel cell) {
        if (selectedObject != null) {
            if(cell.getComponentCount() == 0)
            {
                ImageIcon objectImageIcon;

                objectImageIcon = new ImageIcon(imageDirectory + selectedObject + ".png");

                Image objectImage = objectImageIcon.getImage();

                int cellWidth, cellHeight;

                cellWidth = cell.getWidth();
                cellHeight = cell.getHeight();

                Image scaledObjectImage = objectImage;

                ImageIcon scaledObjectImageIcon;

                scaledObjectImageIcon = new ImageIcon(scaledObjectImage);

                JLabel objectLabel = new JLabel(scaledObjectImageIcon);


                objectLabel.setToolTipText(selectedObject);


                // centram imaginea in celula
                objectLabel.setHorizontalAlignment(JLabel.CENTER);
                objectLabel.setVerticalAlignment(JLabel.CENTER);

                cell.add(objectLabel);
                cell.revalidate();
                cell.repaint();
            }
            else
            {
                warningTextArea.setForeground(Color.RED);
                warningTextArea.setText(Errors.showErrorOverObject());
                timerEroare.start();
            }
        }
    }

    public int countChar(String str, char c)
    {
        int count = 0;

        for(int i=0; i < str.length(); i++)
        {    if(str.charAt(i) == c)
            count++;
        }

        return count;
    }

    public int getterMarimeMatrice()
    {
        return this.gridSize;
    }

    public String[][] getterVectorPereti()
    {
        return config;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RoomMagine app = new RoomMagine(10, null);
            app.setVisible(true);
        });
    }
}
