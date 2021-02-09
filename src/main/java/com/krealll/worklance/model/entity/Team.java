package com.krealll.worklance.model.entity;

public class Team  {

    private Long teamId;
    private String name;
    private String description;

    public Team(Long teamId, String teamName, String description) {
        this.teamId = teamId;
        this.name = teamName;
        this.description=description;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        if(teamId != null ? !teamId.equals(team.teamId) : team.teamId != null ){
            return false;
        }
        if(name != null ? !name.equals(team.name) : team.name != null ){
            return false;
        }
        if(description != null ? !description.equals(team.description) : team.description != null ){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = teamId != null ? teamId.hashCode() : 0;
        hashCode = 31 * hashCode + (name != null ? name.hashCode() : 0);
        hashCode = 31 * hashCode + (description != null ? description.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Team{");
        stringBuilder.append("teamId=").append(teamId);
        stringBuilder.append(", teamName=").append(name).append('\'');
        stringBuilder.append(", description=").append(description).append('\'');
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

}
