package com.beans;

import com.com.dao.ClientDAO;
import com.com.dao.ClientDAOIml;
import com.customer.CarEJB;
import com.models.Car;
import com.models.Client;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

// управляемый компонент для работы с данными машины
@ManagedBean(name = "carBean")
@RequestScoped
public class CarBean {

    @EJB
    private CarEJB carEJB;
    private String model; // модель
    private String manufacturer; // производитель
    private double price; // стоимость
    private int count; // количество
    private static int rentalId;

    public CarBean() {

    }
// добавление машины
    public String addCar(CarBean carBean) {
        Car car = null;
        car = new Car(carBean.model, carBean.manufacturer, carBean.price, carBean.count, this.rentalId);
        boolean inserted = false;
       
        if (car != null) {
            // проверяем, что машина добавилась в базу
            inserted = carEJB.addCar(car);
        }
        // если машина была добавлена, обновляем список машин и сохраняем их в сессии
        if (inserted) {
            ClientDAO clientDAO = new ClientDAOIml();

            Client client = (Client) ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).getAttribute("client");
            clientDAO.setupClient(client);
            ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).setAttribute("client", client);
            // в этом случае переходим на страницу result
            return "result";
        } else {
            // иначе возвращается форма добавления машины
            return "insert";
        }
    }

    public String getAddCar(int rentalId) {
        this.rentalId = rentalId;
        return "insert";
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

}
