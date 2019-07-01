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
    @Field("teaches")
    private Set<Lecture> teaches = new HashSet<>();
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

    public Set<Lecture> getTeaches() {
        return teaches;
    }

    public Professor teaches(Set<Lecture> lectures) {
        this.teaches = lectures;
        return this;
    }

    public Professor addTeaches(Lecture lecture) {
        this.teaches.add(lecture);
        lecture.setEntitledTo(this);
        return this;
    }

    public Professor removeTeaches(Lecture lecture) {
        boolean remove = this.teaches.remove(lecture);
        lecture.setEntitledTo(null);
        return this;
    }

    public void setTeaches(Set<Lecture> lectures) {
        this.teaches = lectures;
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
