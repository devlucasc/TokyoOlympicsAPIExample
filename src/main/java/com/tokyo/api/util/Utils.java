package com.tokyo.api.util;

import java.util.Calendar;
import java.util.Date;

public class Utils {

    /**
     * check if time ranges overlaps
     * @param start1 first range start
     * @param end1 first range end
     * @param start2 second range start
     * @param end2 second range end
     * @return true if ranges overlaps or false if else
     */
    public static boolean dateOverlaps(Date start1, Date end1, Date start2, Date end2){
        return ((start1.before(start2) && end1.after(start2))
                || (start1.before(end2) && end1.after(end2))
                || (start1.before(start2) && end1.after(end2))
                || (start1.equals(start2) && end1.equals(end2)));
    }

    /**
     * Remove time information from datetime
     * @param date Date that will be truncated in day
     * @return date truncated at a day
     */
    public static Date dateTrunc(Date date) {
        // remove all time information from date, this will be used to check if day is equals
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
