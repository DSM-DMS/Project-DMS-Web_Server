package com.dms.planb.action;

import org.boxfox.dms.utilities.actions.Actionable;
import java.sql.SQLException;

import org.boxfox.dms.utilities.database.DataBase;
import org.boxfox.dms.utilities.json.EasyJsonObject;

import com.dms.planb.support.Commands;

public class DeleteAction implements Actionable {
	@Override
	public EasyJsonObject action(int command, EasyJsonObject requestObject) throws SQLException {
		EasyJsonObject responseObject = new EasyJsonObject();
		
		DataBase database = DataBase.getInstance();
		
		// For account
		String id = null;
		
		// For post
		int no;
		String date;
		
		// For status
		int status = 0;
		
		switch(command) {
		case Commands.DELETE_ACCOUNT:
			id = requestObject.getString("id");
			
			status = database.executeUpdate("DELETE FROM account WHERE id=", id);
			
			break;
		case Commands.DELETE_RULE:
			no = requestObject.getInt("no");
			
			status = database.executeUpdate("DELETE FROM rule WHERE no=", no);
			
			break;
		case Commands.DELETE_QUESTION:
			no = requestObject.getInt("no");
			
			status = database.executeUpdate("DELETE FROM qna WHERE no=", no);
			
			break;
		case Commands.DELETE_ANSWER:
			no = requestObject.getInt("no");
			
			status = database.executeUpdate("UPDATE qna SET answer_content=NULL, answer_date=NULL WHERE no=", no);
			
			break;
		case Commands.DELETE_QNA_COMMENT:
			no = requestObject.getInt("no");
			date = requestObject.getString("date");
			
			status = database.executeUpdate("DELETE qna_comment WHERE no=", no, " AND date='", date, "'");
			
			break;
		case Commands.DELETE_FAQ:
			no = requestObject.getInt("no");
			
			status = database.executeUpdate("DELETE faq WHERE no=", no);
			
			break;
		case Commands.DELETE_REPORT_FACILITY:
			no = requestObject.getInt("no");
			
			status = database.executeUpdate("DELETE facility_report WHERE no=", no);
			
			break;
		case Commands.DEAPPLY_EXTENTION:
			id = requestObject.getString("id");
			
			status = database.executeUpdate("DELETE extension_apply WHERE id='", id, "'");
			
			break;
		case Commands.DEAPPLY_GOINGOUT:
			id = requestObject.getString("id");
			
			status = database.executeUpdate("DELETE goingout_apply WHERE id='", id, "'");
			
			break;
		case Commands.DEAPPLY_MERIT:
			id = requestObject.getString("id");
			
			status = database.executeUpdate("DELETE merit_apply WHERE id='", id, "'");
			
			break;
		}
		
		responseObject.put("status", status);
		
		return responseObject;
	}
}
