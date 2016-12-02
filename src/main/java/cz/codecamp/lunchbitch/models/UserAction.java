package cz.codecamp.lunchbitch.models;

public enum UserAction {
    REGISTRATION("Aktivace účtu"),
    UPDATE("Změna nastavení"),
    UNSUBSCRIPTION("Odhlášení odběru");

    private String action;

    UserAction(String action){
        this.action = action;
    }

    public String getAction(){
        return action;
    }
}
