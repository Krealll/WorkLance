package com.krealll.worklance.model.service.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.dao.NotificationDao;
import com.krealll.worklance.model.dao.UserDao;
import com.krealll.worklance.model.dao.impl.NotificationDaoImpl;
import com.krealll.worklance.model.dao.impl.UserDaoImpl;
import com.krealll.worklance.model.entity.Notification;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.entity.type.Specialization;
import com.krealll.worklance.model.entity.type.UserRole;
import com.krealll.worklance.util.MapKeys;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.*;

import java.util.*;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

@PrepareForTest(NotificationServiceImpl.class)
public class NotificationServiceImplTest {

    private NotificationServiceImpl notificationService;
    private NotificationDao notificationDao;
    private UserDao userDao;
    private Notification firstNotification;
    private Notification secondNotification;
    private List<Notification> notifications;
    private Optional<Notification> notificationOptional;
    private Optional<User> userOptional;

    @DataProvider(name="acceptNotification")
    public Object[][] createNotificationParameters(){
        return new Object[][]{
                {"managerOne",1L},
                {"managerTwo",2L},
                {"managerThree",3L}
        };
    }

    @DataProvider(name="addNotifications")
    public Object[][] createAdditionParameters(){
        return new Object[][]{
                {"managerOne","managerTow"},
                {"managerThree","managerFour"},
                {"managerFive","managerSix"}
        };
    }

    @BeforeMethod
    public void setUp() {
        userDao = mock(UserDao.class);
        notificationDao = mock(NotificationDaoImpl.class);
        notificationService = new NotificationServiceImpl();
        Whitebox.setInternalState(UserDaoImpl.class,"instance",userDao);
        Whitebox.setInternalState(NotificationDaoImpl.class,"instance", notificationDao);
        firstNotification = new Notification(1L,2L,3L,"team",4L,"manager");
        secondNotification = new Notification(2L,2L,3L,"team",4L,"managerTwo");
        User user = new User(1L,"login","email", UserRole.USER, Specialization.OTHER, true,"description",1L);
        notificationOptional = Optional.of(firstNotification);
        userOptional = Optional.of(user);
        notifications = new ArrayList<>();
        notifications.add(firstNotification);
        notifications.add(secondNotification);
    }

    @AfterClass
    public void tearDown() {
        notifications=null;
        notificationOptional=Optional.empty();
        secondNotification=null;
        firstNotification= null;
        notificationDao=null;
        notificationService=null;
    }

