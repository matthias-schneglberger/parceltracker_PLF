package at.htlgkr.parceltracker;

import java.time.LocalDateTime;

public class Parcel {
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private Category category;

    public Parcel(String title, String description, LocalDateTime start, LocalDateTime end, Category category) {
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
