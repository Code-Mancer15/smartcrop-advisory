package com.smartcrop.config;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

import java.sql.Types;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        registerColumnTypes(Types.INTEGER, "integer");
        registerColumnTypes(Types.VARCHAR, "text");
    }

    private void registerColumnTypes(int integer, String string) {
        throw new UnsupportedOperationException("Unimplemented method 'registerColumnTypes'");
    }

    @Override
    public IdentityColumnSupportImpl getIdentityColumnSupport() {
        return new IdentityColumnSupportImpl();
    }
}