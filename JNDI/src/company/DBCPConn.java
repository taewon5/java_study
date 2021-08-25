package company;
import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.crypto.Data;

public class DBCPConn {
    private static Connection conn;
    private static PoolingDataSource poolingDataSource;
    private DBCPConn() {
    }
    public static Connection getConnection() {
        if (conn==null) {
            try {
                Context init = new InitialContext();
                Context content = (Context)init.lookup("java:/comp/env");
                DataSource pgds = (DataSource)content.lookup("jdbc/DBName");
                conn=pgds.getConnection();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return conn;
    }
    public static void close() {
        if (conn!=null) {
            try{
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        conn = null;
    }
}