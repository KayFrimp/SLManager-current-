package net.jojoaddison.slmanager.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Professor.
 */
@Document(collection = "professor")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "professor")
public class Professor implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;

    @NotNull
    @Size(min = 3)
    @Field("first_name")
    private String firstName;

    @Size(min = 3)
    @Field("last_name")
    private String lastName;

    @DBRef
    @Field("lecture")
    private Set<Lecture> lectures = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Professor firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Professor lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Lecture> getLectures() {
        return lectures;
    }

    public Professor lectures(Set<Lecture> lectures) {
        this.lectures = lectures;
        return this;
    }

    public Professor addLecture(Lecture lecture) {
        this.lectures.add(lecture);
        //lecture.setProfessor(this);
        return this;
    }

    public Professor removeLecture(Lecture lecture) {
        this.lectures.remove(lecture);
        //lecture.setProfessor(null);
        return this;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Professor professor = (Professor) o;
        if (professor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Professor{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
