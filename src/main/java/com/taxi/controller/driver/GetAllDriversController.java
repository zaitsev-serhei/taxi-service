package com.taxi.controller.driver;

import com.taxi.config.AppConfig;
import com.taxi.model.Driver;
import com.taxi.service.DriverService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/drivers")
public class GetAllDriversController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        DriverService driverService = context.getBean(DriverService.class);
        List<Driver> driversList = driverService.getAll();
        req.setAttribute("drivers", driversList);
        req.getRequestDispatcher("/WEB-INF/views/driver/getAllDrivers.jsp").forward(req, resp);
    }
}
