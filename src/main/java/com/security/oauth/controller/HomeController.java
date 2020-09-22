package com.security.oauth.controller;

import com.security.oauth.model.User;
import com.security.oauth.repo.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
//Enabling Swagger UI for root directory access
//URL/swagger-ui.html   URL/v2/api-docs
@Api(value = "Common Controller")
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/user")
    @ApiOperation(value = "Get User Info from OAuth2",
    notes = "getting user info of logged in user",
    response = Map.class)
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        System.out.println(principal);
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @RequestMapping("/getLoggedInUser")
    @ApiOperation(value = "Get LoggedIn User Info from OAuth2",
            notes = "getting user info of logged in user",
            response = User.class)
    public User getLoggerInUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        User user = new User();
        user.setId(principal.getAttribute("id"));
        user.setName(principal.getAttribute("name"));
        user.setMail(principal.getAttribute("email"));
        userRepository.save(user); //id will be overridden by DB
        return user;
    }

}
