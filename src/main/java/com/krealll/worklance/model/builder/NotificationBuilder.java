package com.krealll.worklance.model.builder;

import com.krealll.worklance.model.entity.Notification;

public class NotificationBuilder {

    private Long notificationId;
    private Long teamId;
    private Long userId;
    private String teamName;
    private Long managerId;
    private String managerLogin;
    
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public void setManagerLogin(String managerLogin) {
        this.managerLogin = managerLogin;
    }

    public Notification build(){
        Notification notification = new Notification(notificationId,teamId,userId,teamName,managerId,managerLogin);
        notificationId =null;
        teamId =null;
        userId =null;
        teamName =null;
        managerId =null;
        managerLogin =null;
        return notification;
    }
}
