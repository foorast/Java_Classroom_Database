/*
 * Listens for events on the input form. 
 * Implements the ActionListener interface which contains a single method, 
 * "actionPerformed" - this method contains all the logic to process the data
 * on the form, as well as several other events
 */
package controllers;

import datacontainers.ClassroomDC;
import datamodels.Classroom;
import exceptionhandlers.ErrorPopup;
import exceptionhandlers.InvalidDataException;
import exceptionhandlers.MissingDataException;
import utilities.ConsoleLogger;
import view.inputforms.ClassroomInputForm;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.logging.Level;


public class InputClassroomFormController implements ActionListener {

    // The Classroom data model is passed in via the constructor
    private ClassroomDC m_classroomDataContainer;

    // The form is created here and this constructor object gets passed to it
    private ClassroomInputForm form;

    // Constructor 
    public InputClassroomFormController(ClassroomDC p_classroomDataContainer) {

        // Store the passed in data model
        this.m_classroomDataContainer = p_classroomDataContainer;

        // create the form
        form = new ClassroomInputForm(this);

        // make the form visible
        this.form.setVisible(true);
    }

    /**
     * Implements actionPerformed method of the ActionListener interface
     */
    public void actionPerformed(java.awt.event.ActionEvent event) {

        if (event.getActionCommand().equals("Save")) {
            this.saveData();
        } else if (event.getActionCommand().equals("Clear")) {
            this.clearForm();
        } else if (event.getActionCommand().equals("Close")) {
            this.closeForm();
        }
    }

    /**
     * Private save method If an error is thrown, handle it by creating an error
     * popup and don't save the classroom
     */
    private void saveData() {
       
        try {

            // Create a new classroom object
            Classroom aClassroom = new Classroom();

            // Retrieve the room number from the form
            String roomNumber = form.getRoomNumberTextfield().getText();

            if (isEmpty(roomNumber)) {
                throw new InvalidDataException("Missing Data!");
            }

            if (!isValid(roomNumber)) {
                throw new InvalidDataException("Invalid Data!");
            }

            // If the room number is valid, store it in the classroom object
            aClassroom.setRoomNumber(roomNumber);

            // Finish processing the rest of the data from the form        
            // Retrieve the data model associated with the drop down list 
            ComboBoxModel dataModel = this.form.getRoomTypeCombobox().getModel();
            // Retrieve the selected item from the data model, notice
            // it is stored as type Object, we need to convert it in the next step
            Object selectedItem = dataModel.getSelectedItem();
            // Convert (Cast) the selected item to a String
            String roomType = (String) selectedItem;
            // Store the room type
            aClassroom.setTypeOfRoom(roomType);

            // set capacity
            aClassroom.setCapacity(form.getRoomCapacityTextField().getText());

            // Add the new classroom to the list in ClassroomDataModel
            m_classroomDataContainer.getListOfClassrooms().add(aClassroom);

            ConsoleLogger.log(Level.INFO,"Classroom Data Saved");

        } catch (InvalidDataException | MissingDataException exception) {
            // Invalid data was found, 
            // No classroom will be saved,
            // create an error popup
            ConsoleLogger.log(Level.WARNING, InputClassroomFormController.class.getName());
            ErrorPopup error = new ErrorPopup(form, exception);
        }
    }

    /**
     * Private method to clearForm the data
     */
    private void clearForm() {
        form.getRoomNumberTextfield().setText("");
        form.getRoomTypeCombobox().setSelectedIndex(0);
    }

    /**
     * Private method to close the form
     */
    private void closeForm() {
        this.form.dispose();
    }

    // gettes to access the private data in the controller object
    public ClassroomDC getDataModel() {
        return m_classroomDataContainer;
    }

    public ClassroomInputForm getForm() {
        return form;
    }

    /**
     * Uses the isEmpty method from the String class to test whether the method
     * argument is empty. Notice that there is really no value added to have
     * a private helper method for isEmpty but I put it in for consistency
     *
     * @param data
     * @return
     */
    private boolean isEmpty(String data) {
        if (data.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Uses a regular expression (regex) to compare the format of the data
     * argument
     *
     * ^[a-zA-Z]{2}[0-9]{3}$ will compare the first 2 characters to a-z and A-Z
     * and the last 3 characters to numerics
     *
     * @param data
     * @return
     */
    private boolean isValid(String data) {

        if (data.matches("^[a-zA-Z]{2}[0-9]{3}$")) {
            return true;
        } else {
            return false;
        }
    }
}
