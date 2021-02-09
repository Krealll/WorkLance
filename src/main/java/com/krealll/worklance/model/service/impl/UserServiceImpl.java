package com.krealll.worklance.model.service.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.exception.HashGeneratorException;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.builder.UserBuilder;
import com.krealll.worklance.model.dao.UserDao;
import com.krealll.worklance.model.dao.impl.UserDaoImpl;
import com.krealll.worklance.model.entity.type.Specialization;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.UserService;
import com.krealll.worklance.util.HashGenerator;
import com.krealll.worklance.util.MapKeys;
import com.krealll.worklance.model.validator.DescriptionValidator;
import com.krealll.worklance.model.validator.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Override
    public boolean registerUser(Map<String,String> parameters) throws ServiceException {
        boolean result = Validator.checkRegistration(parameters);
        if(result){
            UserDao userDao = UserDaoImpl.getInstance();
            Map<String, Object> userParams = new HashMap<>();
            try {
                String password = HashGenerator.generatePassword(parameters.get(MapKeys.PASSWORD));
                userParams.put(MapKeys.EMAIL, parameters.get(MapKeys.EMAIL));
                userParams.put(MapKeys.LOGIN, parameters.get(MapKeys.LOGIN));
                userParams.put(MapKeys.SPECIALIZATION, parameters.get(MapKeys.SPECIALIZATION));
                userParams.put(MapKeys.PASSWORD, password);
                result = userDao.add(userParams);
                return result;
            } catch (DaoException |  HashGeneratorException e){
                throw new ServiceException(e);
            }
        }
        return result;
    }


    @Override
    public boolean checkLoginAndPassword(String login, String password) throws ServiceException {
        Map<String,String> parameters = new HashMap<>();
        parameters.put(MapKeys.LOGIN,login);
        parameters.put(MapKeys.PASSWORD,password);
        boolean check = Validator.checkLogIn(parameters);
        if(check){
            UserDao userDao = UserDaoImpl.getInstance();
            try {
                Optional<String> optionalPassword = userDao.findPasswordByLogin(login);
                if (optionalPassword.isPresent()) {
                    String foundPassword = optionalPassword.get();
                    String hashPassword = HashGenerator.generatePassword(password);
                    if (hashPassword.equals(foundPassword)) {
                        return true;
                    }
                }
                return false;
            } catch (DaoException | HashGeneratorException e) {
                throw new ServiceException(e);
            }
        }
        return check;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            List<User> users;
            users = userDao.findAll();
            return users;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findBySpecialization(String specialization) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            List<User> users;
            users = userDao.findBySpecialization(specialization);
            return users;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findByKey(String keyWord) throws ServiceException {
        boolean check = DescriptionValidator.checkDescription(keyWord);
        UserDao userDao = UserDaoImpl.getInstance();
        List<User> users = null;
        try {
            if(check){
                users = userDao.findByKey(keyWord);
            }
            return users;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            Optional<User> foundUser = userDao.findUserByLogin(login);
            return foundUser;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findManager(Long teamId) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            Optional<User> foundUser = userDao.findManager(teamId);
            return foundUser;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findById(Long userId) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            Optional<User> foundUser = userDao.findById(userId);
            return foundUser;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findByTeamId(Long teamId) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        try {
            List<User> users = userDao.findByTeamId(teamId);
            return users;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteMember(User user) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        boolean result;
        try {
            result = userDao.updateMembership(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean update(Map<String,String> params) throws ServiceException {
        boolean result = Validator.checkEditingProfile(params);
        if(result){
            UserDao userDao = UserDaoImpl.getInstance();
            try {
                UserBuilder userBuilder = new UserBuilder();
                userBuilder.setLogin(params.get(MapKeys.LOGIN));
                userBuilder.setEmail(params.get(MapKeys.EMAIL));
                userBuilder.setShow(params.get(MapKeys.SHOW_EMAIL)!=null);
                userBuilder.setSpecialization(Specialization.valueOf(params.get(MapKeys.SPECIALIZATION).toUpperCase()));
                userBuilder.setDescription(params.get(MapKeys.DESCRIPTION));
                userBuilder.setUserId(Long.parseLong(params.get(MapKeys.USER_ID)));
                result = userDao.update(userBuilder.build());
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }


    @Override
    public boolean leaveTeam(Long userId) throws ServiceException {
        UserDao userDao = UserDaoImpl.getInstance();
        boolean result;
        try {
            result = userDao.leaveTeam(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

}
