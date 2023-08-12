package com.taxi.controller.car;

import com.taxi.config.AppConfig;
import com.taxi.service.CarService;
import com.taxi.service.DriverService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/cars/drivers/delete")
public class RemoveDriverFromCarController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        CarService carService = context.getBean(CarService.class);
        DriverService driverService = context.getBean(DriverService.class);
        Long carId = Long.parseLong(req.getParameter("carId"));
        Long driverId = Long.parseLong(req.getParameter("driverId"));
        carService.removeDriverFromCar(driverService.get(driverId), carService.get(carId));
        req.getRequestDispatcher(req.getContextPath() + "/cars").forward(req, resp);
    }
}
