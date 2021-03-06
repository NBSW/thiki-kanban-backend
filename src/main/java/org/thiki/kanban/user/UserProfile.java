package org.thiki.kanban.user;

/**
 * Created by xubt on 28/09/2016.
 */
public class UserProfile {

    private String id;
    private String userName;
    private String avatar;
    private String nickName;
    private String email;

    public UserProfile(String userName) {
        this.userName = userName;
    }

    public UserProfile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
