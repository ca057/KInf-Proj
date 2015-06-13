package de.uniba.kinf.projm.hylleblomst.logic;

import java.sql.SQLException;

public class main {
	public static void main(String[] args) {
		QueriesImpl test = new QueriesImpl();
		try {
			test.fullTextSearch("Johannes");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
