package be.vinci.services;

import be.vinci.domain.Page;
import be.vinci.domain.User;
import be.vinci.services.utils.Json;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class PageDataServiceImpl implements PageDataService {

  private static final String COLLECTION_NAME = "pages";
  private static Json<Page> jsonDB = new Json<>(Page.class);

  @Override
  public List<Page> getAll() {
    var pages = jsonDB.parse(COLLECTION_NAME);
    return pages.stream().filter(item ->
            item.getPublicationStatus().contentEquals("published"))
        .toList();

  }

  @Override
  public List<Page> getAll(User authenticatedUser) {
    var pages = jsonDB.parse(COLLECTION_NAME);
    return pages.stream().filter(item -> item.getPublicationStatus().contentEquals("published")
        || item.getAuthorId() == authenticatedUser.getId()).toList();

  }

  @Override
  public Page getOne(int id) {
    var pages = jsonDB.parse(COLLECTION_NAME);
    return pages.stream().filter(item -> (item.getId() == id)
            && (item.getPublicationStatus().contentEquals("published")))
        .findAny().orElse(null);
  }

  @Override
  public Page getOne(int id, User authenticatedUser) {
    var pages = jsonDB.parse(COLLECTION_NAME);
    return pages.stream().filter(item -> (item.getId() == id)
            && (item.getPublicationStatus().contentEquals("published")
            || item.getAuthorId() == authenticatedUser.getId()))
        .findAny().orElse(null);
  }

  @Override
  public Page createOne(Page page, User authenticatedUser) {
    var pages = jsonDB.parse(COLLECTION_NAME);
    page.setId(nextPageId());
    // escape dangerous chars to protect against XSS attacks
    page.setTitle(StringEscapeUtils.escapeHtml4(page.getTitle()));
    page.setUri(StringEscapeUtils.escapeHtml4(page.getUri()));
    page.setContent(StringEscapeUtils.escapeHtml4(page.getContent()));
    page.setAuthorId(authenticatedUser.getId());
    pages.add(page);

    jsonDB.serialize(pages, COLLECTION_NAME);
    return page;
  }


  @Override
  public Page deleteOne(int id, User authenticatedUser) {
    Page pageToDelete = getOne(id, authenticatedUser);
    var pages = jsonDB.parse(COLLECTION_NAME);
      if (pageToDelete == null) {
          return null;
      }
      if (pageToDelete.getAuthorId() != authenticatedUser.getId()) {
          throw new IllegalStateException("Forbidden");
      }
    pages.remove(pageToDelete);
    jsonDB.serialize(pages, COLLECTION_NAME);
    return pageToDelete;
  }

  @Override
  public Page updateOne(Page page, int id, User authenticatedUser) {
    Page pageToUpdate = getOne(id, authenticatedUser);
    var pages = jsonDB.parse(COLLECTION_NAME);

      if (pageToUpdate == null) {
          return null;
      }
      if (pageToUpdate.getAuthorId() != authenticatedUser.getId()) {
          throw new IllegalStateException("Forbidden");
      }

    pageToUpdate.setId(id);
      if (pageToUpdate == null) {
          return null;
      }
    // escape dangerous chars to protect against XSS attacks
      if (page.getTitle() != null) {
          pageToUpdate.setTitle(StringEscapeUtils.escapeHtml4(page.getTitle()));
      }
      if (page.getUri() != null) {
          pageToUpdate.setUri(StringEscapeUtils.escapeHtml4(page.getUri()));
      }
      if (page.getContent() != null) {
          pageToUpdate.setContent(StringEscapeUtils.escapeHtml4(page.getContent()));
      }
      if (page.getAuthorId() != 0) {
          pageToUpdate.setAuthorId(page.getAuthorId());
      }
      if (page.getPublicationStatus() != null) {
          pageToUpdate.setPublicationStatus(page.getPublicationStatus());
      }
    pages.remove(page); // thanks to equals(), pages is found via its id
    pages.add(pageToUpdate);
    jsonDB.serialize(pages, COLLECTION_NAME);
    return pageToUpdate;
  }

  @Override
  public int nextPageId() {
    var pages = jsonDB.parse(COLLECTION_NAME);
      if (pages.size() == 0) {
          return 1;
      }
    return pages.get(pages.size() - 1).getId() + 1;
  }
}
