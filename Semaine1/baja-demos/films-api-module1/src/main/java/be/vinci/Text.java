package be.vinci;

import java.util.Objects;

public class Text {
    public enum Level{
        EASY("easy"),MEDIUM("medium"),HARD("hard");

        private String lowerCase;
        Level(String lowerCase) {
            this.lowerCase = lowerCase;
        }

        public String getLowerCase() {
            return lowerCase;
        }
    }

    public Text() {
    }

    public Text(int id, String content, Level level) {
        this.id = id;
        this.content = content;
        this.level = level;
    }

    private int id;

    private String content;

    private Level level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
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
