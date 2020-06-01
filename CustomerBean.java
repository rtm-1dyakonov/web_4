package com.beans;

import com.converters.ClientXMLConverter;
import com.customer.CustomerEJB;
import com.models.Client;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// управляемый компонент для работы с пользователями
@ManagedBean(name = "customerBean")
@RequestScoped
public class CustomerBean {

    @EJB
    private CustomerEJB customerEJB;
    private static HttpSession session;
    private String login;
    private String password;
    private boolean isLoginError;
    private boolean isPassError;
    private boolean isNoClientError;

    public boolean isIsLoginError() {
        return isLoginError;
    }

    public boolean isIsPassError() {
        return isPassError;
    }

    public boolean isIsNoClientError() {
        return isNoClientError;
    }

    public CustomerBean() {

    }
// метод валидации логина и пароля а также обработки выхода из аккаунта

    public String validateUserLogin() {
        String appResponse = "index";
        this.isLoginError = false;
        this.isPassError = false;
        this.isNoClientError = false;
        Map<String, String> parameterValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        // если необходимо зайти в аккаунт
        if (parameterValue.get("operation").equals("login")) {
            // если что-то из логина или пароля отсутствует, готовим сообщение об ошибке
            if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
                if (login == null || login.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage("login-form:login", new FacesMessage("Поле ввода логина обязательно для заполнения"));
                    this.isLoginError = true;
                }
                if (password == null || password.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage("login-form:password", new FacesMessage("Поле ввода пароля обязательно для заполнения"));
                    this.isPassError = true;
                }
                // иначе, если и логин и пароль есть, то надо проверить существует ли такой пользователь
            } else {
                this.session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                Client client = customerEJB.validateUserLogin(this.login, this.password);
                // если пользователь существует, то сохраняем его в сессии и переходим на страницу result
                if (client != null) {
                    session.setAttribute("client", client);
                    session.setMaxInactiveInterval(30 * 60);
                    appResponse = "result";
                } else {
                    FacesContext.getCurrentInstance().addMessage("login-form:login", new FacesMessage("Пользователь не найден"));
                    this.isNoClientError = true;
                }
            }

            // иначе, еслит необходимо выйти из аккаунта, сессия разрушается
        } else {
            if (session != null) {
                session.invalidate();

            }
        }
        // если с вводом что-то не так или необходимо было выйти из аккаунта, то возвращаемся обратно на страницу входа
        return appResponse;
    }
// метод возвращения информации о клиенте в виде xml формы
    public String getXMLReport() {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        Client client = (Client) session.getAttribute("client");
        ClientXMLConverter xmlConverter = new ClientXMLConverter();

        try (ByteArrayOutputStream bos = xmlConverter.convertClient(client)) {
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "filename=" + "result.xml");
            OutputStream os = response.getOutputStream();
            bos.writeTo(os);
            os.flush();
            os.close();
        } catch (IOException ex) {
            Logger.getLogger(CustomerBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "ok";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
