package cz.codecamp.lunchbitch.webPageMappers;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Filip Ocelka on 15.11.2016, filip.ocelka@gmail.com
 */
public class SearchForm {

    private String OPTIONS_DESCRIPTION_KEYWORD = "Vyhledat podle klíčového slova";
    private String OPTIONS_DESCRIPTION_ADDRESS = "Vyhledat podle adresy";

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
        typeDescription.put("keyword", OPTIONS_DESCRIPTION_KEYWORD);
        typeDescription.put("address", OPTIONS_DESCRIPTION_ADDRESS);
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
