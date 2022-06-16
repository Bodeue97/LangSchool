package pl.blukasz.langschool.course;


import pl.blukasz.langschool.users.User;

import javax.persistence.*;

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String courseName;
    private String description;
    private Double price;
    @OneToOne
    private User teacher;

    public Course() {
    }

    public Course(String courseName, String description, Double price, User teacher) {
        this.courseName = courseName;
        this.description = description;
        this.price = price;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }
}
