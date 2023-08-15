package com.taxi.controller.driver;

import com.taxi.config.AppConfig;
import com.taxi.model.Driver;
import com.taxi.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/drivers/add")
public class AddDriverController extends HttpServlet {
    private final DriverService driverService;

    public AddDriverController() {
        this.driverService = new AnnotationConfigApplicationContext(AppConfig.class).getBean(DriverService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/driver/addDriver.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String licenseNumber = req.getParameter("licenseNumber");
        driverService.create(new Driver(name,licenseNumber));
        resp.sendRedirect(req.getContextPath() + "/drivers");
    }
}
