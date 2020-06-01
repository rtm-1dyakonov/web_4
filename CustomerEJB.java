package com.customer;

import com.com.dao.ClientDAO;
import com.com.dao.ClientDAOIml;
import com.models.Client;
import javax.ejb.Stateless;

//ejb bean отвечающий за валидацию логина и пароля
@Stateless
public class CustomerEJB {
// бизнес метод валидации
    public Client validateUserLogin(String login, String password) {
        ClientDAO clientDAO = new ClientDAOIml();
        // проверяем, если такой пользователь существует, то наполняем его данными и возвращаем
        if (clientDAO.isClientExist(login, password)) {
            Client client = new Client();
            client.setLogin(login);
            client.setPassword(password);
            clientDAO.setupClient(client);
            return client;
// если такого пользователя не существует, то возвращаем null
        } else {
            return null;
        }
    }
}
