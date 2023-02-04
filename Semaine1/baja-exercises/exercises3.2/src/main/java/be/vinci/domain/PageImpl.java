package be.vinci.domain;

import be.vinci.views.Views;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL) // ignore all null fields in order to avoid sending props not linked to a
// JSON view

@JsonDeserialize(as = PageImpl.class)
class PageImpl implements Page {
    @JsonView(Views.Public.class)
    private Integer id;
    @JsonView(Views.Public.class)
    private String title;
    @JsonView(Views.Internal.class)
    private String uri;
    @JsonView(Views.Internal.class)
    private String content;
    @JsonView(Views.Internal.class)
    private final static String[] POSSIBLE_PUBLICATION_STATUSES = {"hidden", "published"};
    @JsonView(Views.Internal.class)
    private String publicationStatus;
    @JsonView(Views.Internal.class)
    private Integer authorId;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
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
    public Integer getAuthorId() {
        return authorId;
    }

    @Override
    public void setAuthorId(Integer authorId) {
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
