package com.taxi.controller.manufacturer;

import com.taxi.config.AppConfig;
import com.taxi.model.Manufacturer;
import com.taxi.service.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/manufacturers/add")
public class AddManufacturerController extends HttpServlet {
    private final AnnotationConfigApplicationContext context;
    private final ManufacturerService manufacturerService;

    public AddManufacturerController() {
        this.context = new AnnotationConfigApplicationContext(AppConfig.class);
        this.manufacturerService = context.getBean(ManufacturerService.class);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/manufacturer/addManufacturer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String country = req.getParameter("country");
        manufacturerService.create(new Manufacturer(name,country));
        resp.sendRedirect(req.getContextPath() + "/manufacturers");
    }
}
