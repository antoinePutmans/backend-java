package be.vinci;

import java.util.Arrays;

public class Text {

  private int id;
  private String content;
  private final static String[] POSSIBLE_LEVELS = {"easy", "medium", "hard"};
  private String level;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = Arrays.stream(POSSIBLE_LEVELS).filter(possibleLevel -> possibleLevel.equals(level))
        .findFirst()
        .orElse(null);

  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    Text text = (Text) o;
    return id == text.id;
  }

  @Override
  public int hashCode() {
    return id;
  }

  @Override
  public String toString() {
    return "Text [id=" + id + ", content=" + content + ", level=" + level + "]";
  }

}
