package cz.codecamp.lunchbitch.models;

public class AuthToken {

    private final String authKey;

    private final UserAction action;

    public AuthToken(String authKey, UserAction action) {
        this.authKey = authKey;
        this.action = action;
    }

    public static AuthToken of(String authKey, UserAction userAction) {
        return new AuthToken(authKey, userAction);
    }

    public String getAuthKey() {
        return authKey;
    }

    public UserAction getAction() {
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthToken authToken = (AuthToken) o;

        if (authKey != null ? !authKey.equals(authToken.authKey) : authToken.authKey != null) return false;
        return action == authToken.action;

    }

    @Override
    public int hashCode() {
        int result = authKey != null ? authKey.hashCode() : 0;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "authKey='" + authKey + '\'' +
                ", action=" + action +
                '}';
    }
}
