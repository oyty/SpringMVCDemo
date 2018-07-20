package com.oyty.dao;

import com.oyty.domain.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by oyty on 2018/7/20
 */
@Repository
public class LoginLogDao {

    private static final String SQL_INSERT_LOGIN_LOG = "insert into t_login_log(user_id, ip, login_datetime) " +
            "values(?,?,?)";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertLoginLog(LoginLog loginLog) {
        jdbcTemplate.update(SQL_INSERT_LOGIN_LOG, loginLog.getUserId(), loginLog.getIp(), loginLog.getLoginDate());
    }
}
