package com.krealll.worklance.model.entity;

public class Team extends Entity{

    private Long teamId;
    private String teamName;
    private Integer completedOrders;

    public Team(Long teamId, String teamName, Integer numberOfCompletedOrders) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.completedOrders = numberOfCompletedOrders;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(Integer completedOrders) {
        this.completedOrders = completedOrders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        if(teamId != null ? !teamId.equals(team.teamId) : team.teamId != null ){
            return false;
        }
        if(teamName != null ? !teamName.equals(team.teamName) : team.teamName != null ){
            return false;
        }
        return completedOrders != null ? !completedOrders.equals(team.completedOrders)
                : team.completedOrders != null;
    }

    @Override
    public int hashCode() {
        int hashCode = teamId != null ? teamId.hashCode() : 0;
        hashCode = 31 * hashCode + (teamName != null ? teamName.hashCode() : 0);
        hashCode = 31 * hashCode + (completedOrders != null ? completedOrders.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Team{");
        stringBuilder.append("teamId=").append(teamId);
        stringBuilder.append(", teamName='").append(teamName).append('\'');
        stringBuilder.append(", numberOfCompletedOrders='").append(completedOrders).append('\'');
        return stringBuilder.toString();
    }
}
