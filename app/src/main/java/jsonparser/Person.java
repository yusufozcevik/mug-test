package jsonparser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Person.class)
public class Person {
    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("firstname")
    public String getFirstname() {
        return firstname;
    }

    @JsonProperty("lastname")
    public String getLastname() {
        return lastname;
    }

    @JsonProperty("firstname")
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @JsonProperty("lastname")
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("age")
    public int getAge() {
        return age;
    }

    @JsonProperty("age")
    public void setAge(int age) {
        this.age = age;
    }

    @JsonProperty("age")
    private int age;
}
