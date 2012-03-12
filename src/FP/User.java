/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FP;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author lycantrophe
 */
public class User {

    private Person me;

    /**
     * Constructor
     *
     * @param myPerson Person object to bind User session to
     *
     */
    public User(Person myPerson) {
        me = myPerson;
    }

    /**
     * Creates and adds an appointment to this User's Person
     *
     * @param start Appointment start time
     *
     * @param end Appointment end time
     *
     * @param description Appointment description
     *
     * @param invited Existing participants. Can be null
     *
     * @param participants Non-existing participants. Can be null
     *
     * @param location Location. Can be null
     */
    public void createAppointment(Date start, Date end, String description, ArrayList<Person> invited, ArrayList<String> participants, Location location) {

        Appointment appointment;
        // TODO: Add restrictions checking, throw error
        if (invited == null) {
            // TODO: Add restrictions to location. Should be in appointment constructor?
            appointment = new AppointmentImpl(me, start, end, description, participants, location);
        } else {
            appointment = new Meeting(me, start, end, description, invited, participants, location);

            // Give this appointment to everyone invited
            for (Person other : invited) {
                other.addAppointment(appointment);
            }
        }
        // TODO: Pass "me" in invited list?
        me.addAppointment(appointment);
    }

    public void deleteAppointment( Appointment appointment ) {
        ArrayList<Person> invited = appointment.getInvited();

        for (Person other : invited) {
            other.removeAppointment(appointment);
        }
        this.me.removeAppointment(appointment);
        /*
         * Delete from database
         */
    }
    
    public void declineAppointment( Appointment appointment ) {
        ArrayList<Person> invited = appointment.getInvited();
        
        for( Person other : invited ){
            other.declined( appointment, me );
        }
        
        // TODO: Status change or hard removal?
        me.removeAppointment( appointment );
        /*
         * Update database
         */
    }
    
    /**
     * Edits an existing appointment and notifies all participants
     * 
     * @param appointment Appointment to change
     * @param newAppointment Appointment object with changes
     */
    
    public void editAppointment( Appointment appointment, Appointment newAppointment ){
        appointment.updateAppointment( newAppointment );
    }
}
