import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
public class Reader implements Runnable {
    private String name;
    private String location;
    private Library library;
    private Room room;
    private ArrayList<Book> books;
    private HashMap<String, int[]> movement = new HashMap<>();

    // Конструктор класса Reader
    Reader(Library library, Room room, String name) {
        this.library = library;
        this.room = room;
        this.name = name;
        location = "дом";
        books = new ArrayList<>();

        // Настройка возможных перемещений читателя между локациями
        movement.put("дом", new int[]{6, 8});
        movement.put("библиотека", new int[]{1, 5, 5, 5,5,5,7,7, 7});
        movement.put("здание", new int[]{0, 0, 2, 2, 2, 2, 2,0,0,2,2});
        movement.put("читальный зал", new int[]{3, 6, 6, 6,6,6,6,6});
    }

    // Метод выбора случайного действия
    private int choose() {
        for (String loc : movement.keySet()) {
            if (loc.equals(location)) {
                int[] m = movement.get(loc);
                return m[new Random().nextInt(m.length)];
            }
        }
        return -1;
    }

    // Метод, который выполняется при запуске потока
    public void run() {
        try {
            while (true) {
                int action = choose();
                switch (action) {
                    case 0:
                        library.enter(Reader.this);
                        Thread.sleep(2000);
                        break;
                    case 1:
                        library.exit(Reader.this);
                        Thread.sleep(2000);
                        break;
                    case 2:
                        room.enter(Reader.this);
                        Thread.sleep(3000);
                        break;
                    case 3:
                        room.exit(Reader.this);
                        Thread.sleep(3000);
                        break;
                    case 4:
                        setLocation("дом");
                        System.out.println("Читатель " + this.name + " дома");
                        Thread.sleep(5000);
                        break;
                    case 5:
                        if (library.getBooks().size() > 0) {
                            library.giveBook(Reader.this, library.getBooks().
                                    get(new Random().nextInt(library.getBooks().size())));
                            Thread.sleep(2000);

                            // Если книга предназначена только для чтения в зале, читатель начинает ее чтение
                            if (Reader.this.books.get(books.size() - 1).isReaderRoomOnly()) {
                                readAtReadingRoom(Reader.this.books.get(books.size() - 1));
                            }
                        }
                        break;
                    case 6:
                        if (Reader.this.books.size() > 0) {
                            System.out.println("Читатель " + this.name + " читает");
                            Thread.sleep(10000);
                        } else break;
                        break;
                    case 7:
                        if (Reader.this.books.size() > 0) {
                            library.returnBook(Reader.this, Reader.this.getBooks().
                                    get(new Random().nextInt(Reader.this.
                                            getBooks().size())));
                        } else break;
                        break;
                    case 8:
                        Reader.this.setLocation("здание");
                        System.out.println("Читатель " + Reader.this.name + " вошел(а) в здание");
                        break;
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    // Метод чтения книги в читательном зале
    private void readAtReadingRoom(Book book) {
        try {
            setLocation("читальный зал");
            System.out.println("Читатель " + this.name + " читает книгу " + book.getId() + ", которую можно читать только в зале");
            Thread.sleep(10000);
            setLocation("библиотека");
            library.returnBook(Reader.this, book);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Геттер для имени посетителя
    String getName() {
        return name;
    }

    // Геттер для списка книг, взятых посетителем
    ArrayList<Book> getBooks() {
        return books;
    }

    // Сеттер для списка книг, взятых посетителем
    void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    // Сеттер для установки текущей локации посетителя
    void setLocation(String location) {
        this.location = location;
    }
}