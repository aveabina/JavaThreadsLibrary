import java.util.ArrayList;

public class Library implements Action {
    private int readersInRoom = 0; // Количество читателей в библиотеке

    private ArrayList<Book> books; // Список книг в библиотеке

    // Метод входа в библиотеку
    public synchronized void enter(Reader reader) {
        int maxInRoom = 4; // Максимальное количество читателей в библиотеке
        while (readersInRoom >= maxInRoom) { // Пока достигнуто максимальное количество читателей
            try {
                wait(); // Ожидание освобождения места в библиотеке
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        readersInRoom++; // Увеличение количества читателей в библиотеке
        reader.setLocation("библиотека"); // Установка локации читателя в "библиотека"
        System.out.println("Читатель " + reader.getName() + " вошел(а) в библиотеку");
        System.out.println("Читателей в библиотеке: " + readersInRoom);
        notify(); // Уведомление других потоков об освобождении места в библиотеке
    }

    // Метод выхода из библиотеки
    public synchronized void exit(Reader reader) {
        int minInRoom = 0; // Минимальное количество читателей в библиотеке
        while (readersInRoom <= minInRoom) { // Пока нет читателей в библиотеке
            try {
                wait(); // Ожидание появления читателей
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        readersInRoom--; // Уменьшение количества читателей в библиотеке
        reader.setLocation("здание"); // Установка локации читателя в "здание"
        System.out.println("Читатель " + reader.getName() + " вышел(а) из библиотеки");
        System.out.println("Читателей в библиотеке: " + readersInRoom);
        notify(); // Уведомление других потоков об освобождении места в библиотеке
    }

    // Метод выдачи книги читателю
    synchronized void giveBook(Reader reader, Book book) {
        ArrayList<Book> readerBooks = reader.getBooks();
        readerBooks.add(book); // Добавление книги в список книг читателя
        reader.setBooks(readerBooks); // Установка обновленного списка книг читателя
        books.remove(book); // Удаление книги из списка книг в библиотеке
        System.out.println("Читатель " + reader.getName() + " взял(а) книгу " + book.getId());
    }
    // Метод возврата книги в библиотеку
    synchronized void returnBook(Reader reader, Book book) {
        ArrayList<Book> readerBooks = reader.getBooks();
        readerBooks.remove(book); // Удаление книги из списка книг читателя
        reader.setBooks(readerBooks); // Установка обновленного списка книг читателя
        books.add(book); // Добавление книги в список книг в библиотеке
        System.out.println("Читатель " + reader.getName() + " вернул(а) книгу " + book.getId());
    }

    // Метод получения списка книг в библиотеке
    ArrayList<Book> getBooks() {
        return books;
    }

    // Метод установки списка книг в библиотеке
    void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}