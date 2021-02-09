package com.krealll.worklance.model.service.impl;

import com.krealll.worklance.exception.DaoException;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.dao.impl.TeamDaoImpl;
import com.krealll.worklance.model.entity.Team;
import com.krealll.worklance.util.MapKeys;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;

@PrepareForTest(TeamServiceImpl.class)
public class TeamServiceImplTest {
    private TeamDaoImpl teamDao;
    private TeamServiceImpl teamService;
    private Team firstTeam;
    private Team secondTeam;
    private Optional<Team> teamOptional;
    private List<Team> teams;

    @BeforeMethod
    public void setUp() {
        teamDao = mock(TeamDaoImpl.class);
        teamService = new TeamServiceImpl();
        Whitebox.setInternalState(TeamDaoImpl.class,"instance",teamDao);
        firstTeam = new Team(1L,"teamName","description");
        secondTeam = new Team(2L,"teamNameTwo","description");
        teamOptional = Optional.of(firstTeam);
        teams = new ArrayList<>();
        teams.add(firstTeam);
        teams.add(secondTeam);
    }

    @AfterClass
    public void tearDown() {
        teamDao = null;
        teamService =null;
        firstTeam = null;
        secondTeam = null;
        teamOptional = Optional.empty();
        teams = null;
    }

    @DataProvider(name = "correctTeam")
    public Object[][] createUpdateParameters(){
        return new Object[][]{
                {"nameName","description is more than 20 symbols"},
                {"nameTwo","description is more than 20 symbols"},
                {"nameThree","description is more than 20 symbols"}
        };
    }

    @DataProvider(name = "incorrectTeam")
    public Object[][] createIncorrectOrderParameters(){
        return new Object[][]{
                {"name name","description is more than 20 symbols"},
                {"name","description"},
                {"name","description is more than 20 symbols"}
        };
    }


    @Test
    public void testFindByNameTrue() {
        try {
            when(teamDao.findByName(anyString())).thenReturn(teamOptional);
            Optional<Team> foundTeam = teamService.findByName("name");;
            assertEquals(foundTeam,teamOptional);
        } catch (DaoException| ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByNameException() throws DaoException, ServiceException{
        when(teamDao.findByName(anyString())).thenThrow(DaoException.class);
        teamService.findByName("name");
    }

    @Test
    public void testFindByManagerTrue() {
        try {
            when(teamDao.findByManager(anyString())).thenReturn(teams);
            List<Team> foundTeams = teamService.findByManager("manager");
            assertEquals(foundTeams,teams);
        } catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByManagerException() throws DaoException, ServiceException{
        when(teamDao.findByManager(anyString())).thenThrow(DaoException.class);
        teamService.findByManager("name");
    }

    @Test
    public void testFindByKeyTrue() {
        try {
            when(teamDao.findByKey(anyString())).thenReturn(teams);
            List<Team> foundTeams = teamService.findByKey("manager");
            assertEquals(foundTeams,teams);
        } catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByKeyException() throws DaoException, ServiceException{
        when(teamDao.findByKey(anyString())).thenThrow(DaoException.class);
        teamService.findByKey("name");
    }

    @Test
    public void testFindByIdTrue() {
        try {
            when(teamDao.findById(anyLong())).thenReturn(teamOptional);
            Optional<Team> foundTeam = teamService.findById(1L);;
            assertEquals(foundTeam,teamOptional);
        } catch (DaoException| ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByIdException() throws DaoException, ServiceException{
        when(teamDao.findById(anyLong())).thenThrow(DaoException.class);
        teamService.findById(1L);
    }

    @Test
    public void testDeleteTrue() {
        try {
            when(teamDao.delete(anyLong())).thenReturn(true);
            boolean result = teamService.delete(1L);
            assertTrue(result);
        }catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testDeleteException() throws DaoException, ServiceException{
        when(teamDao.delete(anyLong())).thenThrow(DaoException.class);
        teamService.delete(1L);
    }

    @Test(dataProvider = "correctTeam")
    public void testUpdateTrue(String name, String description) {
        try {
            Map<String,String> parameters = new HashMap<>();
            parameters.put(MapKeys.NAME,name);
            parameters.put(MapKeys.TEAM_ID,"1");
            parameters.put(MapKeys.DESCRIPTION,description);

            when(teamDao.update(any(Team.class))).thenReturn(true);
            boolean result = teamService.update(parameters);
            assertTrue(result);
        }catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "incorrectTeam")
    public void testUpdateFalse(String name, String description) {
        try {
            Map<String,String> parameters = new HashMap<>();
            parameters.put(MapKeys.NAME,name);
            parameters.put(MapKeys.TEAM_ID,"1");
            parameters.put(MapKeys.DESCRIPTION,description);

            when(teamDao.update(any(Team.class))).thenReturn(false);
            boolean result = teamService.update(parameters);
            assertFalse(result);
        }catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testUpdateException() throws DaoException, ServiceException{
        Map<String,String> parameters = new HashMap<>();
        parameters.put(MapKeys.NAME,"name");
        parameters.put(MapKeys.TEAM_ID,"1");
        parameters.put(MapKeys.DESCRIPTION,"description");

        when(teamDao.update(any(Team.class))).thenThrow(DaoException.class);
        teamService.update(parameters);
    }

    @Test(dataProvider = "correctTeam")
    public void testCreateTrue(String name, String description) {
        try {
            Map<String,String> parameters = new HashMap<>();
            parameters.put(MapKeys.NAME,name);
            parameters.put(MapKeys.TEAM_ID,"1");
            parameters.put(MapKeys.DESCRIPTION,description);

            when(teamDao.add(anyMap())).thenReturn(true);
            boolean result = teamService.create(parameters);
            assertTrue(result);
        }catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "incorrectTeam")
    public void testCreateFalse(String name, String description) {
        try {
            Map<String,String> parameters = new HashMap<>();
            parameters.put(MapKeys.NAME,name);
            parameters.put(MapKeys.TEAM_ID,"1");
            parameters.put(MapKeys.DESCRIPTION,description);

            when(teamDao.add(anyMap())).thenReturn(false);
            boolean result = teamService.create(parameters);
            assertFalse(result);
        }catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testCreateException() throws DaoException, ServiceException{
        Map<String,String> parameters = new HashMap<>();
        parameters.put(MapKeys.NAME,"name");
        parameters.put(MapKeys.TEAM_ID,"1");
        parameters.put(MapKeys.DESCRIPTION,"description");

        when(teamDao.add(anyMap())).thenThrow(DaoException.class);
        teamService.create(parameters);
    }

    @Test
    public void testFindByManagerIdTrue() {
        try {
            when(teamDao.findByManagerId(anyLong())).thenReturn(teamOptional);
            Optional<Team> foundTeam = teamService.findByManagerId(1L);;
            assertEquals(foundTeam,teamOptional);
        } catch (DaoException| ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindByManagerIdException() throws DaoException, ServiceException{
        when(teamDao.findByManagerId(anyLong())).thenThrow(DaoException.class);
        teamService.findByManagerId(1L);
    }

    @Test
    public void testFindAllTrue() {
        try {
            when(teamDao.findAll()).thenReturn(teams);
            List<Team> foundTeams = teamService.findAll();
            assertEquals(foundTeams,teams);
        }catch (DaoException|ServiceException e){
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindAllException() throws DaoException, ServiceException{
        when(teamDao.findAll()).thenThrow(DaoException.class);
        teamService.findAll();
    }
}