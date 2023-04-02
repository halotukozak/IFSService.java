package zadanie.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IFSServiceTest {

    IFSService service;

    @BeforeEach
    void setUp() {
        service = new IFSService();
    }

    @Test
    void tooManyArguments() {
        assertThrows(IllegalArgumentException.class, () -> service.parseArguments(new String[]{"store.json", "orders.json", "another.json"}));
    }

    @Test
    void tooLessArguments() {
        assertThrows(IllegalArgumentException.class, () -> service.parseArguments(new String[]{"store.json"}));
        assertThrows(IllegalArgumentException.class, () -> service.parseArguments(new String[]{}));
    }

    @Test
    void invalidFormatOfArguments() {
        assertThrows(IllegalArgumentException.class, () -> service.parseArguments(new String[]{"65456", "46464"}));
        assertThrows(IllegalArgumentException.class, () -> service.parseArguments(new String[]{"/home/cat.jpeg", "/home/dog.gif"}));
    }

    @Test
    void nonExistingFiles(){
        assertThrows(IllegalArgumentException.class, () -> service.parseArguments(new String[]{"/not/existing/file.json", "/home/store.json"}));
    }


}