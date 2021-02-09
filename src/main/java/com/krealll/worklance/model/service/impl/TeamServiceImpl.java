package com.krealll.worklance.model.service.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.builder.TeamBuilder;
import com.krealll.worklance.model.dao.TeamDao;
import com.krealll.worklance.model.dao.impl.TeamDaoImpl;
import com.krealll.worklance.model.entity.Team;
import com.krealll.worklance.model.service.TeamService;
import com.krealll.worklance.model.validator.NameValidator;
import com.krealll.worklance.util.MapKeys;
import com.krealll.worklance.model.validator.DescriptionValidator;
import com.krealll.worklance.model.validator.LoginValidator;
import com.krealll.worklance.model.validator.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TeamServiceImpl implements TeamService {

    @Override
    public Optional<Team> findByName(String name) throws ServiceException {
        boolean check = NameValidator.checkName(name);
        TeamDao teamDao = TeamDaoImpl.getInstance();
        Optional<Team> foundTeam = Optional.empty();
        try {
            if (check){
                foundTeam = teamDao.findByName(name);
            }
            return foundTeam;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Team> findByManager(String manager) throws ServiceException {
        boolean check = LoginValidator.checkLogin(manager);
        TeamDao teamDao = TeamDaoImpl.getInstance();
        List<Team> teams = null;
        try {
            if(check){
                teams = teamDao.findByManager(manager);
            }
            return teams;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Team> findByKey(String keyWord) throws ServiceException {
        boolean check = DescriptionValidator.checkDescription(keyWord);
        TeamDao teamDao = TeamDaoImpl.getInstance();
        List<Team> teams = null;
        try {
            if(check){
                teams = teamDao.findByKey(keyWord);
            }
            return teams;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Team> findById(Long teamId) throws ServiceException {
        TeamDao teamDao = TeamDaoImpl.getInstance();
        try {
            Optional<Team> foundTeam = teamDao.findById(teamId);
            return foundTeam;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(Long teamId) throws ServiceException {
        TeamDao teamDao = TeamDaoImpl.getInstance();
        boolean result;
        try {
            result = teamDao.delete(teamId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean update(Map<String, String> params) throws ServiceException {
        boolean result = Validator.checkTeam(params);
        if(result){
            TeamDao teamDao = TeamDaoImpl.getInstance();
            try {
                TeamBuilder builder = new TeamBuilder();
                builder.setTeamName(params.get(MapKeys.NAME));
                builder.setTeamId(Long.parseLong(params.get(MapKeys.TEAM_ID)));
                builder.setDescription(params.get(MapKeys.DESCRIPTION));
                result = teamDao.update(builder.build());
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }



    @Override
    public Optional<Team> findByManagerId(Long managerId) throws ServiceException {
        TeamDao teamDao = TeamDaoImpl.getInstance();
        try {
            Optional<Team> foundTeam = teamDao.findByManagerId(managerId);
            return foundTeam;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public boolean create(Map<String, String> params) throws ServiceException {
        boolean result = Validator.checkTeam(params);
        if(result){
            TeamDao teamDao = TeamDaoImpl.getInstance();
            Map<String, Object> teamParameters = new HashMap<>();

            try {
                teamParameters.put(MapKeys.DESCRIPTION,params.get(MapKeys.DESCRIPTION));
                teamParameters.put(MapKeys.NAME,params.get(MapKeys.NAME));
                teamParameters.put(MapKeys.USER_ID,params.get(MapKeys.USER_ID));
                result = teamDao.add(teamParameters);
                return result;
            } catch (DaoException e){
                throw new ServiceException(e);
            }
        } else {
            return result;
        }
    }

    @Override
    public List<Team> findAll() throws ServiceException {
        TeamDao teamDao = TeamDaoImpl.getInstance();
        try {
            List<Team> teams;
            teams = teamDao.findAll();
            return teams;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }



}
