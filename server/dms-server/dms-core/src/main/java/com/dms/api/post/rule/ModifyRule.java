package com.dms.api.post.rule;

import java.sql.SQLException;

import com.dms.account_manager.Guardian;
import com.dms.utilities.database.DataBase;
import com.dms.utilities.log.Log;
import com.dms.utilities.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(path="/post/rule", method={HttpMethod.PATCH})
public class ModifyRule implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext context) {

		if (!Guardian.isAdmin(context)) {
			context.response().setStatusCode(400).end();
			context.response().close();
			return;
		}
		
		DataBase database = DataBase.getInstance();
		
		int no = Integer.parseInt(context.request().getParam("no"));
		String title = context.request().getParam("title");
		String content = context.request().getParam("content");
		
		if(!Guardian.checkParameters(no, title, content)) {
            context.response().setStatusCode(400).end();
            context.response().close();
        	return;
        }
		
		try {
			database.executeUpdate("UPDATE rule SET title='", title, "' WHERE no=", no);
			database.executeUpdate("UPDATE rule SET content='", content, "' WHERE no=", no);
			
			context.response().setStatusCode(200).end();
			context.response().close();
		} catch(SQLException e) {
			context.response().setStatusCode(500).end();
			context.response().close();
			
			Log.l("SQLException");
		}
	}
}
