package com.company.dict;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.ui.Model;
@Controller

public class HomeController {
	  @RequestMapping(value = "/welcome") 
	    public String welcome(Model model){ 
	         
	        
	         return "index"; 
	    } 
}
