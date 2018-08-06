package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.util.ToutiaoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;
import sun.security.krb5.internal.Ticket;
import sun.security.provider.MD5;

import java.util.*;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    /**
     * @description: 注册用户
     * @param: username 用户名，password 密码
     * @return: 注册结果
     */
    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isBlank(username)) {
            map.put("error", "用户名不能为空！");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("error", "密码不能为空！");
            return map;
        }

        if (userDAO.selectByName(username) != null) {
            map.put("error", "该用户名已被注册！");
            return map;
        }
        User user = new User();
        user.setName(username);
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setSalt(UUID.randomUUID().toString().substring(0, 8));
        user.setPassword(ToutiaoUtil.MD5(password + user.getSalt()));
        userDAO.addUser(user);
        return map;
    }

    /**
     * @description: 登录
     * @param: username 用户名，password 密码
     * @return: 登录结果
     */
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isBlank(username)) {
            map.put("error", "用户名不能为空！");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("error", "密码不能为空！");
            return map;
        }

        if (userDAO.selectByName(username) == null) {
            map.put("error", "该用户名不存在！");
            return map;
        }

        User user = userDAO.selectByName(username);
        if (!ToutiaoUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("error", "密码错误！");
            return map;
        }

        LoginTicket loginTicket = new LoginTicket();
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24);
        loginTicket.setExpired(date);
        loginTicket.setStatus(2);
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-", ""));
        loginTicketDAO.addTicket(loginTicket);
        map.put("ticket", loginTicket.getTicket());
        return map;
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket,0);
    }
}
