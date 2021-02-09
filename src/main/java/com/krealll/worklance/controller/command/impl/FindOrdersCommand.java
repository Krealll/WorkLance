package com.krealll.worklance.controller.command.impl;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.command.PaginationParameters;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Order;
import com.krealll.worklance.model.service.OrderService;
import com.krealll.worklance.model.service.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class FindOrdersCommand implements Command {


    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        OrderService orderService = new OrderServiceImpl();
        try {
            List<Order> orders = new ArrayList<>();
            String name = request.getParameter(RequestParameter.NAME);
            String customer = request.getParameter(RequestParameter.CUSTOMER);
            String keyWord = request.getParameter(RequestParameter.KEY);
            String upperBorder = request.getParameter(RequestParameter.UPPER);
            String lowerBorder = request.getParameter(RequestParameter.LOWER);
            if(name!=null){
                orders = orderService.findByName(name);
            }
            if(customer!=null){
                orders = orderService.findByCustomer(customer);
            }
            if(keyWord!=null){
                orders = orderService.findByKey(keyWord);
            }
            if(upperBorder!=null&&lowerBorder!=null){
                orders = orderService.findByBudget(lowerBorder,upperBorder);
            }

            request.setAttribute(RequestParameter.ORDERS,orders);
            request.setAttribute(RequestParameter.ELEMENTS_ON_PAGE, PaginationParameters.ORDERS_ON_PAGE);
            request.setAttribute(RequestParameter.CURRENT_PAGE_NUMBER,PaginationParameters.FIRST_PAGE);
            request.setAttribute(RequestParameter.PAGES_AMOUNT,Math.ceil((double)orders.size()
                    /PaginationParameters.ORDERS_ON_PAGE));
            request.setAttribute(RequestParameter.NUMBER_OF_ORDERS,orders.size());
            router = new Router(PagePath.ORDERS);
        } catch (ServiceException e){
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
