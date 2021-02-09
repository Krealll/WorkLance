package com.krealll.worklance.model.service.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.dao.NotificationDao;
import com.krealll.worklance.model.dao.impl.NotificationDaoImpl;
import com.krealll.worklance.model.dao.UserDao;
import com.krealll.worklance.model.dao.impl.UserDaoImpl;
import com.krealll.worklance.model.entity.Notification;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.NotificationService;
import com.krealll.worklance.util.MapKeys;
import com.krealll.worklance.model.validator.LoginValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NotificationServiceImpl implements NotificationService {

    @Override
    public List<Notification> findByUserId(Long userId) throws ServiceException {
        NotificationDao notificationDao = NotificationDaoImpl.getInstance();
        List<Notification> notifications;
        try {
            notifications = notificationDao.findByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return notifications;
    }


    @Override
    public Optional<Notification> findById(Long notificationId) throws ServiceException {
        NotificationDao notificationDao = NotificationDaoImpl.getInstance();
        try {
            Optional<Notification> notifications = notificationDao.findById(notificationId);
            return notifications;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean acceptNotification(Notification notification) throws ServiceException {
        NotificationDao notificationDao = NotificationDaoImpl.getInstance();
        boolean result;
        try {
            result = notificationDao.acceptNotification(notification);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean delete(Long notificationId) throws ServiceException {
        NotificationDao notificationDao = NotificationDaoImpl.getInstance();
        boolean result;
        try {
            result = notificationDao.delete(notificationId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }


    @Override
    public boolean addNotifications(Map<String, String> membersParameters) throws ServiceException {
        boolean result = true;
        NotificationDao notificationDao = NotificationDaoImpl.getInstance();
        UserDao userDao = UserDaoImpl.getInstance();
        Map<String, Object> parameters = new HashMap<>();
        try {
            int numberOfMembers = Integer.parseInt(membersParameters.get(MapKeys.NUMBER_OF_MEMBERS));
            for (int i = 1; i <= numberOfMembers ; i++) {
                String userLogin = membersParameters.get(MapKeys.MEMBER_INPUT+i);
                if(userLogin.equals(membersParameters.get(MapKeys.USER_LOGIN))){
                    continue;
                }
                if(LoginValidator.checkLogin(userLogin)){
                    Optional<User> userOptional = userDao.findUserByLogin(userLogin);
                    if(userOptional.isPresent()){
                        parameters.put(MapKeys.USER_ID,userOptional.get().getId().toString());
                        parameters.put(MapKeys.TEAM_ID,membersParameters.get(MapKeys.TEAM_ID));
                        result = notificationDao.add(parameters);
                        if(!result){
                            break;
                        }
                    }
                }
            }
            return result;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addNotification(Map<String, String> membersParameters) throws ServiceException {
        NotificationDao notificationDao = NotificationDaoImpl.getInstance();
        Map<String, Object> parameters = new HashMap<>();
        boolean result;
        try {
            parameters.put(MapKeys.USER_ID, membersParameters.get(MapKeys.USER_ID));
            parameters.put(MapKeys.TEAM_ID,membersParameters.get(MapKeys.TEAM_ID));
            result = notificationDao.add(parameters);
            return result;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

}
