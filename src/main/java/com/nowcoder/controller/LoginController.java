package com.nowcoder.controller;

import com.nowcoder.service.UserService;
import com.nowcoder.util.ToutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 登录Controller
 * @author: ICEMAN
 * @date: 2018\7\15 0015  9:45
 * @version: 1.0
 */
@Controller
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    String SELECTFILED = "id, user_id, ticket, expired, status";

    /**
     * @description: 注册入口
     * @return: 注册结果
     */
    @RequestMapping(path = {"/reg"}, method = RequestMethod.GET)
    @ResponseBody
    public String regist(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam(value = "remeberme", defaultValue = "0") int remeber) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            SELECTFILED =SELECTFILED+"s";

            map = userService.register(username, password);
            if (map.isEmpty()) {
                return ToutiaoUtil.getJson(0, "注册成功");
            } else {
                return ToutiaoUtil.getJson(1, map);
            }
        } catch (Exception e) {
            logger.error("注册用户失败： " + e);
            return ToutiaoUtil.getJson(1, "注册异常");
        }
    }

    @RequestMapping(path = "/login", method = {RequestMethod.GET})
    @ResponseBody
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "remeberme",defaultValue = "0") int remeberme,
                        HttpServletResponse response) {
        Map<String,Object> map = new HashMap<String, Object>();
        try{
            map = userService.login(username,password);
            if (map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (remeberme > 0) {
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                map.put("ticket",map.get("ticket"));
                return ToutiaoUtil.getJson(0,map.get("ticket").toString());
            }else {
                return ToutiaoUtil.getJson(1,map);
            }
        } catch (Exception e){
            logger.error("登录异常:"+e);
            return ToutiaoUtil.getJson(1,"登录异常");
        }
    }

    @RequestMapping(path = {"/logout"},method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }
}
