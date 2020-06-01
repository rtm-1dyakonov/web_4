package com.customer;

import com.com.dao.ClientDAO;
import com.com.dao.ClientDAOIml;
import com.models.Car;
import javax.ejb.Stateless;

//ejb bean отвечающий за опереции над машинами
@Stateless
public class CarEJB {
// бизнес метод добавления новой машины в список
    public boolean addCar(Car car) {
        ClientDAO clientDAO = new ClientDAOIml();
        boolean inserted = clientDAO.addCar(car);
        return inserted;
    }
}
