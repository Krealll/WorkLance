package com.krealll.worklance.model.service.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.dao.impl.OrderDaoImpl;
import com.krealll.worklance.model.entity.Order;
import com.krealll.worklance.model.entity.type.OrderStatus;
import com.krealll.worklance.util.MapKeys;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

@PrepareForTest(OrderServiceImpl.class)
public class OrderServiceImplTest {
    private OrderDaoImpl orderDao;
    private OrderServiceImpl orderService;
    private Optional<Order> orderOptional;
    private Order firstOrder;
    private Order secondOrder;
    private List<Order> orders;



    @BeforeMethod
    public void setUp() {
        orderDao = mock(OrderDaoImpl.class);
        orderService = new OrderServiceImpl();
        Whitebox.setInternalState(OrderDaoImpl.class,"instance",orderDao);
        firstOrder = new Order(1L,"title", LocalDate.now(),"description", OrderStatus.OPENED,1L,1.0);
        secondOrder = new Order(2L,"title",LocalDate.now(),"description", OrderStatus.OPENED,1L,1.0);
        orderOptional = Optional.of(firstOrder);
        orders = new ArrayList<>();
        orders.add(firstOrder);
        orders.add(secondOrder);
    }

    @AfterClass
    public void tearDown() {
        orderDao = null;
        orderService = null;
        orderOptional = Optional.empty();
        firstOrder = null;
        secondOrder = null;
        orders = null;
    }

    @DataProvider(name = "orderCorrect")
    public Object[][] createOrderParameters(){
        return new Object[][]{
                {"nameName","0.999"},
                {"nameTwo","22.92012312"},
                {"SuperName","2213.0"}
        };
    }

    @DataProvider(name = "orderIncorrect")
    public Object[][] createIncorrectOrderParameters(){
        return new Object[][]{
                {"name name","1.1"},
                {"name","1"},
                {"name*name12/%7rer","13.23"}
        };
    }


    @DataProvider(name = "findByBudgetCorrect")
    public Object[][] createCorrectBudgetParameters(){
        return new Object[][]{
                {"0.001","0.999"},
                {"1.001","22.92012312"},
                {"2212.99","2213.0"}
        };
    }

    @DataProvider(name = "findByBudgetIncorrect")
    public Object[][] createIncorrectBudgetParameters(){
        return new Object[][]{
                {"10.001","0.999"},
                {"1.001","22012312"},
                {"2212,99","2213,0"}
        };
    }

    @Test(dataProvider = "orderCorrect")
    public void testCreateTrue(String name, String budget) {
        try {
            Map<String,String> parameters = new HashMap<>();
            parameters.put(MapKeys.NAME,name);
            parameters.put(MapKeys.BUDGET,budget);
            parameters.put(MapKeys.SHOW_EMAIL,"true");
            parameters.put(MapKeys.DESCRIPTION,"description. description. description. description");
            parameters.put(MapKeys.USERS_USER_ID,"3");
            when(orderDao.add(anyMap())).thenReturn(true);
            boolean result = orderService.create(parameters);;
            assertTrue(result);
        } catch (ServiceException| DaoException e) {
            fail(e.getMessage());
        }
    }


