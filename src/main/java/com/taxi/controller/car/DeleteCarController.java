package com.taxi.controller.car;

import com.taxi.config.AppConfig;
import com.taxi.service.CarService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebServlet("/cars/delete")
public class DeleteCarController extends HttpServlet {
    private final CarService carService;

    public DeleteCarController() {
        this.carService = new AnnotationConfigApplicationContext(AppConfig.class).getBean(CarService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long carId = Long.parseLong(req.getParameter("id"));
        carService.delete(carId);
        req.getRequestDispatcher(req.getContextPath() + "/cars").forward(req, resp);
    }
}
