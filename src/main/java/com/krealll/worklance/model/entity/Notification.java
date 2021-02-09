package com.krealll.worklance.model.entity;

public class Notification {

    private Long notificationId;
    private Long teamId;
    private Long userId;
    private String teamName;
    private Long managerId;
    private String managerLogin;

    public Notification(Long notificationId,Long teamId,Long userId,String teamName,Long managerId,String managerLogin ) {
        this.notificationId = notificationId;
        this.teamId = teamId;
        this.userId = userId;
        this.teamName = teamName;
        this.managerId = managerId;
        this.managerLogin=managerLogin;
    }

    public Notification(Long notificationId, Long teamId, Long userId) {
        this.notificationId = notificationId;
        this.teamId = teamId;
        this.userId = userId;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getManagerLogin() {
        return managerLogin;
    }

    public void setManagerLogin(String managerLogin) {
        this.managerLogin = managerLogin;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification notification = (Notification) o;
        if(notificationId != null ? !notificationId.equals(notification.notificationId) : notification.notificationId != null ){
            return false;
        }
        if(teamId != null ? !teamId.equals(notification.teamId) : notification.teamId != null ){
            return false;
        }
        if(teamName != null ? !teamName.equals(notification.teamName) : notification.teamName != null ){
            return false;
        }
        if(managerId != null ? !managerId.equals(notification.managerId) : notification.managerId != null ){
            return false;
        }
        if(managerLogin != null ? !managerLogin.equals(notification.managerLogin) : notification.managerLogin != null ){
            return false;
        }
        if(userId != null ? !userId.equals(notification.userId) : notification.userId != null ){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = notificationId != null ? notificationId.hashCode() : 0;
        hashCode = 31 * hashCode + (teamId != null ? teamId.hashCode() : 0);
        hashCode = 31 * hashCode + (userId != null ? userId.hashCode() : 0);
        hashCode = 31 * hashCode + (teamName != null ? teamName.hashCode() : 0);
        hashCode = 31 * hashCode + (managerLogin != null ? managerLogin.hashCode() : 0);
        hashCode = 31 * hashCode + (managerId != null ? managerId.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Notification{");
        stringBuilder.append("notificationId=").append(notificationId);
        stringBuilder.append(", teamName=").append(teamId);
        stringBuilder.append(", teamId=").append(teamId);
        stringBuilder.append(", managerLogin=").append(managerLogin);
        stringBuilder.append(", managerId=").append(managerId);
        stringBuilder.append(", userId=").append(userId).append('}');
        return stringBuilder.toString();

    }
}
