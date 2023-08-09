package com.taxi.controller.manufacturer;

import com.taxi.config.AppConfig;
import com.taxi.service.ManufacturerService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/manufacturers/delete")
public class DeleteManufacturerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        ManufacturerService manufacturerService = context.getBean(ManufacturerService.class);
        Long driverId = Long.parseLong(req.getParameter("id"));
        manufacturerService.delete(driverId);
        req.getRequestDispatcher(req.getContextPath() + "/manufacturers/getAll").forward(req, resp);
    }
}
