package com.dms.api.post.notice;

import java.sql.SQLException;

import com.dms.account_manager.Guardian;
import com.dms.utilities.database.DataBase;
import com.dms.utilities.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(path="/post/notice", method={HttpMethod.POST})
public class UploadNotice implements Handler<RoutingContext> {
	public UploadNotice() {
		
	}
	
	@Override
	public void handle(RoutingContext ctx) {

		if (!Guardian.isAdmin(ctx)) {
			ctx.response().setStatusCode(400).end();
			ctx.response().close();
			return;
		}

		DataBase database = DataBase.getInstance();
		
		String title = ctx.request().getParam("title");
		String content = ctx.request().getParam("content");
		
		if(!Guardian.checkParameters(title, content)) {
            ctx.response().setStatusCode(400).end();
            ctx.response().close();
        	return;
        }
		
		try {
			database.executeUpdate("INSERT INTO notice(title, content) VALUES('", title, "', '", content, "')");
			
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} catch(SQLException e) {
			ctx.response().setStatusCode(500).end();
			ctx.response().close();
		}
	}
}
