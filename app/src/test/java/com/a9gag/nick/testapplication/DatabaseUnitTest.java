package com.a9gag.nick.testapplication;

import android.test.suitebuilder.annotation.LargeTest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.concurrent.TimeUnit;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 *  @Config The annotation can be applied to classes and methods
 *   This can be useful if you have a custom build system
 */
@LargeTest //Large: Accesses external file systems, networks, etc.
public class DatabaseUnitTest {

  /*  private SHLoggingDB mDataSource;
    private static final String COMMA = ",";

    @Before
    public void setUp() {
        mDataSource = SHLoggingDB.getInstance(RuntimeEnvironment.application);
        mDataSource.openWrite();
        System.out.println("setUp DB");
    }

    @After
    public void tearDown() {
        mDataSource.forceDeleteAllRecords();
        mDataSource.close();
        System.out.println("tearDown DB");
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mDataSource);
    }

    @Test
    public void testDeleteAll() {
        // Given
        int queryCount = 0;
        System.out.println("Test deleting all DB records");

        // When
        mDataSource.forceDeleteAllRecords();
        queryCount = mDataSource.getLogDBCount();
        System.out.println("Database count is " + queryCount);

        // Then
        Assert.assertThat(queryCount, is(0));
    }

    @Test
    public void testDbInsertion() throws JSONException {
        // Given
        StringBuilder result = new StringBuilder("{\"datatype\":\"testingdata1\"}");
        result.append(COMMA);
        result.append("{\"datatype\":\"testingdata2\"}");
        result.append(COMMA);
        StringBuilder answer = new StringBuilder();
        JSONObject testStr1 = new JSONObject("{\"datatype\":\"testingdata1\"}");
        JSONObject testStr2 = new JSONObject("{\"datatype\":\"testingdata2\"}");

        // When
        mDataSource.saveLoglineToDatabase(testStr1);
        mDataSource.saveLoglineToDatabase(testStr2);
        mDataSource.getLogsInString(answer);
        // Then
        System.out.println(answer.toString());
        System.out.println(result.toString());
        assertEquals(answer.toString(), result.toString());
    }

    @Test
    public void testDbCounter() throws JSONException {
        // Given
        JSONObject testStr1 = new JSONObject("{\"datatype\":\"testingdata1\"}");
        JSONObject testStr2 = new JSONObject("{\"datatype\":\"testingdata2\"}");
        int queryCount = 0;

        // When
        mDataSource.saveLoglineToDatabase(testStr1);
        mDataSource.saveLoglineToDatabase(testStr2);
        queryCount = mDataSource.getLogDBCount();
        // Then
        System.out.println(testStr1);
        System.out.println(testStr2);
        assertThat(queryCount, is(2));
        System.out.println("Database count is " + queryCount);
    }

    @Test
    public void testAddAndDelete() throws JSONException {
        // Given
        JSONObject testStr1 = new JSONObject("{\"AUD\":\"1.2\"}");
        JSONObject testStr2 = new JSONObject("{\"JPY\":\"1.993\"}");
        JSONObject testStr3 = new JSONObject("{\"BGN\":\"1.66\"}");
        int querySize = 0;

        // When
        mDataSource.forceDeleteAllRecords();
        mDataSource.saveLoglineToDatabase(testStr1);
        mDataSource.saveLoglineToDatabase(testStr2);
        mDataSource.saveLoglineToDatabase(testStr3);
        querySize = mDataSource.getLogDBCount();

        // Then
        assertThat(querySize, is(3));
        System.out.println("Database count is " + querySize);
        // When
        mDataSource.forceDeleteAllRecords();

        // Then
        querySize = mDataSource.getLogDBCount();
        assertThat(querySize, is(0));
        System.out.println("Database count is " + querySize);
    }

    @Test
    public void testMultiThreadAdding() throws Throwable {
        //Given
        final JSONObject insertStr1 = new JSONObject("{\"datatype\":\"testingdata1\"}");
        final JSONObject insertStr2 = new JSONObject("{\"datatype\":\"testingdata2\"}");
        final JSONObject insertStr3 = new JSONObject("{\"datatype\":\"testingdata3\"}");
        final Waiter waiter = new Waiter();

        new Thread(new Runnable() {
            @Override
            public void run() {
                SHLoggingDB database1 = SHLoggingDB.getInstance(RuntimeEnvironment.application);
                database1.openWrite();
                database1.saveLoglineToDatabase(insertStr1);
                database1.close();
                SHLoggingDB logLinedb = SHLoggingDB.getInstance(RuntimeEnvironment.application);
                logLinedb.openRead();
                waiter.assertNotNull(logLinedb.getLogDBCount());
                StringBuilder recieveStr1 = new StringBuilder();
                logLinedb.getLogsInString(recieveStr1);
                System.out.println("Database1 received " + recieveStr1.toString());
                logLinedb.forceDeleteAllRecords();
                System.out.println("Database1 count " + logLinedb.getLogDBCount());
                logLinedb.close();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                SHLoggingDB database1 = SHLoggingDB.getInstance(RuntimeEnvironment.application);
                database1.openWrite();
                database1.saveLoglineToDatabase(insertStr2);
                database1.close();
                SHLoggingDB logLinedb = SHLoggingDB.getInstance(RuntimeEnvironment.application);
                logLinedb.openRead();
                waiter.assertNotNull(logLinedb.getLogDBCount());
                StringBuilder recieveStr2 = new StringBuilder();
                logLinedb.getLogsInString(recieveStr2);
                System.out.println("Database1 received " + recieveStr2.toString());
                logLinedb.forceDeleteAllRecords();
                System.out.println("Database2 count " + logLinedb.getLogDBCount());
                logLinedb.close();
            }
        }).start();

        Thread.sleep(1000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SHLoggingDB database1 = SHLoggingDB.getInstance(RuntimeEnvironment.application);
                database1.openWrite();
                database1.saveLoglineToDatabase(insertStr3);
                database1.close();
                SHLoggingDB logLinedb = SHLoggingDB.getInstance(RuntimeEnvironment.application);
                logLinedb.openRead();
                waiter.assertNotNull(logLinedb.getLogDBCount());
                StringBuilder recieveStr2 = new StringBuilder();
                logLinedb.getLogsInString(recieveStr2);
                System.out.println("Database3 received " + recieveStr2.toString());
                logLinedb.forceDeleteAllRecords();
                System.out.println("Database3 count " + logLinedb.getLogDBCount());
                waiter.assertEquals(logLinedb.getLogDBCount(), 0);
                logLinedb.close();
                waiter.resume();
            }
        }).start();
        waiter.await(7, TimeUnit.SECONDS);
    }*/
}
