package com.taxi.controller.driver;

import com.taxi.config.AppConfig;
import com.taxi.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/drivers/delete")
public class DeleteDriverController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        DriverService driverService = context.getBean(DriverService.class);
        Long driverId = Long.parseLong(req.getParameter("id"));
        driverService.delete(driverId);
        req.getRequestDispatcher(req.getContextPath() + "/drivers").forward(req, resp);
    }
}
