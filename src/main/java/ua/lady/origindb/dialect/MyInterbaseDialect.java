package ua.lady.origindb.dialect;

import org.hibernate.dialect.InterbaseDialect;
import org.hibernate.dialect.pagination.AbstractLimitHandler;
import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.engine.spi.RowSelection;

public class MyInterbaseDialect extends InterbaseDialect {
    private static final LimitHandler LIMIT_HANDLER = new AbstractLimitHandler() {
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
            return zeroBasedFirstResult + 1;
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
