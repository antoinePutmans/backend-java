package be.vinci.domain;

import java.util.Arrays;

class PageImpl implements Page {
    private int id;
    private String title;
    private String uri;
    private String content;
    private final static String[] POSSIBLE_PUBLICATION_STATUSES = {"hidden", "published"};
    private String publicationStatus;
    private int authorId;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getPublicationStatus() {
        return publicationStatus;
    }

    @Override
    public void setPublicationStatus(String publicationStatus) {
        this.publicationStatus = Arrays.stream(POSSIBLE_PUBLICATION_STATUSES).filter(possibleStatus -> possibleStatus.equals(publicationStatus)).findFirst()
                .orElse(null);
    }

    @Override
    public int getAuthorId() {
        return authorId;
    }

    @Override
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageImpl page = (PageImpl) o;

        return id == page.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Page [id=" + id + ", title=" + title + ", uri=" + uri + ", content=" + content + ", publicationStatus="
                + publicationStatus + ", authorId=" + authorId + "]";
    }


}
