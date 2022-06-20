package pl.blukasz.langschool.grade;

import pl.blukasz.langschool.course.Course;
import pl.blukasz.langschool.users.User;

import javax.persistence.*;

@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double grade;
    @OneToOne
    private User student;
    @OneToOne
    private Course course;


    public Grade(Double grade, User student, Course course) {
        this.grade = grade;
        this.student = student;
        this.course = course;
    }

    public Grade() {
    }



    public Long getId() {
        return id;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
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
}
