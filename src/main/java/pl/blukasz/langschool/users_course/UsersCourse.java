package pl.blukasz.langschool.users_course;

import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.users.User;

import javax.persistence.*;


@Entity
public class UsersCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User student;
    @OneToOne
    private Course course;
    private Double finalGrade;







    public UsersCourse() {
    }


    public UsersCourse(User student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Long getId() {
        return id;
    }


    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }



    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(Double finalGrade) {
        this.finalGrade = finalGrade;
    }
}
