package com.taxi.controller.manufacturer;

import com.taxi.config.AppConfig;
import com.taxi.service.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/manufacturers/delete")
public class DeleteManufacturerController extends HttpServlet {
    private final ManufacturerService manufacturerService;

    public DeleteManufacturerController() {
        this.manufacturerService = new AnnotationConfigApplicationContext(AppConfig.class)
                .getBean(ManufacturerService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long driverId = Long.parseLong(req.getParameter("id"));
        manufacturerService.delete(driverId);
        req.getRequestDispatcher(req.getContextPath() + "/manufacturers").forward(req, resp);
    }
}
