package no.rubato.exceptions;

public class UsernameAlreadyExistsResponse {
    private String username;

    UsernameAlreadyExistsResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
