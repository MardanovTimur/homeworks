package ru.itis.inform.filters;

import ru.itis.inform.models.User;
import ru.itis.inform.services.TokenService;
import ru.itis.inform.services.TokenServiceImpl;
import ru.itis.inform.services.UserService;
import ru.itis.inform.services.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Тимур on 12.10.2016.
 */
public class IndexFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Object session = ((HttpServletRequest) servletRequest).getSession().getAttribute("current_user");
        Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("current_user")) {

                    TokenService tokenService = new TokenServiceImpl();
                    UserService userService = new UserServiceImpl();

                    //find user_id -> token  == in cooks table
                    String user_id = tokenService.findToken(cookie.getValue());
                    if (user_id == null) {
                        ((HttpServletResponse) servletResponse).sendError(403, "This cookie was changed or db havent your cooks!");
                        return;
                    }

                    try {
                        userService.findId(user_id);
                    } catch (Exception e) {
                        ((HttpServletResponse) servletResponse).sendError(403, "This user not found!");
                        return;
                    }
                    User user = userService.findId(user_id);

                    if (user != null) {
                        if (session == null)
                            ((HttpServletRequest) servletRequest).getSession().setAttribute("current_user", user);
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    } else {
                        ((HttpServletResponse) servletResponse).sendError(403, "User not found!");
                        return;
                    }
                }
            }
        }
        if (session == null) {
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {

    }
}
