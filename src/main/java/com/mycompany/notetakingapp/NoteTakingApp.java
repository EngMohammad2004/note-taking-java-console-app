package com.mycompany.notetakingapp;
import java.util.List;
import java.util.Scanner;

enum PageState {
    LOGIN,
    MAIN_MENU,
    VIEW_ALL_NOTES,
    VERIFY_PASSWORD,
    NOTE_OPTIONS,
    DISPLAY_NOTE,
    EDIT_NOTE,
    VIEW_ALL_IMAGES,
    VIEW_ALL_SKETCHS,
    EXIT
}



public class NoteTakingApp {
    static String rootDirectory = "D:\\الكلية\\OOP\\Java Projects\\Note Takeing\\NoteTakingApp\\rootDirectory";
    static Scanner scanner = new Scanner(System.in);
    static UserManager userManager = new UserManager(rootDirectory);
    static User activeUser;
    static Note activeNote;
    static SecureNote activeSecureNote;


    public static PageState loginPage() {
        
        while (true) {
            System.out.println("=== Login Page ===");
            System.out.println("1. Log in");
            System.out.println("2. Create a new account");
            System.out.println("3. Exit");
            System.out.print("Your choice: ");

            int choise = scanner.nextInt();
            scanner.nextLine();
            String username, password;

            switch (choise) {
                case 1:
                    System.out.println("Enter Username: ");
                    username = scanner.nextLine();
                    System.out.println("Enter Your Password: ");
                    password = scanner.nextLine();
                    if (userManager.login(username, password)) {
                        System.out.println("Logedin Successfully!");
                        activeUser = userManager.getUser(username);
                        return PageState.MAIN_MENU;
                    } else {
                        System.out.println("Username Or Password Is Not Correct!");
                    }
                    break;
    
                case 2:
                    System.out.println("Pick A Username: ");
                    username = scanner.nextLine();
                    System.out.println("Enter A Password: ");
                    password = scanner.nextLine();
                    if (userManager.createUser(username, password)) {
                        System.out.println("User added successfully!");
                        return PageState.LOGIN;
                    } else {
                        System.out.println("User already exists: " + username);
                    }
                    break;
    
                case 3:
                    System.out.println("Exit");
                    return PageState.EXIT;
    
                default:
                    System.out.println("Invalid Choise, Please Try Again.");
            }
        }
    }
    
