package com.krealll.worklance.model.service.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.dao.impl.UserDaoImpl;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.entity.type.Specialization;
import com.krealll.worklance.model.entity.type.UserRole;
import com.krealll.worklance.util.MapKeys;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

@PrepareForTest(UserDaoImpl.class)
public class UserServiceImplTest {
    private UserDaoImpl userDao;
    private UserServiceImpl userService;
    private Optional<User> userOptional;
    private Optional<String> optionalPassword;
    private User testUser;
    private User anotherTestUser;
    private List<User> users;

    @BeforeMethod
    public void setUp() {
        userDao = mock(UserDaoImpl.class);
        userService = new UserServiceImpl();
        Whitebox.setInternalState(UserDaoImpl.class,"instance",userDao);
        testUser = new User(1L,"login","email", UserRole.USER, Specialization.OTHER, true,"description",1L);
        anotherTestUser = new User(2L,"loginTwo","emailTwo", UserRole.USER, Specialization.OTHER, false,"descriptionTwo",1L);
        userOptional = Optional.of(testUser);
        optionalPassword = Optional.of("d54d1702ad0f8326224b817c796763c9");
        users = new ArrayList<>();
        users.add(testUser);
        users.add(anotherTestUser);
    }

    @AfterClass
    public void tearDown() {
        userDao=null;
        userService=null;
        userOptional=Optional.empty();
        testUser = null;
        anotherTestUser = null;
        users=null;
    }

    @DataProvider(name="correctUserRegister")
    public Object[][] createCorrectRegistrationParameters(){
        return new Object[][]{
                {"email@email.com","login1"},
                {"somemail@gmail.com","login_"},
                {"email@mail.ru","login"}
        };
    }

    @DataProvider(name="incorrectUserRegister")
    public Object[][] createIncorrectRegistrationParameters(){
        return new Object[][]{
                {"email","login1"},
                {"somemail@gmail.com","login_qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"},
                {"email@mail.ru","lo gin"}

        };
    }

    @DataProvider(name="correctPasswordCheck")
    public Object[][] createCorrectRegistrationPassword(){
        return new Object[][]{
                {"login","11223344"},
                {"login1","11223344"},
                {"login_","11223344"}
        };
    }

    @DataProvider(name="incorrectPasswordCheck")
    public Object[][] crateIncorrectRegistrationPassword(){
        return new Object[][]{
                {"login","11223   344"},
                {"login1","aqwerty12121212ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss121212121"},
                {"log    in","11223344"},
                {"1login","11223344"}
        };
    }

    @DataProvider(name="correctUpdate")
    public Object[][] createCorrectUpdateParameters(){
        return new Object[][]{
                {"epam@email.com","login1"},
                {"newmail@gmail.com","log___in_"},
                {"email@gmail.ru","login"}
        };
    }

    @DataProvider(name="incorrectUpdate")
    public Object[][] createIncorrectUpdateParameters(){
        return new Object[][]{
                {"email_____","login_qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"},
                {"newmail@","login1"},
                {"email@mail.ru","lo gin"}

        };
    }

