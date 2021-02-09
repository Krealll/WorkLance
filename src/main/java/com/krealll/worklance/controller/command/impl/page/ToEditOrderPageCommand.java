package com.krealll.worklance.controller.command.impl.page;

import com.krealll.worklance.controller.PagePath;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.controller.command.Command;
import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.command.CommandType;
import com.krealll.worklance.controller.router.Router;
import com.krealll.worklance.exception.ServiceException;
import com.krealll.worklance.model.entity.Order;
import com.krealll.worklance.model.entity.User;
import com.krealll.worklance.model.service.OrderService;
import com.krealll.worklance.model.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class ToEditOrderPageCommand  implements Command {

    private final static Logger logger = LogManager.getLogger(ToEditOrderPageCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        OrderService service = new OrderServiceImpl();
        HttpSession session = request.getSession();
        try {
            User user = (User)request.getSession().getAttribute(SessionAttribute.USER);
            Long orderId = Long.parseLong(request.getParameter(RequestParameter.ORDER_ID));
            Optional<Order> optionalOrder = service.findById(orderId);
            if(optionalOrder.isPresent()){
                if(optionalOrder.get().getCustomer().equals(user.getId())){
                    session.setAttribute(SessionAttribute.CHOSEN_ORDER,optionalOrder.get());
                    router = new Router(PagePath.ORDER_EDIT);
                } else {
                    router = new Router(Router.Type.REDIRECT, getRedirectPage(request,
                            CommandType.FIND_USER_BY_ID,RequestParameter.USER_ID_REDIRECT+ user.getId() ));
                }
            } else {
                logger.log(Level.ERROR,"Error during searching for order to edit");
                router = new Router(PagePath.ERROR_500);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
