package com.example.application.views.login;

import com.example.application.backend.entity.User;
import com.example.application.backend.service.CompanyService;
import com.example.application.backend.service.ContactService;
import com.example.application.backend.service.UserService;
import com.example.application.views.list.ListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Login | Vaadin CRM")
public class LoginView extends VerticalLayout {

    private UserService userService;
    private ContactService contactService;
    private CompanyService companyService;

    public LoginView(UserService userService, ContactService contactService, CompanyService companyService) {
        this.userService = userService;
        this.contactService = contactService;
        this.companyService = companyService;

        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(loginEvent -> {
            String username = loginEvent.getUsername();
            String password = loginEvent.getPassword();
            User user = userService.findUserByUserNameAndPassword(username, password);
            if (user != null) {
                loginEvent.getSource().getUI().get().navigate(ListView.class);
            } else {
                loginForm.setError(true);
            }
        });

        add(loginForm);
        setAlignItems(Alignment.CENTER);
    }
}