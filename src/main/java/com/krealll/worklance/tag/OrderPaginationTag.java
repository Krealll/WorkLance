package com.krealll.worklance.tag;

import com.krealll.worklance.controller.RequestParameter;
import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.model.entity.Order;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class OrderPaginationTag extends TagSupport {

    private int pageNumber;
    private int ordersOnPage;
    private static final String CONTENT_PATH = "properties/language";
    private final static String RESOURCE_NAME = "status.";
    private final static String CUSTOMER_RESOURCE = "orders.customer";


    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setOrdersOnPage(int ordersOnPage) {
        this.ordersOnPage = ordersOnPage;
    }

    @Override
    public int doStartTag() throws JspException {
        ServletRequest request = pageContext.getRequest();
        HttpSession session = pageContext.getSession();
        String attrLocale = (String) session.getAttribute(SessionAttribute.LOCALE);
        Locale locale = Locale.forLanguageTag(attrLocale.replace("_", "-"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle(CONTENT_PATH, locale);

        List<Order> orders = (List<Order>) request.getAttribute(RequestParameter.ORDERS);
        int start = (pageNumber-1)*ordersOnPage;
        int end =Math.min(orders.size(),pageNumber*ordersOnPage);
        for (int i = start; i < end; i++) {
            Order order = orders.get(i);

            StringBuilder result = new StringBuilder("<div class=\"col-12 col-md-6 col-lg-4\">" +
                    "<div class=\"clean-product-item\" style=\" border-right: 0px; border-bottom: 0px;\">" +
                    "<a class=\"link\" href=\"projectServlet?order_id=");
            result.append(order.getId());
            result.append("&command=find_order_by_id\">");
            result.append(order.getName());
            result.append("</a>" +
                    "<div>" +
                    "<a class=\"link\" href=\"projectServlet?user_id=");
            result.append(order.getCustomer());
            result.append("&command=find_user_by_id\">");
            result.append(resourceBundle.getString(CUSTOMER_RESOURCE));
            result.append("</a>" +
                    "</div>" +
                    "<div>" +
                    "<label>");
            result.append(order.getBudget());
            result.append("</label>" +
                    "</div>" +
                    "<div>" +
                    "<label>");
            result.append(resourceBundle.getString(RESOURCE_NAME +
                    order.getStatus().getName().replace(" ","").toLowerCase()));
            result.append("</label>" +
                    "</div>" +
                    "<div>" +
                    "<label>");
            result.append(order.getDate());
            result.append("</label>" +
                    "</div>" +
                    "</div>" +
                    "</div>");
            try {
                pageContext.getOut().write(result.toString());
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }

}
