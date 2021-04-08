package com.example.application.views.list;

import com.example.application.backend.entity.Company;
import com.example.application.backend.entity.Contact;
import com.example.application.backend.service.CompanyService;
import com.example.application.backend.service.ContactService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "ListView", layout = MainLayout.class)
@PageTitle("Contacts | Vaadin CRM")
@CssImport("./styles/shared-styles.css")
public class ListView extends VerticalLayout {

    private ContactService contactService;
    private CompanyService companyService;

    private ContactForm contactForm;
    private Div content;

    Grid<Contact> grid = new Grid<>(Contact.class);
    TextField filterField = new TextField();

    public ListView(ContactService contactService, CompanyService companyService) {
        this.contactService = contactService;
        this.companyService = companyService;

        buildFormLayout();
        buildContactLayout();

        add(filterField, content);
        updateList();

        closeEditor();
    }

    private void buildFormLayout() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureFilter();
    }

    private void buildContactLayout() {
        //form alanını div ile sarıcaz hem daha duyarlı hem mobil için
        contactForm = new ContactForm(companyService.findAll());
        contactForm.addListener(ContactForm.SaveEvent.class, this::saveContact);
        contactForm.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
        contactForm.addListener(ContactForm.CloseEvent.class, e -> closeEditor());

        content = new Div(grid, contactForm);
        content.addClassName("content");
        content.setSizeFull();
    }

    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("company");
        grid.setColumns("id", "firstName", "lastName", "email", "status");
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            return company == null ? "-" : company.getName();
        }).setHeader("Company");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editContact(evt.getValue()));
    }

    private void configureFilter() {
        filterField.setPlaceholder("Filter By FirstName..");
        filterField.setClearButtonVisible(true);
        //giriş yaptıktan sonra birkaç saniye bekler
        filterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterField.addValueChangeListener(e -> updateListByFirstName(filterField.getValue()));
    }

    private void updateList() {
        grid.setItems(contactService.findAll());
    }

    private void updateListByFirstName(String firstName) {
        grid.setItems(contactService.findContactByName(firstName));
    }

    private void editContact(Contact contact) {
        if (contact == null) {
            closeEditor();
        } else {
            contactForm.setContact(contact);
            contactForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        contactForm.setContact(null);
        contactForm.setVisible(false);
        removeClassName("editing");
    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        contactService.delete(event.getContact());
        updateList();
        closeEditor();
    }

    private void saveContact(ContactForm.SaveEvent event) {
        contactService.save(event.getContact());
        updateList();
        closeEditor();
    }
}
