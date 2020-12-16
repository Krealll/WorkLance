package com.krealll.worklance.model.entity;

public class User extends Entity {

    private Long userId;
    private String login;
    private String email;
    private UserRole userRole;
    private Specialization specialization;

    public User(Long userId, String login, String email, UserRole userRole, Specialization specialization) {
        this.userId = userId;
        this.login = login;
        this.email = email;
        this.userRole = userRole;
        this.specialization = specialization;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if(userId != null ? !userId.equals(user.userId) : user.userId != null ){
            return false;
        }
        if(login != null ? !login.equals(user.login) : user.login != null ){
            return false;
        }
        if(email != null ? !email.equals(user.email) : user.email != null ){
            return false;
        }
        if(userRole != null ? !userRole.equals(user.userRole) : user.userRole != null ){
            return false;
        }
        return specialization != null ? !specialization.equals(user.specialization) : user.specialization != null;
    }

    @Override
    public int hashCode() {
        int hashCode = userId != null ? userId.hashCode() : 0;
        hashCode = 31 * hashCode + (email != null ? email.hashCode() : 0);
        hashCode = 31 * hashCode + (login != null ? login.hashCode() : 0);
        hashCode = 31 * hashCode + (userRole != null ? userRole.hashCode() : 0);
        hashCode = 31 * hashCode + (specialization != null ? specialization.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("User{");
        stringBuilder.append("userId=").append(userId);
        stringBuilder.append(", login='").append(login).append('\'');
        stringBuilder.append(", email='").append(email).append('\'');
        stringBuilder.append(", userRole='").append(userRole).append('\'');
        stringBuilder.append(", specialization='").append(specialization).append('\'');
        return stringBuilder.toString();
    }
}
