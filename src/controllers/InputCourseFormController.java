/*
 * The InputCourseFormController class contains the following:
 *
 * 1) an instance of the classroom data model which is used to populate the
 *    list of classrooms on the form. (passed in via the constructor)
 * 2) an instance of the offered course data model which is used to store any
 *    offered course objects created (passed in via the constructor)
 * 3) an instance of the offered course input form. (created here)
 *
 * Listens for events on the input form.
 * Implements the ActionListener interface which contains a single method,
 * "actionPerformed" - this method contains all the logic to process the data
 * on the form, as well as several other events
 */
package controllers;

import datacontainers.ClassroomDC;
import datacontainers.CourseDC;
import datamodels.Classroom;
import datamodels.Course;
import exceptionhandlers.ErrorPopup;
import exceptionhandlers.InvalidDataException;
import exceptionhandlers.MissingDataException;
import utilities.ConsoleLogger;
import view.inputforms.CourseInputForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;

public class InputCourseFormController implements ActionListener {

    // The data datacontainers needed for this form are passed in via the constructor
    CourseDC CourseDC;
    ClassroomDC ClassroomDC;

    // The form is created here
    private CourseInputForm form;

    /**
     * Constructor
     *
     * @param CourseDC
     * @param ClassroomDC
     */
    public InputCourseFormController(CourseDC CourseDC,
                                     ClassroomDC ClassroomDC) {

        // Store the passed in data datacontainers
        this.CourseDC = CourseDC;
        this.ClassroomDC = ClassroomDC;

        // Create the form
        form = new CourseInputForm(this);

        // make the form visible
        this.form.setVisible(true);
    }

    /**
     * actionPerformed method implemented from the ActionListener interface
     *
     * This method figures out which button was clicked based on the text of the
     * button. The button click event is passed in via the ActionEvent object.
     *
     * @param event
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("Save")) {
            this.saveData();
        } else if (event.getActionCommand().equals("Clear")) {
            this.clearForm();
        } else if (event.getActionCommand().equals("Close")) {
            this.closeForm();
        }
    }

    /**
     * Private method called from the actionPerformed method to save the daa
     */
    private void saveData() {

        try {
            // Create a new Course object
            Course newCourse = new Course();

            // Retrieve the selected classroom from the drop down list
            // The objects in the list are stored as generic objects and have to be
            // converted to classroom objects
            Classroom classroom = (Classroom) this.form.getListOfClassrooms().getSelectedValue();

            // Store the classroom
            newCourse.setClassroom(classroom);


            // Retrieve the course id from the form
            String courseID = this.form.getCourseIdField().getText();

            // Retrieve the course name
            String courseName = this.form.getCourseNameField().getText();

            // Set the course name in the object
            newCourse.setCourseName(courseName);

            // Set the course id in the object
            newCourse.setCourseID(courseID);

            // Store the object in the application data model list of courses
            this.CourseDC.getListOfCourses().add(newCourse);
            ConsoleLogger.log(Level.INFO,"Course Data Saved");
        } catch (InvalidDataException | MissingDataException | IOException exception){
            ConsoleLogger.log(Level.WARNING, InputCourseFormController.class.getName());
            ErrorPopup error = new ErrorPopup(form, exception);
        }

    }

    /**
     * Private method to clear the data
     */
    private void clearForm() {
        this.form.getCourseIdField().setText("");
        this.form.getCourseNameField().setText("");
        this.form.getListOfClassrooms().setSelectedIndex(0);
    }

    /**
     * Private method to close the form
     */
    private void closeForm() {
        this.form.dispose();
    }

    public ClassroomDC getClassroomDC() {
        return ClassroomDC;
    }

}
