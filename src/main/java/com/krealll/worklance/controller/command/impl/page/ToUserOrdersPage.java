package com.krealll.worklance.controller.command.impl.page;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.PaginationParameters;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Order;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.OrderService;
import com.krealll.worklance.model.service.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ToUserOrdersPage  implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        OrderService orderService = new OrderServiceImpl();
        try {
            List<Order> orders;
            HttpSession session = request.getSession();
            User user =(User) session.getAttribute(SessionAttribute.USER);
            orders = orderService.findByUserId(user.getId());
            request.setAttribute(RequestParameter.ORDERS,orders);
            request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER,PaginationParameters.FIRST_PAGE);
            request.setAttribute(RequestParameter.PAGES_AMOUNT,Math.ceil((double)orders.size()
                    /PaginationParameters.ORDERS_ON_PAGE));
            request.setAttribute(RequestParameter.ELEMENTS_ON_PAGE, PaginationParameters.ORDERS_ON_PAGE);
            request.setAttribute(RequestParameter.NUMBER_OF_ORDERS,orders.size());
            router = new Router(PagePath.USER_ORDERS);

        } catch (ServiceException e){
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
