package uk.ac.standrews.cs.host.cs3099user20.service;

public class InjectionPrevention {

    public static String injectionPrevention(String string){
        if(string == null) {return string;} else {
        return string
                .replaceAll("\"", "")
                .replaceAll("'", "")
                .replaceAll(";", "");}
    }
}
