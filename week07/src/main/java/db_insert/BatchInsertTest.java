package db_insert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;

public class BatchInsertTest {

    private static void singleThread() {
        long start = System.currentTimeMillis();
        bulkInsert(1, 1);
        System.out.println("costs: " + (System.currentTimeMillis() - start));
    }

    private static void multiThread() throws InterruptedException {
        long start = System.currentTimeMillis();

        CountDownLatch cdt = new CountDownLatch(2);
        Thread run1 = new Thread(() -> {
            bulkInsert(1, 2);
            cdt.countDown();
        });
        Thread run2 = new Thread(() -> {
            bulkInsert(0, 2);
            cdt.countDown();
        });


        run1.start();
        run2.start();

        cdt.await();
        System.out.println("costs: " + (System.currentTimeMillis() - start));

    }

    private static void bulkInsert(int s, int step) {
        Connection connection = JdbcUtil.getConnection();
        PreparedStatement ps = null;

        try  {
            ps = connection.prepareStatement("INSERT INTO t_order (order_seq, user_id) VALUES (?,?)");
            connection.setAutoCommit(false);
            for (int i = s; i < 1000000; i+=step) {
                ps.setLong(1, i);
                ps.setLong(2, i);
                ps.addBatch();

                if (i % 3000 == 0) {
                    ps.executeBatch();
                }
            }
            ps.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.releaseDB(connection, ps, null);
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        singleThread();
        multiThread();
    }
}
