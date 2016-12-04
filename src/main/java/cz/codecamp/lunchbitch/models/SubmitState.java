package cz.codecamp.lunchbitch.models;

public enum SubmitState {
    SUCCESS("succcess"),
    ALREADYEXISTS("alreadyExists"),
    NOTACTIVATED("notActivated"),
    ILLEGAL("illegal");

    private String state;

    SubmitState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }
}
