package com.krealll.worklance.controller.filter;

import java.io.IOException;
import javax.servlet.*;

public class EncodingFilter implements Filter {
    private String code;
    public void init(FilterConfig fConfig) throws ServletException {
        code = fConfig.getInitParameter("encoding");
    }
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);

    }
    public void destroy() {
        code = null;
    }
}
