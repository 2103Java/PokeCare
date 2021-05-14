package com.revature.pokecare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@Controller
public class ViewController {

    @GetMapping(value = "**")
    public void locale(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String locale = "/" + request.getLocale().getLanguage();
        InputStream is;

        if (request.getRequestURI().startsWith(locale)) {
            is = request.getServletContext().getResourceAsStream(request.getRequestURI());
        } else {
            is = request.getServletContext().getResourceAsStream(locale + request.getRequestURI());
        }

        if (is == null) {
            is = request.getServletContext().getResourceAsStream(locale + "/index.html");
        }

        if (is == null) {
            System.out.println(request.getLocale() + " does not exist?");
            is = request.getServletContext().getResourceAsStream("/en-US/index.html");
        }

        PrintWriter wr = response.getWriter();

        if (is != null) {
            int read;

            while ((read = is.read()) != -1) {
                wr.write(read);
            }
        }
    }
}