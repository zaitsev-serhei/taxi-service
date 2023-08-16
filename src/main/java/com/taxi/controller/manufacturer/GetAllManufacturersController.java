package com.taxi.controller.manufacturer;

import com.taxi.config.AppConfig;
import com.taxi.model.Manufacturer;
import com.taxi.service.ManufacturerService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/manufacturers")
public class GetAllManufacturersController extends HttpServlet {
    private final ManufacturerService manufacturerService;

    public GetAllManufacturersController() {
        this.manufacturerService = new AnnotationConfigApplicationContext(AppConfig.class)
                .getBean(ManufacturerService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Manufacturer> manufacturers = manufacturerService.getAll();
        req.setAttribute("manufacturers", manufacturers);
        req.getRequestDispatcher("/WEB-INF/views/manufacturer/getAllManufacturers.jsp").forward(req, resp);
    }
}
