package com.taxi.controller.car;

import com.taxi.config.AppConfig;
import com.taxi.model.Car;
import com.taxi.model.Manufacturer;
import com.taxi.service.CarService;
import com.taxi.service.ManufacturerService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/cars/add")
public class AddCarController extends HttpServlet {
    private final AnnotationConfigApplicationContext context;
    private final ManufacturerService manufacturerService;
    private final CarService carService;

    public AddCarController() {
        this.context = new AnnotationConfigApplicationContext(AppConfig.class);
        this.carService = context.getBean(CarService.class);
        this.manufacturerService = context.getBean(ManufacturerService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("manufacturerList", manufacturerService.getAll());
        req.getRequestDispatcher("/WEB-INF/views/car/addCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String model = req.getParameter("model");
        Long manufacturerId = Long.parseLong(req.getParameter("option_manufacturer"));
        Manufacturer manufacturer = manufacturerService.get(manufacturerId);
        carService.create(new Car(model, manufacturer));
        resp.sendRedirect(req.getContextPath() + "/cars");
    }
}
