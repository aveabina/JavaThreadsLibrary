class Book {
    private String id; // Идентификатор книги
    private boolean readOnlyAtRoom; // Флаг, указывающий, можно ли читать книгу только в зале

    Book(String id, boolean readerRoomOnly) {
        this.id = id;
        this.readOnlyAtRoom = readerRoomOnly;
    }

    String getId() {
        return id;
    }

    boolean isReaderRoomOnly() {
        return readOnlyAtRoom;
    }
}