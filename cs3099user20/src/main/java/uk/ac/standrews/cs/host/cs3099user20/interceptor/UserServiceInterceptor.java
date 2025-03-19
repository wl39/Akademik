/**
 * You can use the Interceptor in Spring Boot to perform operations under the following situations −
 *
 * Before sending the request to the controller
 *
 * Before sending the response to the client
 *
 * For example, you can use an interceptor to add the request header before sending the request to the controller and add the response header before sending the response to the client.
 */

package uk.ac.standrews.cs.host.cs3099user20.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class UserServiceInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle
            (HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        
    }
    @Override
    public void afterCompletion
            (HttpServletRequest request, HttpServletResponse response, Object
                    handler, Exception exception) throws Exception {

        
    }
}
