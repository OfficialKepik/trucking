package com.example.diplom.controller;

import com.example.diplom.model.Order;
import com.example.diplom.service.OrderService;
import com.example.diplom.model.User;
import com.example.diplom.user.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public String orderProcess(@ModelAttribute("order") Order order) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        User user = userDetail.getUser();
        order.setUser(user);

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String newData = time.format(formatter);
        order.setCreated(newData);

        user.getOrders().add(order);
        orderService.save(order);
        sendTelegramNotificationOrder(order);
        return "redirect:/profile";
    }

    private void sendTelegramNotificationOrder(Order order) {
        try {
            String botToken = "6538053068:AAEyMclICKtfBjjg2_dM2AQFdd0Ft58OYmA";
            String chatId = "453355030";

            URL url = new URL("https://api.telegram.org/bot" + botToken + "/sendMessage");

            Map<String, String> params = new HashMap<>();
            params.put("chat_id", chatId);
            params.put("text", "Поступил новый заказ:\n" +
                    "ФИО: " + order.getUser().getUsername() + "\n" +
                    "Почта: " + order.getUser().getEmailUser() + "\n" +
                    "Телефон: " + order.getUser().getPhoneUser() + "\n" +
                    "Описание: " + order.getDescription() + "\n" +
                    "Адресс: " + order.getAddress() + "\n" +
                    "Дата: " + order.getCreated()
            );

            // POST-запрос к Telegram Bot API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                postData.append(param.getKey()).append("=").append(param.getValue()).append("&");
            }
            os.write(postData.toString().getBytes("UTF-8"));
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                System.out.println("Уведомление успешно отправлено в Telegram.");
            } else {
                System.out.println("Ошибка при отправке уведомления в Telegram. Код ответа: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}