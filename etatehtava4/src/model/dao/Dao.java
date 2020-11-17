package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.sun.jdi.connect.spi.Connection;

public class Dao {
	private Connection con = null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep = null;
	private String sql;
	private String db = "Myynti.sqlite";
}
