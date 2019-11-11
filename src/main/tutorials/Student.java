package tutorials;

import java.util.Objects;

public class Student {
    private String name;
    private Speciality speciality;
    private int year;

    public Student() {
    }

    public Student(String name, Speciality speciality, int year) {
        this.name = name;
        this.speciality = speciality;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", speciality=" + speciality +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return year == student.year &&
                Objects.equals(name, student.name) &&
                speciality == student.speciality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, speciality, year);
    }
}