    @Test(dataProvider = "orderIncorrect")
    public void testCreateFalse(String name, String budget) {
        try {
            Map<String,String> parameters = new HashMap<>();
            parameters.put(MapKeys.NAME,name);
            parameters.put(MapKeys.BUDGET,budget);
            parameters.put(MapKeys.SHOW_EMAIL,"true");
            parameters.put(MapKeys.DESCRIPTION,"description. description. description. description");
            parameters.put(MapKeys.USERS_USER_ID,"3");
            when(orderDao.add(anyMap())).thenReturn(false);
            boolean result = orderService.create(parameters);;
            assertFalse(result);
        } catch (ServiceException| DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateException() throws ServiceException, DaoException {
        Map<String,String> parameters = new HashMap<>();
        parameters.put(MapKeys.NAME,"name");
        parameters.put(MapKeys.BUDGET,"1.1");
        parameters.put(MapKeys.SHOW_EMAIL,"true");
        parameters.put(MapKeys.DESCRIPTION,"description. description. description. description");
        parameters.put(MapKeys.USERS_USER_ID,"3");
        when(orderDao.add(anyMap())).thenThrow(DaoException.class);
        orderService.create(parameters);
    }

    @Test
    public void testFindAllTrue() {
        try {
            when(orderDao.findAll()).thenReturn(orders);
            List<Order> foundOrders = orderService.findAll();
            assertEquals(foundOrders,orders);
        } catch (ServiceException|DaoException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindAllException() throws DaoException, ServiceException {
        when(orderDao.findAll()).thenThrow(DaoException.class);
        orderService.findAll();
    }


    @Test(dataProvider = "findByBudgetCorrect")
    public void testFindByBudgetTrue(String lower, String upper) {
        try {
            when(orderDao.findByBudget(anyDouble(), anyDouble())).thenReturn(orders);
            List<Order> foundOrders = orderService.findByBudget(lower,upper);
            assertEquals(foundOrders,orders);
        } catch (ServiceException|DaoException e){
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "findByBudgetIncorrect")
    public void testFindByBudgetFalse(String lower, String upper) {
        try {
            when(orderDao.findByBudget(anyDouble(), anyDouble())).thenReturn(orders);
            List<Order> foundOrders = orderService.findByBudget(lower,upper);
            assertNotEquals(foundOrders,orders);
        } catch (ServiceException|DaoException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByBudgetException() throws ServiceException, DaoException {
        when(orderDao.findByBudget(anyDouble(),anyDouble())).thenThrow(DaoException.class);
        orderService.findByBudget("0.1","1.9");
    }

    @Test
    public void testFindByKeyTrue() {
        try {
            when(orderDao.findByKey(anyString())).thenReturn(orders);
            List<Order> foundOrders = orderService.findByKey("keyWord");
            assertEquals(foundOrders,orders);
        } catch (ServiceException|DaoException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByKeyException() throws ServiceException, DaoException {
        when(orderDao.findByKey(anyString())).thenThrow(DaoException.class);
        orderService.findByKey("keyWord");
    }

    @Test
    public void testFindByNameTrue() {
        try {
            when(orderDao.findByName(anyString())).thenReturn(orders);
            List<Order> foundOrders = orderService.findByName("name");
            assertEquals(foundOrders,orders);
        } catch (ServiceException|DaoException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByNameException() throws ServiceException, DaoException {
        when(orderDao.findByName(anyString())).thenThrow(DaoException.class);
        orderService.findByName("name");
    }

    @Test
    public void testFindByCustomerTrue() {
        try {
            when(orderDao.findByCustomer(anyString())).thenReturn(orders);
            List<Order> foundOrders = orderService.findByCustomer("customer");
            assertEquals(foundOrders,orders);
        } catch (ServiceException|DaoException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByCustomerException() throws ServiceException, DaoException {
        when(orderDao.findByCustomer(anyString())).thenThrow(DaoException.class);
        orderService.findByCustomer("customer");
    }

    @Test
    public void testFindByUserIdTrue() {
        try {
            when(orderDao.findByUserId(anyLong())).thenReturn(orders);
            List<Order> foundOrders = orderService.findByUserId(1L);
            assertEquals(foundOrders,orders);
        } catch (ServiceException|DaoException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByUserIdException() throws ServiceException, DaoException {
        when(orderDao.findByUserId(anyLong())).thenThrow(DaoException.class);
        orderService.findByUserId(1L);
    }

    @Test
    public void testDeleteTrue() {
        try {
            when(orderDao.delete(anyLong())).thenReturn(true);
            boolean result = orderService.delete(1L);
            assertTrue(result);
        } catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testDeleteException() throws ServiceException, DaoException {
        when(orderDao.delete(anyLong())).thenThrow(DaoException.class);
        orderService.delete(1L);
    }

    @Test
    public void testFindByIdTrue() {
        try {
            when(orderDao.findById(anyLong())).thenReturn(orderOptional);
            Optional<Order> foundOrder = orderService.findById(1L);
            assertEquals(foundOrder,orderOptional);
        } catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByIdException() throws ServiceException, DaoException {
        when(orderDao.findById(anyLong())).thenThrow(DaoException.class);
        orderService.findById(1L);
    }

    @Test(dataProvider = "orderCorrect")
    public void testUpdateTrue(String name, String budget) {
        try {
            Map<String,String> orderParameters = new HashMap<>();
            orderParameters.put(MapKeys.NAME,name);
            orderParameters.put(MapKeys.DESCRIPTION,"description");
            orderParameters.put(MapKeys.STATUS,OrderStatus.OPENED.getName());
            orderParameters.put(MapKeys.BUDGET,budget);
            orderParameters.put(MapKeys.SHOW_EMAIL,"true");
            orderParameters.put(MapKeys.ORDER_ID,"1");
            orderParameters.put(MapKeys.CUSTOMER,"1");

            when(orderDao.update(anyMap())).thenReturn(true);
            boolean result = orderService.update(orderParameters);
            assertTrue(result);
        } catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "orderIncorrect")
    public void testUpdateFalse(String name, String budget) {
        try {
            Map<String,String> orderParameters = new HashMap<>();
            orderParameters.put(MapKeys.NAME,name);
            orderParameters.put(MapKeys.DESCRIPTION,"description");
            orderParameters.put(MapKeys.STATUS,OrderStatus.OPENED.getName());
            orderParameters.put(MapKeys.BUDGET,budget);
            orderParameters.put(MapKeys.SHOW_EMAIL,"true");
            orderParameters.put(MapKeys.ORDER_ID,"1");
            orderParameters.put(MapKeys.CUSTOMER,"1");

            when(orderDao.update(anyMap())).thenReturn(false);
            boolean result = orderService.update(orderParameters);
            assertFalse(result);
        } catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testLeaveException() throws ServiceException, DaoException {
        Map<String,String> orderParameters = new HashMap<>();
        orderParameters.put(MapKeys.NAME,"name");
        orderParameters.put(MapKeys.DESCRIPTION,"description");
        orderParameters.put(MapKeys.STATUS,OrderStatus.OPENED.getName());
        orderParameters.put(MapKeys.BUDGET,"1.1");
        orderParameters.put(MapKeys.SHOW_EMAIL,"true");
        orderParameters.put(MapKeys.ORDER_ID,"1");
        orderParameters.put(MapKeys.CUSTOMER,"1");
        when(orderDao.update(anyMap())).thenThrow(DaoException.class);
        orderService.update(orderParameters);
    }
}