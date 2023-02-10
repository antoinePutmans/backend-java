package be.vinci.domain;

import java.util.Objects;

public class Text {




    private int id;

    private String content;

    private String level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        if (!level.equalsIgnoreCase("low") && !level.equalsIgnoreCase("medium") && !level.equalsIgnoreCase("high"))
            throw new IllegalArgumentException();
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Text[" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", level=" + level +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return id == text.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
