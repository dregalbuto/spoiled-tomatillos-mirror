package edu.northeastern.cs4500.user;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

public class RegistrationController {

	@RequestMapping(value = "/user/registration", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("user", userDto);
		return "registration";
	}

	@RequestMapping(value = "/user/registration", method = RequestMethod.GET)
	public ModelAndView registerUserAccount(
		@ModelAttribute("user") UserDto accountDto, 
		BindingResult result, 
		WebRequest request, 
		Errors errors) {
			User registered = new User();
		    if (!result.hasErrors()) {
		        registered = createUserAccount(accountDto, result);
		    }
		    if (registered == null) {
		        result.rejectValue("email", "message.regError");
		    }
		    if (result.hasErrors()) {
		        return new ModelAndView("registration", "user", accountDto);
		    } 
		    else {
		        return new ModelAndView("successRegister", "user", accountDto);
	    }
	}
	private User createUserAccount(UserDto accountDto, BindingResult result) {
	    User registered = null;
	    try {
	        registered = service.registerNewUserAccount(accountDto);
	    } catch (EmailExistsException e) {
	        return null;
	    }    
	    return registered;
	}

	
}
