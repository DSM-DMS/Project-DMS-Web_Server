package com.dms.api.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.boxfox.dms.util.AdminManager;
import org.boxfox.dms.util.UserManager;

import com.dms.utilities.database.DB;
import com.dms.utilities.log.Log;
import com.dms.utilities.routing.RouteRegistration;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@RouteRegistration(path = "/account/initialize", method = { HttpMethod.POST })
public class InitializeAccount implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		if (!AdminManager.isAdmin(ctx)) {
			ctx.response().setStatusCode(400).end();
			ctx.response().close();
			return;
		}

		String number = ctx.request().getParam("number");
		String encryptedNumber = UserManager.getAES().encrypt(number);

		try {
			ResultSet rs = DB.executeQuery("SELECT uid FROM student_data WHERE number=?", encryptedNumber);
			if(rs.next()) {
				String uid = rs.getString("uid");
				DB.executeUpdate("UPDATE account SET id=null, password=null, session_key=null WHERE uid=?", uid);
				
				ctx.response().setStatusCode(200).end();
				ctx.response().close();
			} else {
				ctx.response().setStatusCode(204).end();
				ctx.response().close();
			}
		} catch (SQLException e) {
			ctx.response().setStatusCode(500).end();
			ctx.response().close();

			Log.l("SQLException");
		}
	}
}