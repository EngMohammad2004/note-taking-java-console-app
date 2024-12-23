package com.mycompany.notetakingapp;
import java.util.List;
import java.util.Scanner;

// تعريف Enum لحالات الصفحات التي يمكن أن يكون فيها التطبيق
enum PageState {
    LOGIN,              // صفحة تسجيل الدخول
    MAIN_MENU,          // الصفحة الرئيسية
    VIEW_ALL_NOTES,     // عرض جميع الملاحظات
    VERIFY_PASSWORD,    // التحقق من كلمة السر
    NOTE_OPTIONS,       // خيارات الملاحظة
    DISPLAY_NOTE,       // عرض الملاحظة
    EDIT_NOTE,          // تعديل الملاحظة
    VIEW_ALL_IMAGES,    // عرض جميع الصور
    VIEW_ALL_SKETCHS,   // عرض جميع الرسومات
    EXIT                // الخروج من التطبيق
}

// الفئة الرئيسية للتطبيق
public class NoteTakingApp {
    static String rootDirectory = "C:\\Users\\10\\Desktop\\rootDirectory";   // مسار الدليل الجذري للمستخدم
    static Scanner scanner = new Scanner(System.in);     // كائن Scanner لأخذ المدخلات من المستخدم
    static UserManager userManager = new UserManager(rootDirectory);  // مدير المستخدمين
    static User activeUser;   // المستخدم النشط
    static Note activeNote;   // الملاحظة النشطة
    static SecureNote activeSecureNote;  // الملاحظة الآمنة النشطة

