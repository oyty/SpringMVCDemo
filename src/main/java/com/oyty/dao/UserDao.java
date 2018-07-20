package com.oyty.dao;

import com.oyty.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oyty on 2018/7/20
 * 通过注解定一个DAO
 */
@Repository
public class UserDao {

    private static final String SQL_MATCH_COUNT = "select count(*) from t_user " +
            "where user_name=? and password=?";
    private static final String SQL_USER_BY_NAME = "select * from t_user where " +
            "user_name=?";
    private static final String SQL_UPDATE_LOGIN_INFO = "update t_user set " +
            "last_visit=?, last_ip=?, credits=? where user_id=?";

    private JdbcTemplate jdbcTemplate;

    /**
     * 通过注解实现属性注入
     * @param jdbcTemplate
     */
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getMatchCount(String userName, String password) {
        return jdbcTemplate.queryForObject(SQL_MATCH_COUNT, Integer.class, userName, password);
    }

    public User findUserByUserName(final String userName) {
        final User user = new User();
        jdbcTemplate.query(SQL_USER_BY_NAME, new Object[]{userName}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(userName);
                user.setCredits(resultSet.getInt("credits"));
            }
        });
        return user;
    }

    public void updateLoginInfo(User user) {
        jdbcTemplate.update(SQL_UPDATE_LOGIN_INFO, user.getLastVisit(),
                user.getLastIP(), user.getCredits(), user.getUserId());
    }


}
