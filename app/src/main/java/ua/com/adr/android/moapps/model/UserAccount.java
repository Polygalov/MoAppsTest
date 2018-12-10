package ua.com.adr.android.moapps.model;


public class UserAccount {

    final String userNick;
    final String password;

    public UserAccount(String nickName, String pass) {
        this.userNick = nickName;
        this.password = pass;
    }
}
