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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        ManufacturerService manufacturerService = context.getBean(ManufacturerService.class);
        req.setAttribute("manufacturerList", manufacturerService.getAll());
        req.getRequestDispatcher("/WEB-INF/views/car/addCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        CarService carService = context.getBean(CarService.class);
        ManufacturerService manufacturerService = context.getBean(ManufacturerService.class);
        String model = req.getParameter("model");
        Long manufacturerId = Long.parseLong(req.getParameter("option_manufacturer"));
        Manufacturer manufacturer = manufacturerService.get(manufacturerId);
        carService.create(new Car(model, manufacturer));
        resp.sendRedirect(req.getContextPath() + "/cars/getAll");
    }
}
