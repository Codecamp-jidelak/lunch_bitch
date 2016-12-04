package cz.codecamp.lunchbitch.models;

public enum RegistrationState {
    SUCCESS("succcess"),
    INACTIVE("inactive"),
    NOTEXISTS("notexists");

    private String state;

    RegistrationState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }
}
