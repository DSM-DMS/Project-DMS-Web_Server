package com.dms.parser.dataio.plan;

import java.sql.SQLException;

import com.dms.parser.dataio.Query;
import com.dms.parser.datamodel.plan.MonthPlan;
import com.dms.utilities.database.DataBase;
import com.dms.utilities.database.DataSaveable;
import com.dms.utilities.database.QueryUtils;
import com.dms.utilities.database.SafeResultSet;

public class PlanModel {
	public static DataSaveable getPlan(int year, int month) {
		try {
			SafeResultSet rs = DataBase.getInstance().executeQuery(QueryUtils.querySetter(Query.PLAN.selectFormat, year, month));
			if (rs.next()) {
				MonthPlan plan = (MonthPlan) new MonthPlan().fromResultSet(rs);
				if (plan.isVaild())
					return plan;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new PlanParser(year,month).parse();
	}

}
