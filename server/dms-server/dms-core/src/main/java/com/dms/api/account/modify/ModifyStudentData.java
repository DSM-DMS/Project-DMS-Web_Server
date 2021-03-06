package com.dms.api.account.modify;

import java.sql.SQLException;

import com.dms.account_manager.Guardian;
import com.dms.account_manager.UserManager;
import com.dms.utilities.database.DataBase;
import com.dms.utilities.log.Log;
import com.dms.utilities.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(path="/account/student", method={HttpMethod.PATCH})
public class ModifyStudentData implements Handler<RoutingContext> {
	private UserManager userManager;

	public ModifyStudentData(){
		userManager = new UserManager();
	}

	@Override
	public void handle(RoutingContext ctx) {

		DataBase database = DataBase.getInstance();
		
		String id = userManager.getIdFromSession(ctx);
        String uid = null;
        try {
            if (id != null) {
                uid = userManager.getUid(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		int number = Integer.parseInt(ctx.request().getParam("number"));
		String name = ctx.request().getParam("name");
		
		if(!Guardian.checkParameters(id, uid, number, name)) {
        	ctx.response().setStatusCode(400).end();
        	ctx.response().close();
        	return;
        }
		
		try {
			database.executeUpdate("UPDATE student_data SET number=", number, " WHERE uid='", uid, "'");
			database.executeUpdate("UPDATE student_data SET name='", name, "' WHERE uid='", uid, "'");
			
			ctx.response().setStatusCode(200).end();
			ctx.response().close();
		} catch(SQLException e) {
			ctx.response().setStatusCode(500).end();
			ctx.response().close();
			
			Log.l("SQLException");
		}
	}
}
