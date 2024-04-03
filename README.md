RO:

RoomMagine este o aplicație Java creată pentru planificarea și proiectarea spațiilor
interioare. Scopul său principal este de a oferi utilizatorilor un instrument interactiv pentru a crea
planuri personalizate pentru diferite tipuri de încăpere, precum locuințe, birouri sau alte tipuri de săli.
Aplicația facilitează procesul de planificare și decorare a spațiului interior, oferind instrumente
potrivite în acest sens.

2.1. Meniul de start (MeniuStart.java)

Aplicația începe cu un meniu principal, unde utilizatorul poate alege între a începe un
nou plan, a importa un plan deja existent sau a vedea secțiunea de credite prin intermediul celor
trei butoane aflate în view. După alegerea dimensiunilor grilei de lucru, utilizatorul este
redirecționat către aplicația RoomMagine.

2.2. Interfața de bază(RoomMagine.java)

Interfața principală a aplicației are un panou principal pe partea stângă, care conține
butoane pentru meniul principal, adăugarea de structuri, adăugarea de obiecte, activarea modului
de noapte și schimbarea modului de vizualizare între normal și plan(modul plan isi propune sa
elimine texturarea elementelor pentru a facilita procesul de a masura exact spatiul necesar
plasarii obiectelor). Pe partea dreaptă, există o grilă unde utilizatorul poate plasa structuri și
obiecte. Aceste structuri și obiecte se pot selecta cu ajutorul butoanelor Selecteaza structura,
respectiv Selecteaza obiectul și plasa direct pe grilă folosind click-ul stanga al mouse-ului.

2.3. Meniul aplicației(MeniuPanel.java)

Oferă opțiuni pentru exportarea schemei în format JPG, save(exportarea fișierului .txt cu
configurația actuala a grilei), revenirea la meniul principal și ieșirea din aplicație.
2.4. ExportJPG.java

Oferă funcționalitatea de exportare a schemei din panoul principal în format JPG.
2.5. ExportTXT.java

Oferă funcționalitatea de salvare a progresului făcut în construirea proiectului (exportare
a schemei din panoul principal în format .txt).
2.6. Errors.java

Fișierul care returnează mesajele de eroare ce pot apărea în timpul interacțiunii cu
aplicația.

2.7. Alte fișiere

Aplicația mai contine si 2 foldere cu imaginile aferente obiectelor, atat pentru modul
normal cât și pentru modul plan(practic obiectele din grid se updateaza cu sprite-urile aferente la
trecerea din modul normal în mod plan și invers).


EN:

RoomMagine is a Java application designed for the planning and design of interior spaces. Its main purpose is to provide users with an interactive tool to create customized plans for any type of room, such as homes, offices, or other types of spaces. The application facilitates the process of planning and decorating the interior space by providing suitable tools for this purpose.


2.1. Start Menu (MenuStart.java)

The application begins with a main menu where the user can choose between starting a new plan, importing an existing plan, or viewing the credits section through the three buttons displayed. After selecting the dimensions of the workspace grid, the user is redirected to the RoomMagine application.

2.2. Basic Interface (RoomMagine.java)

The main interface of the application has a primary panel on the left side containing buttons for the main menu, adding structures, adding objects, activating night mode, and switching between normal and plan view (the plan mode aims to eliminate the texturing of elements to facilitate the process of accurately measuring the space required for object placement). On the right side, there is a grid where the user can place structures and objects. These structures and objects can be selected using the Select Structure and Select Object buttons and placed directly on the grid using the left mouse click.

2.3. Application Menu (MenuPanel.java)

It provides options for exporting the scheme in JPG format, saving (exporting the .txt file with the current grid configuration), returning to the main menu, and exiting the application.

2.4. ExportJPG.java

Provides functionality for exporting the scheme from the main panel in JPG format.

2.5. ExportTXT.java

Provides functionality for saving progress made in building the project (exporting the scheme from the main panel in .txt format).

2.6. Errors.java

The file that returns error messages that may occur during interaction with the application.

2.7. Other files

The application also contains 2 folders with images related to objects, both for normal mode and plan mode (practically, the objects in the grid are updated with the respective sprites when switching from normal mode to plan mode and vice versa).

Screenshots:

![image](https://github.com/Stephan-Jacob/RoomMagine/assets/83079613/e8ed3c1b-84df-4ecc-9096-73f200be1f58)
![image](https://github.com/Stephan-Jacob/RoomMagine/assets/83079613/bc9b14c0-d1ca-460d-8344-fda36193afc4)
![image](https://github.com/Stephan-Jacob/RoomMagine/assets/83079613/bb56e51b-6512-49a9-99b8-8d5d2c9e6e65)
![image](https://github.com/Stephan-Jacob/RoomMagine/assets/83079613/0a19ae0f-7b5d-4ff3-868a-2432ec4f08e4)
![image](https://github.com/Stephan-Jacob/RoomMagine/assets/83079613/837e70d7-891a-4c44-a131-b2f529b1ef1b)
![image](https://github.com/Stephan-Jacob/RoomMagine/assets/83079613/fbb9c344-2fbf-43c7-b68d-64e982a51587)
![image](https://github.com/Stephan-Jacob/RoomMagine/assets/83079613/d640cf0b-e24d-4ec5-84ad-926631630e5f)
![image](https://github.com/Stephan-Jacob/RoomMagine/assets/83079613/5462b133-bb4d-4e63-9d6f-fea1d7179118)
![image](https://github.com/Stephan-Jacob/RoomMagine/assets/83079613/e2664104-a2e2-4e90-a137-b8f9b282c7f0)
![image](https://github.com/Stephan-Jacob/RoomMagine/assets/83079613/b4f99471-f7e7-4fd1-a896-069256dafdb4)


