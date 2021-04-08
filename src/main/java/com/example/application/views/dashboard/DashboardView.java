package com.example.application.views.dashboard;

import com.example.application.backend.service.CompanyService;
import com.example.application.backend.service.ContactService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Map;

@PageTitle("Dashboard | Vaadin CRM")
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {
    private final CompanyService companyService;
    private  final ContactService contactService;

    public DashboardView(ContactService contactService,
                         CompanyService companyService) {
        this.companyService = companyService;
        this.contactService = contactService;

        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(
                getContactStats(),
                getCompaniesChart()
                );

    }

    private Chart getCompaniesChart() {
        Chart pieChart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> stats = companyService.getStats();
        stats.forEach((name, number) -> dataSeries.add(new DataSeriesItem(name, number)));
        pieChart.getConfiguration().setSeries(dataSeries);
        return pieChart;
    }

    private Span getContactStats() {
        Span stats = new Span(contactService.count()+" contacts");
        stats.addClassName("contact-stats");
        return stats;
    }
}
