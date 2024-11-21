package com.example.m03_bounce;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.TestCase;

import java.util.List;

public class DBClassTest extends TestCase {

    private DBClass dbTest1;
    private DBClass dbTest2;
    private DBClass dbTest3;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Initialize the DBClass with a mock context (use an in-memory DB for testing)
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dbTest1 = new DBClass(appContext);  // Adjust the context for testing purposes
        dbTest1.clearAllBalls();
        dbTest2 = new DBClass(appContext);  // Adjust the context for testing purposes
        dbTest2.clearAllBalls();
        dbTest3 = new DBClass(appContext);  // Adjust the context for testing purposes
        dbTest3.clearAllBalls();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        dbTest1.clearAllBalls();
        dbTest1.close(); // Close DB after tests
        dbTest2.clearAllBalls();
        dbTest2.close(); // Close DB after tests
        dbTest3.clearAllBalls();
        dbTest3.close(); // Close DB after tests
    }

    public void testOnCreate() {
    }

    public void testOnUpgrade() {
    }

    public void testCount() {
    }

    // Test to see if balls are being added
    public void testSave_01() {

        DataModel dataModel = new DataModel(1, 10.0f, 10.0f, 1.0f, 1.0f);
        int result = dbTest1.save(dataModel);

        // Check that the ball is saved by querying the DB and verifying the result.
        List<DataModel> balls = dbTest1.findAll();
        Log.v("DBClassTest", "SaveTest01 - Ball added: " + dataModel.getModelX() + " " +
                dataModel.getModelY() + " " +dataModel.getModelDX() + " " + dataModel.getModelDY());

        assertTrue( balls.size() > 0);
    }

    public void testSave_02() {
        DataModel dataModel = new DataModel(1, 22.0f, 22.0f, 2.0f, 2.0f);
        int result = dbTest2.save(dataModel);

        // Check that the ball is saved by querying the DB and verifying the result.
        List<DataModel> balls = dbTest2.findAll();
        Log.v("DBClassTest", "SaveTest02 - Ball added: " + dataModel.getModelX() + " " +
                dataModel.getModelY() + " " +dataModel.getModelDX() + " " + dataModel.getModelDY());
        assertTrue( balls.size() > 0);
    }

    public void testSave_03() {
        DataModel dataModel = new DataModel(1, 33.0f, 33.0f, 3.0f, 3.0f);
        int result = dbTest3.save(dataModel);

        // Check that the ball is saved by querying the DB and verifying the result.
        List<DataModel> balls = dbTest3.findAll();
        Log.v("DBClassTest", "SaveTest03 - Ball added: " + dataModel.getModelX() + " " +
                dataModel.getModelY() + " " +dataModel.getModelDX() + " " + dataModel.getModelDY());
        assertTrue( balls.size() > 0);
    }

    public void testUpdate() {
    }


    public void testFindAll_01() {
        // Save a few balls
        dbTest1.save(new DataModel(1, 10.0f, 20.0f, 1.0f, 1.0f));
        dbTest1.save(new DataModel(2, 30.0f, 40.0f, -1.0f, -1.0f));

        // Retrieve all balls
        List<DataModel> balls = dbTest1.findAll();
        assertEquals("There should be 2 balls", 2, balls.size());

        Log.v("DBClassTest", "testFindAll() passed - Balls in DB: " + balls.size());
    }

    public void testGetNameById() {
    }

    //Test to see if balls are being cleared
    public void testClearAllBalls_01() {
        //Add some balls to the db
        dbTest1.save(new DataModel(1, 10.0f, 20.0f, 1.0f, 1.0f));
        dbTest1.save(new DataModel(2, 30.0f, 40.0f, -1.0f, -1.0f));

        //Clear em
        dbTest1.clearAllBalls();

        //Verify that the database is now empty
        List<DataModel> ballsAfterClear = dbTest1.findAll();
        assertEquals("There should be no balls after clearing", 0, ballsAfterClear.size());


        Log.v("DBClassTest", "testClearAllBalls_1() passed - Balls left in DB: " + ballsAfterClear.size());
    }
}