    @Test(dataProvider = "correctUserRegister")
    public void testRegisterUserTrue(String email, String login) {
        Map<String,String> registrationParameters = new HashMap<>();
        registrationParameters.put(MapKeys.LOGIN,login);
        registrationParameters.put(MapKeys.EMAIL,email);
        registrationParameters.put(MapKeys.PASSWORD,"12345678");
        registrationParameters.put(MapKeys.REPEAT_PASSWORD,"12345678");
        registrationParameters.put(MapKeys.SPECIALIZATION,Specialization.IT.getName());

        try {
            when(userDao.add(any(Map.class))).thenReturn(true);
            boolean result = userService.registerUser(registrationParameters);
            assertTrue(result);
        } catch (DaoException | ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "incorrectUserRegister")
    public void testRegisterUserFalse(String email, String login) {
        Map<String,String> registrationParameters = new HashMap<>();
        registrationParameters.put(MapKeys.LOGIN,login);
        registrationParameters.put(MapKeys.EMAIL,email);
        registrationParameters.put(MapKeys.PASSWORD,"12345678");
        registrationParameters.put(MapKeys.REPEAT_PASSWORD,"12345678");
        registrationParameters.put(MapKeys.SPECIALIZATION,Specialization.IT.getName());

        try {
            when(userDao.add(any(Map.class))).thenReturn(true);
            boolean result = userService.registerUser(registrationParameters);
            assertFalse(result);
        } catch (DaoException | ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testRegisterUserException() throws ServiceException, DaoException {
        Map<String,String> registrationParameters = new HashMap<>();
        registrationParameters.put(MapKeys.LOGIN,"login");
        registrationParameters.put(MapKeys.EMAIL,"email@email.com");
        registrationParameters.put(MapKeys.PASSWORD,"12345678");
        registrationParameters.put(MapKeys.REPEAT_PASSWORD,"12345678");
        registrationParameters.put(MapKeys.SPECIALIZATION,Specialization.IT.getName());
        when(userDao.add(any(Map.class))).thenThrow(DaoException.class);
        userService.registerUser(registrationParameters);
    }

    @Test(dataProvider = "correctPasswordCheck")
    public void testCheckLoginAndPasswordTrue(String login, String password) {
        try {
            when(userDao.findPasswordByLogin(any(String.class))).thenReturn(optionalPassword);
            boolean result = userService.checkLoginAndPassword(login,password);
            assertTrue(result);
        } catch (DaoException | ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "incorrectPasswordCheck")
    public void testCheckLoginAndPasswordFalse(String login, String password) {
        try {
            when(userDao.findPasswordByLogin(any(String.class))).thenReturn(optionalPassword);
            boolean result = userService.checkLoginAndPassword(login,password);
            assertFalse(result);
        } catch (DaoException | ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCheckLoginAndPasswordException() throws DaoException, ServiceException {
        when(userDao.findPasswordByLogin(any(String.class))).thenThrow(DaoException.class);
        userService.checkLoginAndPassword("login","11111111");
    }


    @Test
    public void testFindAllUsersTrue() {
        try {
            when(userDao.findAll()).thenReturn(users);
            List<User> foundUsers = userService.findAll();
            assertEquals(foundUsers,users);
        } catch (DaoException | ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindAllUsersException() throws DaoException, ServiceException {
        when(userDao.findAll()).thenThrow(DaoException.class);
        userService.findAll();
    }

    @Test
    public void testFindBySpecializationTrue() {
        try {
            when(userDao.findBySpecialization(any(String.class))).thenReturn(users);
            List<User> foundUsers = userService.findBySpecialization(Specialization.OTHER.getName());
            assertEquals(foundUsers,users);
        } catch (DaoException| ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindBySpecializationException() throws ServiceException, DaoException {
        when(userDao.findBySpecialization(any(String.class))).thenThrow(DaoException.class);
        userService.findBySpecialization(Specialization.OTHER.getName());
    }

    @Test
    public void testFindByKeyTrue() {
        try {
            when(userDao.findByKey(any(String.class))).thenReturn(users);
            List<User> foundUsers = userService.findByKey("desc");
            assertEquals(foundUsers,users);
        } catch (DaoException| ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByKeyException() throws ServiceException, DaoException {
        when(userDao.findByKey(any(String.class))).thenThrow(DaoException.class);
        userService.findByKey("desc");
    }

    @Test
    public void testFindByTeamTrue() {
        try {
            when(userDao.findByTeamId(any(Long.class))).thenReturn(users);
            List<User> foundUsers = userService.findByTeamId(1L);
            assertEquals(foundUsers,users);
        } catch (DaoException| ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByTeamException() throws ServiceException, DaoException {
        when(userDao.findByTeamId(any(Long.class))).thenThrow(DaoException.class);
        userService.findByTeamId(1L);
    }

    @Test
    public void testFindByLoginTrue() {
        try {
            when(userDao.findUserByLogin(any(String.class))).thenReturn(userOptional);
            Optional<User> foundUser = userService.findByLogin("login");
            assertEquals(foundUser,userOptional);
        } catch (DaoException| ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByLoginException() throws DaoException, ServiceException {
        when(userDao.findUserByLogin(any(String.class))).thenThrow(DaoException.class);
        userService.findByLogin("login");
    }

    @Test
    public void testFindByManagerTrue() {
        try {
            when(userDao.findManager(any(Long.class))).thenReturn(userOptional);
            Optional<User> foundUser = userService.findManager(1L);
            assertEquals(foundUser,userOptional);
        } catch (DaoException| ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByManagerException() throws DaoException, ServiceException {
        when(userDao.findManager(any(Long.class))).thenThrow(DaoException.class);
        userService.findManager(1L);
    }

    @Test
    public void testFindByIdTrue() {
        try {
            when(userDao.findById(any(Long.class))).thenReturn(userOptional);
            Optional<User> foundUser = userService.findById(1L);
            assertEquals(foundUser,userOptional);
        } catch (DaoException| ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByIdException() throws DaoException, ServiceException {
        when(userDao.findById(any(Long.class))).thenThrow(DaoException.class);
        userService.findById(1L);
    }

    @Test
    public void testDeleteMemberTrue() {
        try {
            User member = new User(2L,"login","email",
                        UserRole.USER, Specialization.OTHER, true,"description",1L);
            when(userDao.updateMembership(any(User.class))).thenReturn(true);
            boolean result = userService.deleteMember(member);
            assertTrue(result);
        } catch (DaoException | ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testDeleteMemberException() throws ServiceException, DaoException {
        User member = new User(2L,"login","email",
                UserRole.USER, Specialization.OTHER, true,"description",1L);
        when(userDao.updateMembership(any(User.class))).thenThrow(DaoException.class);
        userService.deleteMember(member);
    }

    @Test(dataProvider = "correctUpdate")
    public void testUpdateUserTrue(String email, String login) {
        Map<String,String> updateParameters = new HashMap<>();
        updateParameters.put(MapKeys.LOGIN,login);
        updateParameters.put(MapKeys.EMAIL,email);
        updateParameters.put(MapKeys.SHOW_EMAIL,"true");
        updateParameters.put(MapKeys.DESCRIPTION,"description");
        updateParameters.put(MapKeys.SPECIALIZATION,Specialization.IT.getName());
        updateParameters.put(MapKeys.USER_ID,"1");
        try {
            when(userDao.update(any(User.class))).thenReturn(true);
            boolean result = userService.update(updateParameters);
            assertTrue(result);
        } catch (DaoException | ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "incorrectUpdate")
    public void testUpdateUserFalse(String email, String login) {
        Map<String,String> updateParameters = new HashMap<>();
        updateParameters.put(MapKeys.LOGIN,login);
        updateParameters.put(MapKeys.EMAIL,email);
        updateParameters.put(MapKeys.SHOW_EMAIL,"true");
        updateParameters.put(MapKeys.DESCRIPTION,"description");
        updateParameters.put(MapKeys.SPECIALIZATION,Specialization.IT.getName());
        updateParameters.put(MapKeys.USER_ID,"1");
        try {
            when(userDao.update(any(User.class))).thenReturn(true);
            boolean result = userService.update(updateParameters);
            assertFalse(result);
        } catch (DaoException | ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateUserException() throws ServiceException, DaoException {
        Map<String,String> updateParameters = new HashMap<>();
        updateParameters.put(MapKeys.LOGIN,"login");
        updateParameters.put(MapKeys.EMAIL,"email@mail.com");
        updateParameters.put(MapKeys.SHOW_EMAIL,"true");
        updateParameters.put(MapKeys.DESCRIPTION,"description");
        updateParameters.put(MapKeys.SPECIALIZATION,Specialization.IT.getName());
        updateParameters.put(MapKeys.USER_ID,"1");
        when(userDao.update(any(User.class))).thenThrow(DaoException.class);
        userService.update(updateParameters);
    }

    @Test
    public void testLeaveTrue() {
        try {
            when(userDao.leaveTeam(any(Long.class))).thenReturn(true);
            boolean result = userService.leaveTeam(1L);
            assertTrue(result);
        } catch (DaoException | ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testLeaveException() throws ServiceException, DaoException {
        when(userDao.leaveTeam(any(Long.class))).thenThrow(DaoException.class);
        userService.leaveTeam(1L);
    }
}