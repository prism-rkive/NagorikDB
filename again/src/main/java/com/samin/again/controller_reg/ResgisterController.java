/*package com.samin.again.controller_reg;

import com.samin.again.entity.User;
import com.samin.again.repository_reg.RegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ResgisterController {
    @Autowired
    private RegistrationRepo registrationuser;

    @GetMapping("/showRegister")
    public ModelAndView showsignupform(){
        ModelAndView modelAndView = new ModelAndView( "signup");
        return modelAndView;
    }
    @PostMapping("/register")
    public ModelAndView signedup (User registration) {
        registrationuser.save(registration);
        ModelAndView modelAndView=new ModelAndView("login");
//        modelAndView.addObject("successMessage", "Registration sucessful");
        return modelAndView;
    }

    @GetMapping("/Showlogin")
    public ModelAndView showloginpage(){
        ModelAndView modelAndView=new ModelAndView("login");
        return modelAndView;
    }
    @PostMapping("/login")
    public ModelAndView loggedin(Long NID, String password){
        ModelAndView modelAndView;
        boolean matched=true;;
                if(matched){
                    modelAndView=new ModelAndView("homepg");
                    return modelAndView;
                }
                modelAndView=new ModelAndView("login");
                return modelAndView;
    }


}*/



