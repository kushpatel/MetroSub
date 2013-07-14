package com.MetroSub.database.dataobjects;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created with IntelliJ IDEA.
 * User: kushpatel
 * Date: 7/14/13
 * Time: 9:28 AM
 * To change this template use File | Settings | File Templates.
 */

// Helper database field so as to store Collection<String> in the database
public class RouteLine {

    @DatabaseField()
    private String mRouteLine;

        /* Accessor and Mutator methods
    ====================================================================================================================*/

    public String getRouteLine() {
        return this.mRouteLine;
    }

    public void setRouteLine(String routeLine) {
        this.mRouteLine = routeLine;
    }
}
