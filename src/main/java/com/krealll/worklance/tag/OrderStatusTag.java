package com.krealll.worklance.tag;

import com.krealll.worklance.controller.SessionAttribute;
import com.krealll.worklance.model.entity.Order;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class OrderStatusTag extends TagSupport {

    private Order currentOrder;

    private static final String CONTENT_PATH = "properties/language";
    private final static String RESOURCE_NAME = "status.";

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        String attrLocale = (String) session.getAttribute(SessionAttribute.LOCALE);
        Locale locale = Locale.forLanguageTag(attrLocale.replace("_", "-"));
        ResourceBundle resourceBundle = ResourceBundle.getBundle(CONTENT_PATH, locale);

        StringBuilder result = new StringBuilder(resourceBundle.getString(RESOURCE_NAME +
                currentOrder.getStatus().getName().replace(" ","").toLowerCase()));
        try {
            pageContext.getOut().write(result.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

}