    public static PageState mainMenuPage() {
        
        while (true) {
            System.out.println("=== Main Menu ===");
            System.out.println("1. View all notes");
            System.out.println("2. Create a new note");
            System.out.println("3. Create a new secure note");
            System.out.println("4. Log out");
            System.out.println("5. Exit");
            System.out.print("Your choice: ");

            int choise = scanner.nextInt();
            scanner.nextLine();

            String title, password;

            switch (choise) {
                case 1:
                    return PageState.VIEW_ALL_NOTES;

                case 2:
                    System.out.println("Enter Note Title: ");
                    title = scanner.nextLine();
                    Note newNote = activeUser.createNote(title);
                    activeNote = activeUser.getNote(newNote.getTitle());
                    activeSecureNote = null;
                    return PageState.VIEW_ALL_NOTES;

                case 3:
                    System.out.println("Enter Note Title: ");
                    title = scanner.nextLine();
                    System.out.println("Enter Note Password: ");
                    password = scanner.nextLine();
                    SecureNote newSecureNote = activeUser.createSecureNote(title, password);
                    activeSecureNote = activeUser.getSecureNote(newSecureNote.getTitle());
                    activeNote = activeSecureNote;
                    return PageState.VIEW_ALL_NOTES;

                case 4:
                    System.out.println("Logged out successfully!");
                    return PageState.LOGIN;

                case 5:
                    System.out.println("Exiting the application...");
                    return PageState.EXIT;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    
    public static PageState viewAllNotesPage() {
        System.out.println("=== All Notes ===");
        List<String> noteList = activeUser.listAllNotes();
        for(String noteTitle : noteList) {
            System.out.println((noteList.indexOf(noteTitle) + 1) + ". " + noteTitle);
        }
        System.out.println("0. Main Menu");
        System.out.print("Your choise: ");

        int choise = scanner.nextInt();
        scanner.nextLine();

        if(choise != 0) {
            activeNote = activeUser.getNote(noteList.get(choise - 1));
            return checkForSecurity();
        } else {
            return PageState.MAIN_MENU;
        }

    }

    public static PageState viewAllImagesPage() {
        System.out.println("=== All Images ===");
        List<Image> imageList = activeNote.getImages();
        for(Image image : imageList) {
            System.out.println((imageList.indexOf(image) + 1) + ". " + image.getImageName());
        }
        System.out.println("0. Back to note");
        System.out.print("Your choise: ");

        int choise = scanner.nextInt();
        scanner.nextLine();

        if(choise != 0) {
            System.out.println(imageList.get(choise - 1).getPath());
            imageList.get(choise-1).displayImage();
            return PageState.VIEW_ALL_IMAGES;
        } else {
            return PageState.NOTE_OPTIONS;
        }
        
    }

    public static PageState viewAllSketchsPage() {
        System.out.println("=== All Sketchs ===");
        List<Sketch> sketchList = activeNote.getSketchs();
        for(Sketch sketch : sketchList) {
            System.out.println((sketchList.indexOf(sketch) + 1) + ". " + sketch.getName());
        }
        System.out.println("0. Back to note");
        System.out.print("Your choise: ");

        int choise = scanner.nextInt();
        scanner.nextLine();

        if(choise != 0) {
            sketchList.get(choise-1).displaySketch();
            return PageState.VIEW_ALL_SKETCHS;
        } else {
            return PageState.NOTE_OPTIONS;
        }
    }

    public static PageState displayNotePage() {
        System.out.println("=== Note Content ===\n");
        System.out.println("Title: " + activeNote.getTitle() + "\n");
        System.out.println("Content:");
        System.out.println(activeNote.getContent() + "\n");
        List<Image> imageList = activeNote.getImages();
        System.out.println("Number Of Images: " + imageList.size());
        List<Sketch> sketchList = activeNote.getSketchs();
        System.out.println("Number Of Sketchs: " + sketchList.size() + "\n");
    
        System.out.println("Press Enter To Go Back");
        scanner.nextLine();
        return PageState.NOTE_OPTIONS;
    }
    
    private static PageState noteOptionsPage() {
        while (true) {
            System.out.println("=== Note Options ===\n");
            System.out.println("1. Display note");
            System.out.println("2. Edit content");
            System.out.println("3. Add image");
            System.out.println("4. Add Sketch");
            System.out.println("5. View all images");
            System.out.println("6. View all sketchs");
            System.out.println("7. Delete note");
            System.out.println("0. Back to notes");
            System.out.print("Your choice: ");
    
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            String content, path;
    
            switch (choice) {
                case 1:
                    return PageState.DISPLAY_NOTE;
    
                case 2:
                    while (true) {
                        System.out.println("Current Content: ");
                        System.out.println(activeNote.getContent() + "\n");
                        System.out.println("Enter The New Content: ");
                        content = scanner.nextLine();
                        switch (confirm("Do you sure about saving changes?")) {
                            case "y":
                                activeNote.setContent(content);
                                activeNote.saveNote();
                                System.out.println("\nSaved Successfully!\n");
                                return PageState.NOTE_OPTIONS;
    
                            case "n":
                                System.out.println("\nNothing Changed!\n");
                                return PageState.NOTE_OPTIONS;
    
                            default:
                                System.out.println("Invalid Choise! Please Try Again.");
                                break;
                        }
                    }
    
                case 3:
                    while (true) {
                        System.out.println("Enter The Image's Path: ");
                        path = scanner.nextLine();
                        switch (confirm("Do you sure about saving changes?")) {
                            case "y":
                                activeNote.getImages().add(new Image(path));
                                activeNote.saveNote();
                                System.out.println("\nSaved Successfully!\n");
                                return PageState.NOTE_OPTIONS;
    
                            case "n":
                                System.out.println("\nNothing Changed!\n");
                                return PageState.NOTE_OPTIONS;
    
                            default:
                                System.out.println("Invalid Choise! Please Try Again.");
                                break;
                        }
                    }
    
                case 4:
                    while (true) {
                        System.out.println("Pick A Name For The Sketch: ");
                        String name = scanner.nextLine();
                        Sketch sketch = new Sketch(name, activeNote.getNoteFolderPath());
                        sketch.drawSketch();
                        switch (confirm("Do you sure about saving changes?")) {
                            case "y":
                                activeNote.getSketchs().add(sketch);
                                activeNote.saveNote();
                                System.out.println("\nSaved Successfully!\n");
                                return PageState.NOTE_OPTIONS;
    
                            case "n":
                                System.out.println("\nNothing Changed!\n");
                                return PageState.NOTE_OPTIONS;
    
                            default:
                                System.out.println("Invalid Choise! Please Try Again.");
                                break;
                        }
                    }
    
                case 5:
                    return PageState.VIEW_ALL_IMAGES;
    
                case 6:
                    return PageState.VIEW_ALL_SKETCHS;
    
                case 7:
                    while (true) {
                        switch (confirm("Do you sure about deleting note?")) {
                            case "y":
                                System.out.println("\n" + activeUser.deleteNote(activeNote).getTitle() + " was deleted successfully!\n");
                                return PageState.VIEW_ALL_NOTES;
    
                            case "n":
                                System.out.println("\nNothing Changed!\n");
                                return PageState.NOTE_OPTIONS;
    
                            default:
                                System.out.println("Invalid Choise! Please Try Again.");
                                break;
                        }
                    }
    
                case 0:
                    return PageState.VIEW_ALL_NOTES;
    
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
    
    public static String confirm(String message) {
        System.out.println(message);
        System.out.println("y) Yes  n) No");
        return scanner.nextLine();
    }

    public static PageState checkForSecurity() {
        if (activeNote.getClass().getSimpleName().equals("SecureNote")) {
            activeSecureNote = (SecureNote)activeNote;
            return PageState.VERIFY_PASSWORD;
        } else {
            return PageState.NOTE_OPTIONS;
        }
    }

    public static PageState verifyPasswordPage() {
        System.out.print("Enter Note's Security Key: ");
        String password = scanner.nextLine();
        if (activeSecureNote.verifyPassword(password)) {
            return PageState.NOTE_OPTIONS;
        } else {
            System.out.println("Wrong password, please try again.");
            return PageState.VERIFY_PASSWORD;
        }
    }

    public static void main(String[] args) {
        if (!FileManager.isFileExists(rootDirectory)) {
            System.out.println("Error: Root directory does not exist!");
            System.exit(1);
        }
        userManager.loadUsers();

        PageState currentPage = PageState.LOGIN;
    
        while (currentPage != PageState.EXIT) {
            switch (currentPage) {
                case LOGIN:
                    currentPage = loginPage();
                    break;
    
                case MAIN_MENU:
                    currentPage = mainMenuPage();
                    break;
    
                case VIEW_ALL_NOTES:
                    currentPage = viewAllNotesPage();
                    break;

                case NOTE_OPTIONS:
                    currentPage = noteOptionsPage();
                    break;
                    
                case DISPLAY_NOTE:
                    currentPage = displayNotePage();
                    break;

                case VIEW_ALL_IMAGES:
                    currentPage = viewAllImagesPage();
                    break;

                case VIEW_ALL_SKETCHS:
                    currentPage = viewAllSketchsPage();
                    break;

                case VERIFY_PASSWORD:
                    currentPage = verifyPasswordPage();
                    break;

                default:
                    System.out.println("Unexpected page state!");
                    currentPage = PageState.EXIT;
            }
        }
    
        System.out.println("Program terminated.");
    }
    
    
    
}
