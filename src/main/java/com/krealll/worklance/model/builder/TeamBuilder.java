package com.krealll.worklance.model.builder;

import com.krealll.worklance.model.entity.Team;

public class TeamBuilder {

    private Long teamId;
    private String teamName;
    private String description;

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }


    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Team build(){
        Team team = new Team(teamId,teamName,description);
        teamId=null;
        description=null;
        teamName=null;
        return team;
    }
}
