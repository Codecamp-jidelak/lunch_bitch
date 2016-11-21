package cz.codecamp.lunchbitch.webPageMappers;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Filip Ocelka on 15.11.2016, filip.ocelka@gmail.com
 */
public class SearchForm {

    @NotNull
    @NotBlank
    private String keyword;
    private String type;
    private String calledFrom;

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

    public String getCalledFrom() {
        return calledFrom;
    }

    public void setCalledFrom(String calledFrom) {
        this.calledFrom = calledFrom;
    }
}
