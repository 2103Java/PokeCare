package com.revature.pokecare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ViewController {

    @GetMapping(value = "**")
    public void locale(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String locale = "/" + request.getLocale().getLanguage();
        InputStream is = request.getServletContext().getResourceAsStream(request.getRequestURI());

        if (is == null && !request.getRequestURI().startsWith(locale)) {
            is = request.getServletContext().getResourceAsStream(locale + request.getRequestURI());
        }

        if (is == null) {
            is = request.getServletContext().getResourceAsStream(locale + "/index.html");
        }

        if (is == null) {
            System.out.println(request.getLocale() + " does not exist?");
            is = request.getServletContext().getResourceAsStream("/en/index.html");

            if (is == null) {
                is = request.getServletContext().getResourceAsStream("/index.html");
            }
        }

        if (is != null) {
            byte[] buffer = new byte[is.available()];

            is.read(buffer);
            response.getOutputStream().write(buffer);
        }
    }
}