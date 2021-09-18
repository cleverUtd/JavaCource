package db_insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BatchInsertTest {

    public static void main(String[] args) {
        Connection connection = JdbcUtil.getConnection();

        long start = System.currentTimeMillis();
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO t_order (order_seq, user_id) VALUES (?,?)")) {

            for (int i = 0; i < 1000000; i++) {
                ps.setLong(1, i);
                ps.setLong(2, i);
                ps.addBatch();

                if (i % 1000 == 0) {
                    ps.executeBatch();
                }
            }
            System.out.println("costs: " + (System.currentTimeMillis() - start));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.releaseDB(connection, null, null);
        }
    }
}
