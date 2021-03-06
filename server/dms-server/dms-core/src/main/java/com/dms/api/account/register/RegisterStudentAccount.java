package com.dms.api.account.register;

import java.sql.SQLException;

import com.dms.account_manager.Guardian;
import com.dms.account_manager.UserManager;
import com.dms.secure.SecureManager;
import com.dms.utilities.log.Log;
import com.dms.utilities.routing.Route;
import com.dms.utilities.support.JobResult;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(path = "/account/register/student", method = {HttpMethod.POST})
public class RegisterStudentAccount implements Handler<RoutingContext> {
    private SecureManager secureManager;
    private SecureManager registerRequestSecureManager;

    public RegisterStudentAccount() {
        secureManager = SecureManager.create(this.getClass());
        registerRequestSecureManager = SecureManager.create("RegisterRequestSecureManager", 5,10);
    }

    @Override
    public void handle(RoutingContext ctx) {
        String uid = ctx.request().getFormAttribute("uid");
        String id = ctx.request().getFormAttribute("id");
        String password = ctx.request().getFormAttribute("password");
        
        try {
            if (Guardian.checkParameters(uid, id, password) && uid.length() > 0 && id.length() > 0 && password.length() > 0) {
                JobResult result = UserManager.register(uid, id, password);
                if (result.isSuccess()) {
                    ctx.response().setStatusCode(201);
                    ctx.response().setStatusMessage(result.getMessage()).end();
                } else {
                    // Conflict
                    ctx.response().setStatusCode(204);
                    ctx.response().setStatusMessage(result.getMessage()).end();
                    registerRequestSecureManager.invalidRequest(ctx);
                }
            } else {
                // Any null in parameters
                ctx.response().setStatusCode(400).end();
                ctx.response().close();
                secureManager.invalidRequest(ctx);
            }
        } catch (SQLException e) {
            ctx.response().setStatusCode(500).end();
            ctx.response().close();
            secureManager.invalidRequest(ctx);
            Log.l("SQLException");
        }
        Log.l("Register Request (", id, ", ", ctx.request().remoteAddress(), ") status : " + ctx.response().getStatusCode());
    }

}
