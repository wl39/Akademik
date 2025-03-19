/**
 * Home Page For Web - index.html runs on REACT
 */
package uk.ac.standrews.cs.host.cs3099user20.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping(value = {"/", "/login", "/register", "/articles", "/articles/{articleID}","/articles/upload","/moderate", "/moderate/reviewers","/moderate/articles", "/moderate/articles/{modID}","/users/profile"})
    public String index() {
        return "index.html";
    }
}
