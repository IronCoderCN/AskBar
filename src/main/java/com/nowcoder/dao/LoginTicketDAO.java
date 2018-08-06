package com.nowcoder.dao;


import com.nowcoder.model.LoginTicket;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @description: LoginTicketDao类
 * @author: ICEMAN
 * @date: 2018\7\22 0022  15:05
 * @version: 1.0
 */
@Mapper
public interface LoginTicketDAO {
    String SELECTFILED = "id, user_id, ticket, expired, status";
    String INSERTFIELD = " user_id, expired, status, ticket ";
    String TABLE_NAME = "login_ticket";


        @Select({"select " , SELECTFILED , "from " , TABLE_NAME , "where ticket= #{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Insert({"insert into " , TABLE_NAME , "(" , INSERTFIELD , ") values (#{userId},#{expired},#{status},#{ticket})"})
    int addTicket(LoginTicket loginTicket);

    @Update({"update ", TABLE_NAME, " set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);


}
