package be.vinci.services;

import be.vinci.domain.Text;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class TextDataService {

    private static final String COLLECTION_NAME = "texts";
    private static Json<Text> jsonDB = new Json<>(Text.class);
    public List<Text> getAll(String level) {
        var texts = jsonDB.parse(COLLECTION_NAME);
        if (level.equals("none")) return texts;
        List<Text> filteredLevelText = texts.stream()
                .filter(text -> text.getLevel().equalsIgnoreCase(level)).toList();
        return filteredLevelText;
    }


    public Text getOne(int id) {
        var texts = jsonDB.parse(COLLECTION_NAME);
        Text textFound = texts.stream().filter(text -> text.getId() == id).findAny().orElse(null);
        return textFound;
    }

    public Text createOne(Text text) {
        var texts = jsonDB.parse(COLLECTION_NAME);
        text.setId(texts.size()+1);
        text.setContent(StringEscapeUtils.escapeHtml4(text.getContent()));
        text.setLevel(StringEscapeUtils.escapeHtml4(text.getLevel()));
        texts.add(text);
        jsonDB.serialize(texts, COLLECTION_NAME);
        return text;
    }


    public Text deleteOne(int id) {
        var texts = jsonDB.parse(COLLECTION_NAME);
        Text textToDelete = texts.stream().filter(text -> text.getId() == id).findAny().orElse(null);
        if (textToDelete == null) return null;
        texts.remove(textToDelete);
        jsonDB.serialize(texts, COLLECTION_NAME);
        return textToDelete;
    }

    public Text updateOne(Text text, int id) {
        var texts = jsonDB.parse(COLLECTION_NAME);
        Text textToUpdate = texts.stream().filter(f -> f.getId() == id).findAny().orElse(null);
        if (textToUpdate == null) return null;
        text.setId(id);
        text.setContent(StringEscapeUtils.escapeHtml4(text.getContent()));
        text.setLevel(StringEscapeUtils.escapeHtml4(text.getLevel()));
        texts.remove(text); // thanks to equals(), films is found via its id
        texts.add(text);
        jsonDB.serialize(texts, COLLECTION_NAME);
        return text;
    }

    public int nextFilmId() {
        var texts = jsonDB.parse(COLLECTION_NAME);
        if (texts.size() == 0)
            return 1;
        return texts.get(texts.size() - 1).getId() + 1;
    }
}

