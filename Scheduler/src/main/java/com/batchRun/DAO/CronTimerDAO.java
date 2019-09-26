package com.batchRun.DAO;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.batchRun.Mapper.CronTimerMapper;
import com.batchRun.bean.CronTimer;

public class CronTimerDAO {

	private JdbcTemplate jdbcTemplate;  

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
		this.jdbcTemplate = jdbcTemplate;  
	}  

	public List<CronTimer> cronExpression(){
		String query= "select * from crontimer where crontype = 'spring'";  
		List<CronTimer> expression = jdbcTemplate.query(query, new CronTimerMapper());
		return expression;
	}
}
