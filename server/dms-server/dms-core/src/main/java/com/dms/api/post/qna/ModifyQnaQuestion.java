package com.dms.api.post.qna;

import java.sql.SQLException;

import com.dms.account_manager.Guardian;
import com.dms.account_manager.UserManager;
import com.dms.utilities.database.DataBase;
import com.dms.utilities.database.SafeResultSet;
import com.dms.utilities.log.Log;
import com.dms.utilities.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(path="/post/qna/question", method={HttpMethod.PATCH})
public class ModifyQnaQuestion implements Handler<RoutingContext> {
	private UserManager userManager;
	
	public ModifyQnaQuestion() {
		userManager = new UserManager();
	}
	
	@Override
	public void handle(RoutingContext context) {
		DataBase database = DataBase.getInstance();

		int no = Integer.parseInt(context.request().getParam("no"));
		String title = context.request().getParam("title");
		String content = context.request().getParam("content");
		
		if(!Guardian.checkParameters(no, title, content)) {
            context.response().setStatusCode(400).end();
            context.response().close();
        	return;
        }
		
		String uid = null;
		try {
			uid = userManager.getUid(userManager.getIdFromSession(context));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			SafeResultSet rs = database.executeQuery("SELECT * FROM qna WHERE no=", no);
			if(uid == null || rs.getString("owner") != uid) {
				// 권한 없는 경우
				context.response().setStatusCode(400).end();
				context.response().close();
				return;
			}
			database.executeUpdate("UPDATE qna SET title='", title, "' WHERE no=", no);
			database.executeUpdate("UPDATE qna SET question_content='", content, "' WHERE no=", no);
			
			context.response().setStatusCode(200).end();
			context.response().close();
		} catch(SQLException e) {
			context.response().setStatusCode(500).end();
			context.response().close();
			
			Log.l("SQLException");
		}
	}
}
