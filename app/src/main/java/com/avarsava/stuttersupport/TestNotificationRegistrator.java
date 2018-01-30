package com.avarsava.stuttersupport;

import android.content.Context;
import android.content.Intent;

import static junit.framework.Assert.*;


/**
 * @author  Alexis Varsava <av11sl@brocku.ca>
 * @version 1.1
 * @since   1.1
 *
 * Tests validity of the NotificationRegistrator class.
 * TODO: This relies a lot on Android. Seems better as an integration test rather than a unit test?
 */

public class TestNotificationRegistrator {

    /**
     * NotificationRegistrator with which to test
     */
    NotificationRegistrator nr;

    /**
     * Context to pass into nr's methods, as the Android components it calls wants one
     */
    Context context;

    /**
     * Tag for Logger
     */
    final String TAG = "TEST: NotificationRegistrator";


    /**
     * Before any tests are run, get a usable Context
     */
    @BeforeClass
    public void setUpBeforeAll() {
        //TODO: How to get context in this setting?
        //context =
    }

    /**
     * Test to ensure register() really does register an alarm with Android.
     *
     * TODO: This is nearly identical to testAlarmExistsReportsTrueWhenAlarmExists. Issue?
     */
    @Test
    public void testRegisterSuccessfullyRegistersWithOS(){
        //Create new NotificationRegistrator which will override previous alarm
        nr = new NotificationRegistrator(true);

        //Register a new alarm
        nr.register(context);

        boolean result = nr.alarmExists();

        assertTrue(result);
    }


    /**
     * Test to ensure that shouldRegisterAlarm() returns true when nr is created with
     * overrideService = true
     */
    @Test
    public void testShouldRegisterAlarmReturnsTrueWhenOverrideService(){
        nr = new NotificationRegistrator(true);
        //TODO: shouldRegisterAlarm is private, is that an issue?
        boolean result = nr.shouldRegisterAlarm(context, new Intent());

        assertTrue(result);

    }

    /**
     * Test to ensure that when nr is created with NOT overrideService, and there is no
     * existing alarm, shouldRegisterAlarm() reports true
     */
    @Test
    public void testShouldRegisterAlarmReturnsTrueWhenNotOverrideServiceAndNoExistingAlarm(){
        //Create NotificationRegistrator with NOT overrideService
        nr = new NotificationRegistrator(false);

        //Ensure no alarm exists
        nr.deleteAlarm();

        //get result of shouldRegisterAlarm given these variables
        boolean result = nr.shouldRegisterAlarm(context, new Intent());

        assertTrue(result);

    }

    /**
     * Test to ensure that when nr is created with NOT overrideService, and an alarm already exists,
     * shouldRegisterAlarm() reports false
     */
    @Test
    public void testShouldRegisterAlarmReturnsFalseWhenNotOverrideServiceAndExistingAlarm(){
        //Create NotificationRegistrator with overrideService
        nr = new NotificationRegistrator(true);

        //Ensure alarm exists
        nr.register(context);

        //get result of shouldRegisterAlarm given these variables
        boolean result = nr.shouldRegisterAlarm(context, new Intent());

        assertFalse(result);
    }

    /**
     * Test to ensure that deleteAlarm() does delete an alarm with the Android OS.
     * TODO: Same issue with this and testAlarmExistsReportsFalseWhenAlarmDoesNotExist
     */
    @Test
    public void testDeleteAlarmShouldDeleteAlarm(){
        nr = new NotificationRegistrator(true);

        //delete alarm
        nr.deleteAlarm(context);

        //get result of alarmExists
        boolean result = nr.alarmExists(context);

        assertFalse(result);
    }

    /**
     * Test to ensure that the time set within the alarm matches the time the user has
     * requested in the Preferences.
     */
    @Test
    public void testRegisteredAlarmTimeShouldMatchUserPreferences(){
        //TODO: Find out how to get the time back from the alarm, if possible
    }

    /**
     * Test to ensure that alarmExists() reports true when we know an alarm exists.
     */
    @Test
    public void testAlarmExistsReportsTrueWhenAlarmExists(){
        nr = new NotificationRegistrator(false);

        //Provided we're confident in register()'s ability, this will ensure an alarm exists
        nr.register(context);

        boolean result = nr.alarmExists();

        assertTrue(result);
    }

    /**
     * Test to ensure alarmExists() reports false when we know there is no alarm.
     */
    @Test
    public void testAlarmExistsReportsFalseWhenAlarmDoesNotExist(){
        nr = new NotificationRegistrator(true);

        //Provided we're confident in deleteAlarm()'s ability, this will ensure no alarm exists
        nr.deleteAlarm(context);

        boolean result = nr.alarmExists();

        assertFalse(result);
    }

    /**
     * After each test, let nr go
     */
    @After
    public void tearDownAfterEach(){
        if(nr != null) nr = null;
    }
}
