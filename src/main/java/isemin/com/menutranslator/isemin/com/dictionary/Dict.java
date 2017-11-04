package isemin.com.menutranslator.isemin.com.dictionary;

/**
 * Created by Shahruk on 4/24/2015.
 */

public class Dict {

    public String word;
    public String type;
    public String definition;

    public Dict(String word, String definition, String type) {
        this.word = word;
        this.definition = definition;
        this.type = type;
    }
    public Dict(){

    }
}
