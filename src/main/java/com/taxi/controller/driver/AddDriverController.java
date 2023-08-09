package com.taxi.controller.driver;

import com.taxi.config.AppConfig;
import com.taxi.model.Driver;
import com.taxi.service.DriverService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/drivers/add")
public class AddDriverController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/driver/addDriver.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        DriverService driverService = context.getBean(DriverService.class);
        String name = req.getParameter("name");
        String licenseNumber = req.getParameter("licenseNumber");
        driverService.create(new Driver(name,licenseNumber));
        resp.sendRedirect(req.getContextPath() + "/drivers/getAll");
    }
}