    @Test
    public void testFindByUserIdTrue() {
        try {
            when(notificationDao.findByUserId(any(Long.class))).thenReturn(notifications);
            List<Notification> foundNotification = notificationService.findByUserId(3L);
            assertEquals(foundNotification,notifications);
        } catch (DaoException| ServiceException e){
            fail();e.getMessage();
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    void testFindByUSerIdException() throws DaoException, ServiceException {
        when(notificationDao.findByUserId(any(Long.class))).thenThrow(DaoException.class);
        notificationService.findByUserId(3L);
    }

    @Test
    public void testFindByIdTrue() {
        try {
           when(notificationDao.findById(any(Long.class))).thenReturn(notificationOptional);
           Optional<Notification> foundNotification = notificationService.findById(1L);
           assertEquals(foundNotification,notificationOptional);
        } catch (DaoException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByIdException() throws ServiceException, DaoException {
        when(notificationDao.findById(any(Long.class))).thenThrow(DaoException.class);
        notificationService.findById(1L);
    }

    @Test(dataProvider = "acceptNotification")
    public void testAcceptNotificationTrue(String manager, Long managerId) {
        try {
            Notification testNotification = new Notification(1L,2L,3L,"team",managerId,manager);
            when(notificationDao.acceptNotification(any(Notification.class))).thenReturn(true);
            boolean result = notificationService.acceptNotification(testNotification);
            assertTrue(result);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAcceptNotificationException() throws ServiceException, DaoException {
        Notification testNotification = new Notification(1L,2L,3L,"team",
                3L,"manager");
        when(notificationDao.acceptNotification(any(Notification.class))).thenThrow(DaoException.class);
        notificationService.acceptNotification(testNotification);
    }

    @Test
    public void testDeleteTrue() {
        try {
            when(notificationDao.delete(anyLong())).thenReturn(true);
            boolean result = notificationService.delete(1L);
            assertTrue(result);
        } catch (DaoException|ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testDeleteException() throws DaoException, ServiceException {
        when(notificationDao.delete(anyLong())).thenThrow(DaoException.class);
        notificationService.delete(1L);
    }

    @Test(dataProvider = "addNotifications")
    public void testAddNotificationsTrue(String firstMember,String secondMember) {
        try {
            Map<String,String> notificationParameters = new HashMap<>();
            notificationParameters.put(MapKeys.NUMBER_OF_MEMBERS,"2");
            notificationParameters.put(MapKeys.USER_LOGIN,"loginlogin");
            notificationParameters.put(MapKeys.TEAM_ID,"5");
            notificationParameters.put(MapKeys.MEMBER_INPUT+1,firstMember);
            notificationParameters.put(MapKeys.MEMBER_INPUT+2,secondMember);
            when(notificationDao.add(anyMap())).thenReturn(true);
            when(userDao.findUserByLogin(any(String.class))).thenReturn(userOptional);
            boolean result = notificationService.addNotifications(notificationParameters);
            assertTrue(result);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAddNotificationsFalse() {
        try {
            Map<String,String> notificationParameters = new HashMap<>();
            notificationParameters.put(MapKeys.NUMBER_OF_MEMBERS,"2");
            notificationParameters.put(MapKeys.USER_LOGIN,"loginlogin");
            notificationParameters.put(MapKeys.TEAM_ID,"5");
            notificationParameters.put(MapKeys.MEMBER_INPUT+1,"managerOne");
            notificationParameters.put(MapKeys.MEMBER_INPUT+2,"managerTwo");
            when(notificationDao.add(anyMap())).thenReturn(false);
            when(userDao.findUserByLogin(any(String.class))).thenReturn(userOptional);
            boolean result = notificationService.addNotifications(notificationParameters);
            assertFalse(result);
        } catch (DaoException | ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddNotificationsException() throws DaoException,ServiceException {
        Map<String,String> notificationParameters = new HashMap<>();
        notificationParameters.put(MapKeys.NUMBER_OF_MEMBERS,"2");
        notificationParameters.put(MapKeys.USER_LOGIN,"loginlogin");
        notificationParameters.put(MapKeys.TEAM_ID,"5");
        notificationParameters.put(MapKeys.MEMBER_INPUT+1,"managerOne");
        notificationParameters.put(MapKeys.MEMBER_INPUT+2,"managerTwo");
        when(notificationDao.add(anyMap())).thenThrow(DaoException.class);
        when(userDao.findUserByLogin(anyString())).thenReturn(userOptional);
        notificationService.addNotifications(notificationParameters);
    }


    @Test
    public void testAddNotificationTrue() {
        try {
            Map<String,String> notificationParameters = new HashMap<>();
            notificationParameters.put(MapKeys.USER_ID,"1");
            notificationParameters.put(MapKeys.TEAM_ID,"5");
            when(notificationDao.add(anyMap())).thenReturn(true);
            notificationService.addNotification(notificationParameters);
        } catch (DaoException|ServiceException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testAddNotificationException() throws ServiceException, DaoException {
        Map<String,String> notificationParameters = new HashMap<>();
        notificationParameters.put(MapKeys.USER_ID, "1");
        notificationParameters.put(MapKeys.TEAM_ID, "5");
        when(notificationDao.add(anyMap())).thenThrow(DaoException.class);
        notificationService.addNotification(notificationParameters);
    }
}