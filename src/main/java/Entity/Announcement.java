package Entity;

public class Announcement {
    private final String id;
    private final String title;
    private final String content;
    private final String author;

    public Announcement(String id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getAuthor() { return author; }
}

