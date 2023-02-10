package be.vinci.services;

import be.vinci.domain.Page;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class PageDataService {

    private static final String COLLECTION_NAME = "pages";

    private static Json<Page> jsonDB = new Json<>(Page.class);

    public List<Page> getPages(){
        return jsonDB.parse(COLLECTION_NAME);
    }

    public Page getOne(int id){
        var pages = jsonDB.parse(COLLECTION_NAME);
        Page pageFound = pages.stream()
                .filter(page -> page.getId()==id).findAny().orElse(null);
        return pageFound;
    }

    public Page deleteOne(int id){
        var pages = jsonDB.parse(COLLECTION_NAME);
        Page pageFound = pages.stream()
                .filter(page -> page.getId()==id).findAny().orElse(null);
        pages.remove(pageFound);
        jsonDB.serialize(pages, COLLECTION_NAME);
        return pageFound;
    }

    public Page createOne(Page page){
        var pages = jsonDB.parse(COLLECTION_NAME);
        page.setId(getNextId());
        page.setAuthor(StringEscapeUtils.escapeHtml4(page.getAuthor()));
        page.setContent(StringEscapeUtils.escapeHtml4(page.getContent()));
        page.setURI(StringEscapeUtils.escapeHtml4(page.getURI()));
        page.setTitle(StringEscapeUtils.escapeHtml4(page.getTitle()));
        page.setStatus(StringEscapeUtils.escapeHtml4(page.getStatus()));
        pages.add(page);
        jsonDB.serialize(pages,COLLECTION_NAME);
        return page;
    }

    public Page updateOne(Page page, int id) {
        var pages = jsonDB.parse(COLLECTION_NAME);
        Page pageToUpdate = pages.stream().filter(f -> f.getId() == id).findAny().orElse(null);
        if (pageToUpdate == null) return null;
        page.setId(id);
        page.setAuthor(StringEscapeUtils.escapeHtml4(page.getAuthor()));
        page.setContent(StringEscapeUtils.escapeHtml4(page.getContent()));
        page.setURI(StringEscapeUtils.escapeHtml4(page.getURI()));
        page.setTitle(StringEscapeUtils.escapeHtml4(page.getTitle()));
        page.setStatus(StringEscapeUtils.escapeHtml4(page.getStatus()));
        pages.remove(page); // thanks to equals(), films is found via its id
        pages.add(page);
        jsonDB.serialize(pages, COLLECTION_NAME);
        return page;
    }

    public int getNextId(){
        var pages = jsonDB.parse(COLLECTION_NAME);
        if (pages.size()==0){
            return 1;
        }
        return pages.get(pages.size()-1).getId()+1;
    }
}
