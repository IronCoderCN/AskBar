package com.nowcoder.model;

import org.springframework.stereotype.Component;

/**
 * @description:    登录用户holder
 * @author:         ICEMAN
 * @date:           2018\7\22 0022  17:00
 * @version:        1.0
 */

@Component
public class HostHolder{
    private ThreadLocal<User> users = new ThreadLocal<User>();

    public void addUser(User user) {
        this.users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
