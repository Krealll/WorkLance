package com.krealll.worklance.controller.command;

import com.krealll.worklance.controller.command.impl.*;
import com.krealll.worklance.controller.command.impl.page.*;

public enum CommandType {
    TO_PROFILE_PAGE{
        {
            this.command = new ToProfilePageCommand();
        }
    },
    FIND_EMPLOYEES_COMMAND{
        {
            this.command = new FindEmployeesCommand();
        }
    },
    LOGOUT{
        {
            this.command = new LogOutCommand();
        }
    },
    LEAVE_TEAM{
        {
            this.command = new LeaveTeam();
        }
    },
    TO_REGISTER_PAGE{
        {
            this.command = new ToRegisterPageCommand();
        }
    },
    TO_LOGIN_PAGE{
        {
            this.command = new ToLoginPageCommand();
        }
    },
    TO_SERVICES_PAGE{
        {
            this.command = new ToServicesPageCommand();
        }
    },
    TO_USER_ORDERS_PAGE {
        {
            this.command = new ToUserOrdersPage();
        }
    },
    CHANGE_LANGUAGE{
        {
            this.command = new ChangeLanguageCommand();
        }
    },
    FIND_USER_BY_ID{
        {
            this.command = new FindUserCommand();
        }
    },
    DELETE_MEMBER{
        {
            this.command = new DeleteMemberCommand();
        }
    },
    FIND_TEAM_BY_ID{
        {
            this.command = new FindTeamCommand();
        }
    },
    ACCEPT_NOTIFICATION{
        {
            this.command = new AcceptNotificationCommand();
        }
    },
    DECLINE_NOTIFICATION{
        {
            this.command = new DeclineNotificationCommand();
        }
    },
    FIND_ORDER_BY_ID{
        {
            this.command = new FindOrderCommand();
        }
    },
    LOGIN{
        {
            this.command = new LoginCommand();
        }
    },
    ADD_MEMBERS{
        {
            this.command = new AddMembersCommand();
        }
    },
    EMPLOYEE_PAGINATION{
        {
            this.command = new EmployeePaginationCommand();
        }
    },
    NOTIFICATION_PAGINATION{
        {
            this.command = new NotificationPaginationCommand();
        }
    },
    TEAM_PAGINATION{
        {
            this.command = new TeamPaginationCommand();
        }
    },
    ORDER_PAGINATION{
        {
            this.command = new OrderPaginationCommand();
        }
    },
    DELETE_ORDER{
        {
            this.command = new DeleteOrderCommand();
        }
    },
    TO_EDIT_ORDER_PAGE{
        {
            this.command = new ToEditOrderPageCommand();
        }
    },
    CREATE_ORDER{
        {
            this.command = new CreateOrderCommand();
        }
    },
    EDIT_ORDER{
        {
            this.command = new EditOrderCommand();
        }
    },
    FIND_TEAMS_COMMAND{
        {
            this.command = new FindTeamsCommand();
        }
    },
    FIND_ORDERS_COMMAND{
        {
            this.command = new FindOrdersCommand();
        }
    },
    TO_CREATE_TEAM_PAGE{
        {
            this.command = new ToCreateTeamPageCommand();
        }
    },
    TO_TEAMS_PAGE{
        {
            this.command = new ToTeamsPageCommand();
        }
    },
    TO_NOTIFICATIONS_PAGE{
        {
            this.command = new ToNotificationsPageCommand();
        }
    },
    TO_EDIT_PROFILE_PAGE{
        {
            this.command = new ToEditProfilePageCommand();
        }
    },
    EDIT_PROFILE{
        {
            this.command = new EditProfileCommand();
        }
    },
    TO_MEMBERS_ADD_PAGE{
        {
            this.command = new ToMembersAddPage();
        }
    },
    TO_CREATE_ORDER_PAGE{
        {
            this.command = new ToCreateOrderPageCommand();
        }
    },
    REGISTER{
        {
            this.command = new RegisterCommand();
        }
    },
    TO_EMPLOYEES_PAGE{
        {
            this.command = new ToEmployeesPageCommand();
        }
    },
    TO_ORDERS_PAGE{
        {
            this.command = new ToOrdersPageCommand();
        }
    },
    TO_EDIT_TEAM_INFO_PAGE{
        {
            this.command = new ToEditTeamInfoPageCommand();
        }
    },
    TO_EDIT_MEMBERS_PAGE{
        {
            this.command = new ToEditMembersPageCommand();
        }
    },
    DELETE_TEAM{
        {
            this.command = new DeleteTeamCommand();
        }
    },
    TO_HOME_PAGE{
        {
            this.command = new ToHomePageCommand();
        }
    },
    CREATE_TEAM{
        {
            this.command = new CreateTeamCommand();
        }
    },
    EDIT_TEAM{
        {
            this.command = new EditTeamCommand();
        }
    },
    INVITE_USER{
        {
            this.command = new InviteUserCommand();
        }
    },
    MEMBER_PAGINATION{
        {
            this.command = new MemberPaginationCommand();
        }
    },
    TO_USER_TEAM_PAGE{
        {
            this.command = new ToTeamCommand();
        }
    },
    EMPTY_COMMAND{
        {
            this.command = new EmptyCommand();
        }
    };

    Command command;

    public Command getCommand() {
        return command;
    }
}
