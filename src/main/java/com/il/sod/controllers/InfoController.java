package com.il.sod.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/info")
public class InfoController {
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView helloWorld(){
 
		ModelAndView model = new ModelAndView("info");
		model.addObject("msg", "hello world");
 
		return model;
	}
	
}

