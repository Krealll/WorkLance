package com.krealll.worklance.model.service.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.dao.OrderDao;
import com.krealll.worklance.model.dao.impl.OrderDaoImpl;
import com.krealll.worklance.model.entity.Order;
import com.krealll.worklance.model.service.OrderService;
import com.krealll.worklance.model.validator.*;
import com.krealll.worklance.util.MapKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    @Override
    public boolean create(Map<String, String> parameters) throws ServiceException {
        boolean result = Validator.checkOrder(parameters);
        if(result){
            OrderDao orderDao = OrderDaoImpl.getInstance();
            Map<String, Object> createdObjects = new HashMap<>();
            try {
                createdObjects.put(MapKeys.NAME, parameters.get(MapKeys.NAME));
                createdObjects.put(MapKeys.BUDGET, parameters.get(MapKeys.BUDGET));
                createdObjects.put(MapKeys.DESCRIPTION, parameters.get(MapKeys.DESCRIPTION));
                createdObjects.put(MapKeys.SHOW_EMAIL, parameters.get(MapKeys.SHOW_EMAIL));
                createdObjects.put(MapKeys.USERS_USER_ID, parameters.get(MapKeys.USERS_USER_ID));
                result = orderDao.add(createdObjects);
                return result;
            } catch (DaoException e){
                throw new ServiceException(e);
            }
        } else {
            return result;
        }
    }

    @Override
    public List<Order> findAll() throws ServiceException {
        OrderDao orderDao = OrderDaoImpl.getInstance();
        try {
            List<Order> orders;
            orders = orderDao.findAll();
            return orders;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> findByBudget(String lower, String upper) throws ServiceException {
        boolean checkUpper = BudgetValidator.checkBudget(upper);
        boolean checkLower = BudgetValidator.checkBudget(lower);
        OrderDao orderDao = OrderDaoImpl.getInstance();
        List<Order> orders = null;
        try {
            if(checkLower&&checkUpper){
                double lowerBorder = Double.parseDouble(lower);
                double upperBorder = Double.parseDouble(upper);
                if( Double.compare(lowerBorder,upperBorder)<0){
                    orders = orderDao.findByBudget(lowerBorder,upperBorder);
                }
            }
            return orders;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> findByKey(String keyWord) throws ServiceException {
        boolean check = DescriptionValidator.checkDescription(keyWord);
        OrderDao orderDao = OrderDaoImpl.getInstance();
        List<Order> orders = null;
        try {
            if(check){
                orders = orderDao.findByKey(keyWord);
            }
            return orders;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> findByCustomer(String customer) throws ServiceException {
        boolean check = LoginValidator.checkLogin(customer);
        OrderDao orderDao = OrderDaoImpl.getInstance();
        List<Order> orders = null;
        try {
            if(check){
                orders = orderDao.findByCustomer(customer);
            }
            return orders;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> findByName(String name) throws ServiceException {
        boolean check = NameValidator.checkName(name);
        OrderDao orderDao = OrderDaoImpl.getInstance();
        List<Order> orders = null;
        try {
            if(check){
                orders = orderDao.findByName(name);
            }
            return orders;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> findByUserId(Long userId) throws ServiceException {
        OrderDao orderDao = OrderDaoImpl.getInstance();
        try {
            List<Order> orders;
            orders = orderDao.findByUserId(userId);
            return orders;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(Long orderId) throws ServiceException {
        OrderDao orderDao = OrderDaoImpl.getInstance();
        boolean result;
        try {
            result = orderDao.delete(orderId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }


    @Override
    public Optional<Order> findById(Long orderId) throws ServiceException {
        OrderDao orderDao = OrderDaoImpl.getInstance();
        try {
            Optional<Order> foundOrder = orderDao.findById(orderId);
            return foundOrder;
        } catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Map<String, String> params) throws ServiceException {
        boolean result = Validator.checkOrder(params);
        if(result){
            OrderDao orderDao = OrderDaoImpl.getInstance();
            Map<String, Object> updatedObjects = new HashMap<>();
            try {
                updatedObjects.put(MapKeys.NAME, params.get(MapKeys.NAME));
                updatedObjects.put(MapKeys.DESCRIPTION, params.get(MapKeys.DESCRIPTION));
                updatedObjects.put(MapKeys.STATUS, params.get(MapKeys.STATUS));
                updatedObjects.put(MapKeys.BUDGET, params.get(MapKeys.BUDGET));
                updatedObjects.put(MapKeys.SHOW_EMAIL, params.get(MapKeys.SHOW_EMAIL));
                updatedObjects.put(MapKeys.ORDER_ID, params.get(MapKeys.ORDER_ID));
                updatedObjects.put(MapKeys.CUSTOMER, params.get(MapKeys.CUSTOMER));
                result = orderDao.update(updatedObjects);
                return result;
            } catch (DaoException e){
                throw new ServiceException(e);
            }
        }
        return result;
    }

}
