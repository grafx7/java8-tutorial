package Interface;

import main.Person;

public interface PersonFactory<P extends Person> {
    P create(String firstName, String lastName);
}
