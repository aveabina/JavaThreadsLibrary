import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Book> books = createBooks(); // Создание списка книг
        Library library = new Library(); // Создание объекта библиотеки
        Room room = new Room(); // Создание объекта читательного зала
        library.setBooks(books); // Установка списка книг в библиотеке

        // Создание потоков для каждого читателя
        Thread reader1 = new Thread(new Reader(library, room, "Болдырева"), "reader1");
        Thread reader2 = new Thread(new Reader(library, room, "Соков"), "reader2");
        Thread reader3 = new Thread(new Reader(library, room, "Годьгаев"), "reader3");
        Thread reader4 = new Thread(new Reader(library, room, "Доржиева"), "reader4");
        Thread reader5 = new Thread(new Reader(library, room, "Бадмаева"), "reader5");
        Thread reader6 = new Thread(new Reader(library, room, "Бадмаева"), "reader6");
        Thread reader7 = new Thread(new Reader(library, room, "Алексеева"), "reader7");

        // Запуск потоков
        reader1.start();
        reader2.start();
        reader3.start();
        reader4.start();
        reader5.start();
    }

    // Метод создания списка книг
    private static ArrayList<Book> createBooks() {
        ArrayList<Book> books = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            String name = "«Книга №" + i +"»";
            Book book = new Book(name, new Random().nextBoolean());
            books.add(book);
        }
        return books;
    }
}