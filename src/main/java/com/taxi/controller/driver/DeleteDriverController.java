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
    private final AnnotationConfigApplicationContext context;
    private final DriverService driverService;

    public DeleteDriverController() {
        this.context = new AnnotationConfigApplicationContext(AppConfig.class);
        this.driverService = context.getBean(DriverService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long driverId = Long.parseLong(req.getParameter("id"));
        driverService.delete(driverId);
        req.getRequestDispatcher(req.getContextPath() + "/drivers").forward(req, resp);
    }
}
