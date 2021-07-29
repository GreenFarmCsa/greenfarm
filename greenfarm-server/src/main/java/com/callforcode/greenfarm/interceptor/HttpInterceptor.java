package com.callforcode.greenfarm.interceptor;

import com.callforcode.greenfarm.consts.GreenFarmConst;
import com.callforcode.greenfarm.consts.GreenFarmSwitch;
import com.callforcode.greenfarm.entity.GFUser;
import com.callforcode.greenfarm.exception.GFIllegalSessionException;
import com.callforcode.greenfarm.exception.GFOperationDenieException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (GreenFarmSwitch.isSessionAuthenticationOn()) {
            if (handler instanceof HandlerMethod) {
                //check login
                HttpSession session = request.getSession();
                Object user = session.getAttribute(GreenFarmConst.GRF_LOGIN_SESSION);
                if (user == null) {
                    throw new GFIllegalSessionException("session for user is null,please login in!");
                }
                GFUser gfUser = (GFUser) user;
                if (GreenFarmConst.GRF_USER_GUEST.contains(gfUser.getUsername())) {
                    HandlerMethod requestMethod = (HandlerMethod) handler;
                    if (!requestMethod.getMethod().getName().startsWith("query")) {
                        throw new GFOperationDenieException("current user can only access query function");
                    }
                }
            }
        }
        return true;
    }

}
