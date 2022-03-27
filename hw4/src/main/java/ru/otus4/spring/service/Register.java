package ru.otus4.spring.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus4.spring.domain.User;


@ShellComponent
public class Register {

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    @ShellMethod(value="Login", key={"login","l"})
    public String registerCurrentUser(String name) {
        this.currentUser = new User(name);
        return String.format("%s, теперь ты можешь начать тест", currentUser.getName());
    }
}
