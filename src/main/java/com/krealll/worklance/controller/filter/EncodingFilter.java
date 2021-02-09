package com.krealll.worklance.controller.filter;

import java.io.IOException;
import javax.servlet.*;

public class EncodingFilter implements Filter {

    private final static String ENCODING_PARAMETER = "encoding";
    private final static String ENCODING = "UTF-8";

    private String code;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        code = fConfig.getInitParameter(ENCODING_PARAMETER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        response.setCharacterEncoding(ENCODING);
        request.setCharacterEncoding(ENCODING);
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        code = null;
    }
}
