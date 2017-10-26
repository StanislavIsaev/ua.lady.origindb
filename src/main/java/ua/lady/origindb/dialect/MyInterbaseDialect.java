package ua.lady.origindb.dialect;

import org.hibernate.dialect.InterbaseDialect;
import org.hibernate.dialect.pagination.AbstractLimitHandler;
import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.dialect.pagination.LimitHelper;
import org.hibernate.engine.spi.RowSelection;

public class MyInterbaseDialect extends InterbaseDialect {
    private static LimitHandler LIMIT_HANDLER = new AbstractLimitHandler() {
        private boolean flag = true;
        @Override
        public String processSql(String sql, RowSelection selection) {
            return sql + " rows ? to ?";
        }

        @Override
        public boolean supportsLimit() {
            return true;
        }

        @Override
        public boolean forceLimitUsage() {
            return true;
        }

        @Override
        public int convertToFirstRowValue(int zeroBasedFirstResult) {
            return zeroBasedFirstResult+((flag = !flag)? 0: 1);
        }

        @Override
        public boolean useMaxForLimit() {
            return true;
        }
    };
    @Override
    public LimitHandler getLimitHandler() {
        return LIMIT_HANDLER;
    }
}
