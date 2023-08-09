package com.taxi.controller.car;

import com.taxi.config.AppConfig;
import com.taxi.model.Car;
import com.taxi.model.Driver;
import com.taxi.service.CarService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cars")
public class CarManagerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        CarService carService = context.getBean(CarService.class);
        List<Car> carList = carService.getAll();
        req.setAttribute("cars", carList);
        req.getRequestDispatcher("/WEB-INF/views/car/carManager.jsp").forward(req, resp);
    }
}
