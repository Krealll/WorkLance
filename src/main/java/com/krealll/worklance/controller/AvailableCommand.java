package com.krealll.worklance.controller;

import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.model.entity.type.UserRole;

import java.util.EnumSet;
import java.util.Set;

import static com.krealll.worklance.controller.command.CommandType.*;

public enum AvailableCommand {

    GUEST(EnumSet.of(REGISTER,
            LOGIN,
            TO_EMPLOYEES_PAGE,
            TO_HOME_PAGE,
            TO_LOGIN_PAGE,
            TO_ORDERS_PAGE,
            TO_REGISTER_PAGE,
            TO_SERVICES_PAGE,
            TO_TEAMS_PAGE,
            FIND_ORDER_BY_ID,
            CHANGE_LANGUAGE,
            EMPLOYEE_PAGINATION,
            FIND_EMPLOYEES_COMMAND,
            FIND_USER_BY_ID,
            FIND_ORDERS_COMMAND,
            ORDER_PAGINATION,
            TEAM_PAGINATION,
            FIND_TEAM_BY_ID,
            FIND_TEAMS_COMMAND),UserRole.GUEST.getName()),
    USER(EnumSet.of(TO_CREATE_ORDER_PAGE,
            TO_CREATE_TEAM_PAGE,
            TO_EDIT_ORDER_PAGE,
            TO_EDIT_PROFILE_PAGE,
            TO_EMPLOYEES_PAGE,
            TO_HOME_PAGE,
            FIND_EMPLOYEES_COMMAND,
            TO_NOTIFICATIONS_PAGE,
            TO_USER_ORDERS_PAGE,
            TO_ORDERS_PAGE,
            TO_PROFILE_PAGE,
            TO_SERVICES_PAGE,
            TO_TEAMS_PAGE,
            ACCEPT_NOTIFICATION,
            CHANGE_LANGUAGE,
            CREATE_ORDER,
            CREATE_TEAM,
            DECLINE_NOTIFICATION,
            DELETE_ORDER,
            EDIT_ORDER,
            EDIT_PROFILE,
            EMPLOYEE_PAGINATION,
            FIND_ORDER_BY_ID,
            FIND_ORDERS_COMMAND,
            FIND_TEAMS_COMMAND,
            FIND_TEAM_BY_ID,
            FIND_USER_BY_ID,
            LEAVE_TEAM,
            LOGOUT,
            NOTIFICATION_PAGINATION,
            ORDER_PAGINATION,
            TEAM_PAGINATION), UserRole.USER.getName()),
    MANAGER(EnumSet.of(TO_CREATE_ORDER_PAGE,
            TO_MEMBERS_ADD_PAGE,
            TO_EDIT_ORDER_PAGE,
            TO_EDIT_PROFILE_PAGE,
            TO_EMPLOYEES_PAGE,
            FIND_EMPLOYEES_COMMAND,
            TO_HOME_PAGE,
            TO_NOTIFICATIONS_PAGE,
            TO_USER_ORDERS_PAGE,
            TO_ORDERS_PAGE,
            TO_PROFILE_PAGE,
            TO_SERVICES_PAGE,
            TO_TEAMS_PAGE,
            ACCEPT_NOTIFICATION,
            CHANGE_LANGUAGE,
            CREATE_ORDER,
            DECLINE_NOTIFICATION,
            DELETE_ORDER,
            EDIT_ORDER,
            EDIT_PROFILE,
            EMPLOYEE_PAGINATION,
            FIND_ORDERS_COMMAND,
            FIND_ORDER_BY_ID,
            FIND_ORDERS_COMMAND,
            FIND_TEAMS_COMMAND,
            FIND_TEAM_BY_ID,
            FIND_USER_BY_ID,
            LOGOUT,
            NOTIFICATION_PAGINATION,
            ORDER_PAGINATION,
            TEAM_PAGINATION,
            MEMBER_PAGINATION,
            INVITE_USER,
            EDIT_TEAM,
            DELETE_TEAM,
            DELETE_MEMBER,
            ADD_MEMBERS,
            TO_USER_TEAM_PAGE,
            TO_EDIT_TEAM_INFO_PAGE,
            TO_EDIT_MEMBERS_PAGE),UserRole.MANAGER.getName());

    private final Set<CommandType> availableCommands;
    private final String name;

    AvailableCommand(Set<CommandType> availableCommands, String name) {
        this.availableCommands = availableCommands;
        this.name = name;
    }

    public Set<CommandType> getAvailableCommands() {
        return availableCommands;
    }

    public String getName() {
        return name;
    }
}
