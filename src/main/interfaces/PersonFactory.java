package interfaces;

import tutorials.Person;

public interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}
