package com.dms.planb.action.insertaction;

import java.sql.SQLException;

import org.boxfox.dms.utilities.actions.ActionRegistration;
import org.boxfox.dms.utilities.actions.Actionable;
import org.boxfox.dms.utilities.json.EasyJsonObject;

@ActionRegistration(command=113)
public class UploadQnaAnswer implements Actionable {
	@Override
	public EasyJsonObject action(int command, EasyJsonObject requestObject) throws SQLException {
		/**
		 * Answer in Q&A
		 * 
		 * Reference UPLOAD_QUESTION
		 * Upload answer based question no
		 */
		int no = requestObject.getInt("no");
		String content = requestObject.getString("answer_content");
		
		int status = database.executeUpdate("UPDATE qna SET answer_content='", content, "', answer_date= now()", " WHERE no=", no);
		
		responseObject.put("status", status);
		
		return responseObject;
	}
}
