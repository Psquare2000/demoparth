package com.example.Tuesday;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> findAllUsers() {
        return jdbcTemplate.queryForList("SELECT * FROM users");
    }
}