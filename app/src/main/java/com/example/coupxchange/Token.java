package com.example.coupxchange;

public class Token {
    String useremail;
    String token;

    public Token() {

    }

    public Token(String useremail, String token) {
        this.useremail = useremail;
        this.token = token;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
