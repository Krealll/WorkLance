package com.krealll.worklance.controller.command;

import com.krealll.worklance.controller.command.impl.*;
import com.krealll.worklance.controller.command.impl.page.*;

public enum CommandType {
    LOGIN_COMMAND{
        {
            this.command = new LoginCommand();
        }
    },
    CREATE_TEAM{
        {
            this.command = new CreateTeamCommand();
        }
    },
    CREATE_ORDER{
        {
            this.command = new CreateOrderCommand();
        }
    },
    REGISTER{
        {
            this.command = new RegisterCommand();
        }
    },
    LOGOUT_COMMAND{
        {
            this.command = new LogoutCommand();
        }
    },
    TO_GETBACK_PAGE_COMMAND{
        {
            this.command = new ToGetBackPageCommand();
        }
    },
    TO_HOME_PAGE_COMMAND{
        {
            this.command = new ToHomePageCommand();
        }
    },
    TO_ORDER_CREATION_PAGE_COMMAND{
        {
            this.command= new ToOrderCreationPageCommand();
        }
    },
    TO_ORDERS_PAGE_COMMAND{
        {
            this.command = new ToOrdersPageCommand();
        }
    },
    TO_PROFILE_PAGE_COMMAND{
        {
            this.command = new ToProfilePageCommand();
        }
    },
    TO_SINGIN_PAGE_COMMAND{
        {
            this.command= new ToSinginPageCommand();
        }
    },
    TO_SIGNUP_PAGE_COMMAND{
        {
            this.command = new ToSignupPageCommand();
        }
    },
    TO_SUCCESSFUL_SIGNUP_PAGE_COMMAND{
        {
            this.command= new ToSuccessfulSignupPageCommand();
        }
    },
    TO_SUCCESSFUL_ORDER_PAGE_COMMAND{
        {
            this.command= new ToSuccessfulOrderPageCommand();
        }
    },
    TO_SUCCESSFUL_TEAM_PAGE_COMMAND{
        {
            this.command= new ToSuccessfulTeamPageCommand();
        }
    },
    TO_TEAM_CREATION_PAGE_COMMAND{
        {
            this.command = new ToTeamCreationPageCommand();
        }
    },
    TO_TEAMS_PAGE_COMMAND{
        {
            this.command = new ToTeamsPageCommand();
        }
    },
    TO_WORKERS_PAGE_COMMAND{
        {
            this.command = new ToWorkersPageCommand();
        }
    },
    CHANGE_LANGUAGE_COMMAND{
        {
            this.command = new ChangeLanguageCommand();
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
