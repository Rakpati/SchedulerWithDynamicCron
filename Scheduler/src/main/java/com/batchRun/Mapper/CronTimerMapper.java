package com.batchRun.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.batchRun.bean.CronTimer;

public class CronTimerMapper implements RowMapper<CronTimer>{

	public CronTimer mapRow(ResultSet rs, int rowNum) throws SQLException {
		CronTimer cronTimer = new CronTimer();
		cronTimer.setCronExpression(rs.getString("cronExpression"));
		return cronTimer;
	}

}
