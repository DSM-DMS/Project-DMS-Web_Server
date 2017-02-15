package com.dms.planb.action.insertaction;

import java.sql.SQLException;

import org.boxfox.dms.utilities.actions.ActionRegistration;
import org.boxfox.dms.utilities.actions.Actionable;
import org.boxfox.dms.utilities.json.EasyJsonObject;

@ActionRegistration(command=114)
public class UploadQnaComment implements Actionable {
	@Override
	public EasyJsonObject action(int command, EasyJsonObject requestObject) throws SQLException {
		/**
		 * Q&A comment in Q&A
		 * 
		 * Table Name : qna_comment
		 * 
		 * idx INT(11) PK NN AI
		 * no INT(11) NN
		 * writer VARCHAR(10) NN
		 * comment_date DATETIME NN
		 * content VARCHAR(300) NN
		 * FOREIGN KEY no REFERENCES qna(no)
		 * ON DELETE/UPDATE CASCADE
		 * 
		 * DATETIME format : YYYY-MM-DD hh:mm:ss
		 * 
		 * Upload comment based qna no
		 */
		int no = requestObject.getInt("no");
		String content = requestObject.getString("content");
		String writer = requestObject.getString("writer");
		
		int status = database.executeUpdate("INSERT INTO qna_comment(no, writer, comment_date, content) VALUES(", no, ", '", writer, "', now(), '", content, "')");
		
		responseObject.put("status", status);
		
		return responseObject;
	}
}