    // صفحة تسجيل الدخول
    public static PageState loginPage() {
        while (true) {
            System.out.println("=== Login Page ===");
            System.out.println("1. Login");
            System.out.println("2. Create New Account");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            int choise = scanner.nextInt();
            scanner.nextLine();  // تنظيف السطر

            String username, password;

            switch (choise) {
                case 1:
                    System.out.println("Enter username: ");
                    username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    password = scanner.nextLine();
                    if (userManager.login(username, password)) {
                        System.out.println("Login successful!");
                        activeUser = userManager.getUser(username);  // تعيين المستخدم النشط
                        return PageState.MAIN_MENU;  // الانتقال إلى الصفحة الرئيسية
                    } else {
                        System.out.println("Invalid username or password!");
                    }
                    break;

                case 2:
                    System.out.println("Choose a username: ");
                    username = scanner.nextLine();
                    System.out.println("Enter a password: ");
                    password = scanner.nextLine();
                    if (userManager.createUser(username, password)) {
                        System.out.println("User created successfully!");
                        return PageState.LOGIN;  // العودة إلى صفحة تسجيل الدخول
                    } else {
                        System.out.println("User already exists: " + username);
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    return PageState.EXIT;  // الخروج من التطبيق

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    // الصفحة الرئيسية بعد تسجيل الدخول
    public static PageState mainMenuPage() {
        while (true) {
            System.out.println("=== Main Menu ===");
            System.out.println("1. View All Notes");
            System.out.println("2. Create New Note");
            System.out.println("3. Create Secure Note");
            System.out.println("4. Logout");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            int choise = scanner.nextInt();
            scanner.nextLine();

            String title, password;

            switch (choise) {
                case 1:
                    return PageState.VIEW_ALL_NOTES;  // الانتقال إلى صفحة عرض جميع الملاحظات

                case 2:
                    System.out.println("Enter note title: ");
                    title = scanner.nextLine();
                    Note newNote = activeUser.createNote(title);  // إنشاء ملاحظة جديدة
                    activeNote = activeUser.getNote(newNote.getTitle());  // تعيين الملاحظة النشطة
                    activeSecureNote = null;  // إزالة الملاحظة الآمنة
                    return PageState.VIEW_ALL_NOTES;  // العودة لعرض جميع الملاحظات

                case 3:
                    System.out.println("Enter note title: ");
                    title = scanner.nextLine();
                    System.out.println("Enter note password: ");
                    password = scanner.nextLine();
                    SecureNote newSecureNote = activeUser.createSecureNote(title, password);  // إنشاء ملاحظة آمنة
                    activeSecureNote = activeUser.getSecureNote(newSecureNote.getTitle());  // تعيين الملاحظة الآمنة
                    activeNote = activeSecureNote;  // تعيين الملاحظة النشطة كملاحظة آمنة
                    return PageState.VIEW_ALL_NOTES;  // العودة لعرض جميع الملاحظات

                case 4:
                    System.out.println("Logged out successfully!");
                    return PageState.LOGIN;  // العودة إلى صفحة تسجيل الدخول

                case 5:
                    System.out.println("Exiting the application...");
                    return PageState.EXIT;  // الخروج من التطبيق

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    
    // صفحة عرض جميع الملاحظات
public static PageState viewAllNotesPage() {
    System.out.println("=== All Notes ===");
    List<String> noteList = activeUser.listAllNotes();  // الحصول على قائمة الملاحظات
    for(String noteTitle : noteList) {
        System.out.println((noteList.indexOf(noteTitle) + 1) + ". " + noteTitle);  // عرض عناوين الملاحظات
    }
    System.out.println("0. Return to Main Menu");
    System.out.print("Choose: ");

    int choise = scanner.nextInt();
    scanner.nextLine();

    if(choise != 0) {
        activeNote = activeUser.getNote(noteList.get(choise - 1));  // تعيين الملاحظة النشطة
        return checkForSecurity();  // التحقق إذا كانت الملاحظة آمنة
    } else {
        return PageState.MAIN_MENU;  // العودة إلى الصفحة الرئيسية
    }
}

// صفحة عرض جميع الصور في الملاحظة
public static PageState viewAllImagesPage() {
    System.out.println("=== All Images ===");
    List<Image> imageList = activeNote.getImages();  // الحصول على قائمة الصور
    for(Image image : imageList) {
        System.out.println((imageList.indexOf(image) + 1) + ". " + image.getImageName());  // عرض أسماء الصور
    }
    System.out.println("0. Return to Note");
    System.out.print("Choose: ");

    int choise = scanner.nextInt();
    scanner.nextLine();

    if(choise != 0) {
        System.out.println(imageList.get(choise - 1).getPath());  // عرض مسار الصورة
        imageList.get(choise-1).displayImage();  // عرض الصورة
        return PageState.VIEW_ALL_IMAGES;  // العودة إلى عرض الصور
    } else {
        return PageState.NOTE_OPTIONS;  // العودة إلى خيارات الملاحظة
    }
}

// صفحة عرض جميع الرسومات في الملاحظة
public static PageState viewAllSketchsPage() {
    System.out.println("=== All Sketches ===");
    List<Sketch> sketchList = activeNote.getSketchs();  // الحصول على قائمة الرسومات
    for(Sketch sketch : sketchList) {
        System.out.println((sketchList.indexOf(sketch) + 1) + ". " + sketch.getName());  // عرض أسماء الرسومات
    }
    System.out.println("0. Return to Note");
    System.out.print("Choose: ");

    int choise = scanner.nextInt();
    scanner.nextLine();

    if(choise != 0) {
        sketchList.get(choise-1).displaySketch();  // عرض الرسم
        return PageState.VIEW_ALL_SKETCHS;  // العودة إلى عرض الرسومات
    } else {
        return PageState.NOTE_OPTIONS;  // العودة إلى خيارات الملاحظة
    }
}

// صفحة عرض محتوى الملاحظة
public static PageState displayNotePage() {
    System.out.println("=== Note Content ===\n");
    System.out.println("Title: " + activeNote.getTitle() + "\n");
    System.out.println("Content:");
    System.out.println(activeNote.getContent() + "\n");  // عرض محتوى الملاحظة
    List<Image> imageList = activeNote.getImages();
    System.out.println("Number of Images: " + imageList.size());
    List<Sketch> sketchList = activeNote.getSketchs();
    System.out.println("Number of Sketches: " + sketchList.size() + "\n");

    System.out.println("Press Enter to return");
    scanner.nextLine();
    return PageState.NOTE_OPTIONS;  // العودة إلى خيارات الملاحظة
}

// صفحة خيارات الملاحظة
private static PageState noteOptionsPage() {
    while (true) {
        System.out.println("=== Note Options ===\n");
        System.out.println("1. View Note");
        System.out.println("2. Edit Content");
        System.out.println("3. Add Image");
        System.out.println("4. Add Sketch");
        System.out.println("5. View All Images");
        System.out.println("6. View All Sketches");
        System.out.println("7. Delete Note");
        System.out.println("0. Return to Notes");
        System.out.print("Choose: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String content, path;

        switch (choice) {
            case 1:
                return PageState.DISPLAY_NOTE;  // عرض الملاحظة

            case 2:
                while (true) {
                    System.out.println("Current Content: ");
                    System.out.println(activeNote.getContent() + "\n");
                    System.out.println("Enter New Content: ");
                    content = scanner.nextLine();
                    switch (confirm("Are you sure you want to save changes?")) {  // تأكيد حفظ التغييرات
                        case "y":
                            activeNote.setContent(content);  // تعديل المحتوى
                            activeNote.saveNote();  // حفظ الملاحظة
                            System.out.println("\nChanges saved successfully!\n");
                            return PageState.NOTE_OPTIONS;  // العودة إلى خيارات الملاحظة

                        case "n":
                            System.out.println("\nNo changes made!\n");
                            return PageState.NOTE_OPTIONS;  // العودة إلى خيارات الملاحظة

                        default:
                            System.out.println("Invalid choice! Try again.");
                            break;
                    }
                }

            case 3:
                while (true) {
                    System.out.println("Enter Image Path: ");
                    path = scanner.nextLine();
                    switch (confirm("Are you sure you want to save changes?")) {  // تأكيد حفظ التغييرات
                        case "y":
                            activeNote.getImages().add(new Image(path));  // إضافة صورة
                            activeNote.saveNote();  // حفظ الملاحظة
                            System.out.println("\nChanges saved successfully!\n");
                            return PageState.NOTE_OPTIONS;  // العودة إلى خيارات الملاحظة

                        case "n":
                            System.out.println("\nNo changes made!\n");
                            return PageState.NOTE_OPTIONS;  // العودة إلى خيارات الملاحظة

                        default:
                            System.out.println("Invalid choice! Try again.");
                            break;
                    }
                }

            case 4:
                while (true) {
                    System.out.println("Choose a name for the sketch: ");
                    String name = scanner.nextLine();
                    Sketch sketch = new Sketch(name, activeNote.getNoteFolderPath());  // إنشاء رسم جديد
                    sketch.drawSketch();  // رسم الرسم
                    switch (confirm("Are you sure you want to save changes?")) {  // تأكيد حفظ التغييرات
                        case "y":
                            activeNote.getSketchs().add(sketch);  // إضافة رسم
                            activeNote.saveNote();  // حفظ الملاحظة
                            System.out.println("\nChanges saved successfully!\n");
                            return PageState.NOTE_OPTIONS;  // العودة إلى خيارات الملاحظة

                        case "n":
                            System.out.println("\nNo changes made!\n");
                            return PageState.NOTE_OPTIONS;  // العودة إلى خيارات الملاحظة

                        default:
                            System.out.println("Invalid choice! Try again.");
                            break;
                    }
                }

            case 5:
                return PageState.VIEW_ALL_IMAGES;  // الانتقال إلى عرض جميع الصور

            case 6:
                return PageState.VIEW_ALL_SKETCHS;  // الانتقال إلى عرض جميع الرسومات

            case 7:
                while (true) {
                    switch (confirm("Are you sure you want to delete the note?")) {  // تأكيد الحذف
                        case "y":
                            System.out.println("\nNote deleted successfully!\n");
                            return PageState.VIEW_ALL_NOTES;  // العودة لعرض جميع الملاحظات

                        case "n":
                            System.out.println("\nNo changes made!\n");
                            return PageState.NOTE_OPTIONS;  // العودة إلى خيارات الملاحظة

                        default:
                            System.out.println("Invalid choice! Try again.");
                            break;
                    }
                }

            case 0:
                return PageState.VIEW_ALL_NOTES;  // العودة لعرض جميع الملاحظات

            default:
                System.out.println("Invalid choice! Try again.");
        }
    }
}

// دالة لتأكيد العمليات (مثل الحفظ أو الحذف)
public static String confirm(String message) {
    System.out.println(message);
    System.out.println("y) Yes  n) No");
    return scanner.nextLine();
}

// دالة للتحقق من الأمان (إذا كانت الملاحظة آمنة)
public static PageState checkForSecurity() {
    if (activeNote.getClass().getSimpleName().equals("SecureNote")) {  // إذا كانت الملاحظة آمنة
        activeSecureNote = (SecureNote) activeNote;
        return PageState.VERIFY_PASSWORD;  // الانتقال إلى صفحة التحقق من كلمة السر
    } else {
        return PageState.NOTE_OPTIONS;  // العودة إلى خيارات الملاحظة
    }
}

// صفحة التحقق من كلمة السر للملاحظات الآمنة
public static PageState verifyPasswordPage() {
    System.out.print("Enter Note Password: ");
    String password = scanner.nextLine();
    if (activeSecureNote.verifyPassword(password)) {
        return PageState.NOTE_OPTIONS;  // إذا كانت كلمة السر صحيحة، العودة إلى خيارات الملاحظة
    } else {
        System.out.println("Incorrect password, try again.");
        return PageState.VERIFY_PASSWORD;  // العودة إلى صفحة التحقق
    }
}

// الدالة الرئيسية لتشغيل التطبيق
public static void main(String[] args) {
    if (!FileManager.isFileExists(rootDirectory)) {
        System.out.println("Error: Root directory does not exist!");
        System.exit(1);
    }
    userManager.loadUsers();  // تحميل المستخدمين من الملف

    PageState currentPage = PageState.LOGIN;  // تعيين الصفحة الحالية كصفحة تسجيل الدخول

    while (currentPage != PageState.EXIT) {
        switch (currentPage) {
            case LOGIN:
                currentPage = loginPage();  // الانتقال إلى صفحة تسجيل الدخول
                break;

            case MAIN_MENU:
                currentPage = mainMenuPage();  // الانتقال إلى الصفحة الرئيسية
                break;

            case VIEW_ALL_NOTES:
                currentPage = viewAllNotesPage();  // الانتقال إلى عرض جميع الملاحظات
                break;

            case DISPLAY_NOTE:
                currentPage = displayNotePage();  // عرض محتوى الملاحظة
                break;

            case NOTE_OPTIONS:
                currentPage = noteOptionsPage();  // الانتقال إلى خيارات الملاحظة
                break;

            case VIEW_ALL_IMAGES:
                currentPage = viewAllImagesPage();  // الانتقال إلى عرض جميع الصور
                break;

            case VIEW_ALL_SKETCHS:
                currentPage = viewAllSketchsPage();  // الانتقال إلى عرض جميع الرسومات
                break;

            case VERIFY_PASSWORD:
                currentPage = verifyPasswordPage();  // الانتقال إلى صفحة التحقق من كلمة السر
                break;
            default:
                break;
        }
    }

    scanner.close();  // إغلاق الـScanner بعد الانتهاء
}
}

