package com.krealll.worklance.model.builder;

import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.entity.type.Specialization;
import com.krealll.worklance.model.entity.type.UserRole;

public class UserBuilder {

    private String login;
    private String email;
    private UserRole role;
    private Specialization specialization;
    private Long userId;
    private String description;
    private Boolean show;
    private Long team;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public void setTeam(Long team) {
        this.team = team;
    }

    public User build(){
        User user = new User(userId, login,email,role,specialization, show,description,team);
        login = null;
        role = null;
        specialization = null;
        userId = null;
        show =null;
        email = null;
        team = null;
        description=null;
        return user;
    }

}
