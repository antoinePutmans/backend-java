package be.vinci.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = PageImpl.class)
public interface Page {
    Integer getId();

    void setId(Integer id);

    String getTitle();

    void setTitle(String title);

    String getUri();

    void setUri(String uri);

    String getContent();

    void setContent(String content);

    String getPublicationStatus();

    void setPublicationStatus(String publicationStatus);

    Integer getAuthorId();

    void setAuthorId(Integer authorId);
}
