package com.batchRun.Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.batchRun.bean.HdbStatus;

public class HdbStatusMapper implements RowMapper<HdbStatus>{

	public HdbStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
		HdbStatus hdbStatus = new HdbStatus();
		hdbStatus.setId(rs.getInt("id"));
		hdbStatus.setName(rs.getString("name"));
		hdbStatus.setEmailId(rs.getString("email"));
		hdbStatus.setStatusDate(rs.getDate("statusdate"));
		hdbStatus.setStatus(rs.getString("status"));
		return hdbStatus;
	}

}
