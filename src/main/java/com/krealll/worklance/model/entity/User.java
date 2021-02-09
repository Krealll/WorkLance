package com.krealll.worklance.model.entity;

import com.krealll.worklance.model.entity.type.Specialization;
import com.krealll.worklance.model.entity.type.UserRole;

public class User {

    private Long id;
    private String login;
    private String email;
    private UserRole role;
    private Specialization specialization;
    private String description;
    private Boolean showEmail;
    private Long team;

    public User(Long userId, String login, String email, UserRole userRole, Specialization specialization, Boolean showMail, String description, Long team) {
        this.id = userId;
        this.login = login;
        this.email = email;
        this.role = userRole;
        this.specialization = specialization;
        this.showEmail = showMail;
        this.description = description;
        this.team = team;
    }

    public User(String login, String email, UserRole userRole, Specialization specialization) {
        this.login = login;
        this.email = email;
        this.role = userRole;
        this.specialization = specialization;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Boolean getShowEmail() {
        return showEmail;
    }

    public void setShowEmail(Boolean showEmail) {
        this.showEmail = showEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTeam() {
        return team;
    }

    public void setTeam(Long team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if(id != null ? !id.equals(user.id) : user.id != null ){
            return false;
        }
        if(login != null ? !login.equals(user.login) : user.login != null ){
            return false;
        }
        if(email != null ? !email.equals(user.email) : user.email != null ){
            return false;
        }
        if(role != null ? !role.equals(user.role) : user.role != null ){
            return false;
        }
        if(showEmail != null ? !showEmail.equals(user.showEmail) : user.showEmail != null){
            return false;
        }
        if(description != null ? !description.equals(user.description) : user.description != null){
            return false;
        }
        if(team != null ? !team.equals(user.team) : user.team != null){
            return false;
        }
        if(specialization != null ? !specialization.equals(user.specialization) : user.specialization != null){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = id != null ? id.hashCode() : 0;
        hashCode = 31 * hashCode + (showEmail? 1:0);
        hashCode = 31 * hashCode + (email != null ? email.hashCode() : 0);
        hashCode = 31 * hashCode + (login != null ? login.hashCode() : 0);
        hashCode = 31 * hashCode + (team != null ? team.hashCode() : 0);
        hashCode = 31 * hashCode + (role != null ? role.hashCode() : 0);
        hashCode = 31 * hashCode + (specialization != null ? specialization.hashCode() : 0);
        hashCode = 31 * hashCode + (description != null ? description.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("User{");
        stringBuilder.append("userId=").append(id);
        stringBuilder.append(", login=").append(login).append('\'');
        stringBuilder.append(", email=").append(email).append('\'');
        stringBuilder.append(", userRole=").append(role).append('\'');
        stringBuilder.append(", specialization=").append(specialization).append('\'');
        stringBuilder.append(", showEmail=").append(showEmail);
        stringBuilder.append(", description=").append(description);
        stringBuilder.append(", teamId=").append(email).append('\'');
        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}
