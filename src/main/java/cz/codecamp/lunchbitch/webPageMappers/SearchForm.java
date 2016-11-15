package cz.codecamp.lunchbitch.webPageMappers;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Filip Ocelka on 15.11.2016, filip.ocelka@gmail.com
 */
public class SearchForm {

    @NotNull
    @Size(min = 1)
    private String keyword;

    private String type;

    private Map<String, String> typeDescription;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SearchForm() {
        this.typeDescription = new HashMap<>();
        typeDescription.put("keyword", "Vyhledat podle klíčového slova");
        typeDescription.put("address", "Vyhledat podle adresy");
    }

    public Map<String, String> getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(Map<String, String> typeDescription) {
        this.typeDescription = typeDescription;
    }
}
