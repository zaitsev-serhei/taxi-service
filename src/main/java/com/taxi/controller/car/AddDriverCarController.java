package com.taxi.controller.car;

import com.taxi.config.AppConfig;
import com.taxi.model.Car;
import com.taxi.model.Driver;
import com.taxi.service.CarService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.taxi.service.DriverService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/cars/addDriver")
public class AddDriverCarController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        CarService carService = context.getBean(CarService.class);
        if(req.getParameter("id") != null){
            Long carId = Long.parseLong(req.getParameter("id"));
            req.setAttribute("car", carService.get(carId));
        }
        DriverService driverService = context.getBean(DriverService.class);
        req.setAttribute("carList", carService.getAll());
        req.setAttribute("driverList", driverService.getAll());
        req.getRequestDispatcher("/WEB-INF/views/car/addDriverToCar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        CarService carService = context.getBean(CarService.class);
        DriverService driverService = context.getBean(DriverService.class);
        Long carId = Long.parseLong(req.getParameter("carId"));
        Car car = carService.get(carId);
        Long driverId = Long.parseLong(req.getParameter("option_driver"));
        Driver driver = driverService.get(driverId);
        carService.addDriverToCar(driver, car);
        resp.sendRedirect(req.getContextPath() + "/cars/getAll");
    }
}
