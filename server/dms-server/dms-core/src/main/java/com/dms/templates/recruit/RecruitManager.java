package com.dms.templates.recruit;

import java.sql.SQLException;
import java.util.Calendar;

import com.dms.account_manager.UserManager;
import com.dms.crypto.AES256;
import com.dms.utilities.database.DataBase;
import com.dms.utilities.database.SafeResultSet;

import io.vertx.ext.web.RoutingContext;

/**
 * Created by boxfox on 2017-05-30.
 */
public class RecruitManager {
    private UserManager userManager;

    public RecruitManager() {
        userManager = new UserManager();
    }

    public RecruitManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public boolean isApply(RoutingContext ctx) {
        boolean result = false;
        try {
            if (DataBase.getInstance().executeQuery("select count(*) from recruit where uid='", UserManager.getUid(UserManager.getIdFromSession(ctx)), "'").nextAndReturn().getInt(1) > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean canApply(RoutingContext ctx) {
        boolean result = false;
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        if ((month == 5 && day > 29) || (month == 6 && day <= 2)) {
            try {
                SafeResultSet rs = DataBase.getInstance().executeQuery("select number from student_data where uid='", UserManager.getUid(UserManager.getIdFromSession(ctx)), "'");
                if (rs.next() && !AES256.decrypt(rs.getString(1)).startsWith("3")) {
                    result = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
