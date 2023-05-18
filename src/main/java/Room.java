public class Room implements Action {
    private int readers = 0;

    // Метод, позволяющий посетителю войти в зал
    public synchronized void enter(Reader reader) {
        int maxInRoom = 5; // Максимальное количество посетителей в зале
        while (readers >= maxInRoom) { // Пока достигнуто максимальное количество посетителей
            try {
                wait(); // Ожидание, пока не освободится место в зале
            } catch (InterruptedException ignored) {
            }
        }
        readers++; // Увеличиваем счетчик посетителей
        reader.setLocation("зал"); // Устанавливаем местоположение посетителя в зал
        System.out.println("Читатель " + reader.getName() + " вошел(а) в читальный зал");
        System.out.println("Читателей в читальном зале: " + readers);
        notify(); // Уведомляем другие потоки, что освободилось место в зале и они могут попытаться войти
    }

    // Метод, позволяющий посетителю покинуть зал
    public synchronized void exit(Reader reader) {
        int minReadersInRoom = 0; // Минимальное количество посетителей в зале
        while (readers <= minReadersInRoom) { // Пока в зале нет посетителей
            try {
                wait(); // Ожидание, пока появятся посетители в зале
            } catch (InterruptedException ignored) {
            }
        }
        readers--; // Уменьшаем счетчик посетителей
        reader.setLocation("здание"); // Устанавливаем местоположение посетителя в здание
        System.out.println("Читатель " + reader.getName() + " покинул(а) читальный зал");
        System.out.println("Читателей в читальном зале: " + readers);
        notify(); // Уведомляем другие потоки, что посетитель покинул зал и другие могут попытаться войти
    }
}