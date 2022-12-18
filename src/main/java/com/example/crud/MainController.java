package com.example.crud;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(path = "/crud")
public class MainController {

	@Autowired
	private UserRepository userRepository;
	
    @RequestMapping("/inicio")
    public String home(Model model) {
    	List<User> users = (List<User>) userRepository.findAll();
    	model.addAttribute("users", users);
        return "home";
    }
    
    @GetMapping("/form")
    public String add() {
        return "add-user";
    }

	@RequestMapping("/add")
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String email) {
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return "Saved";
	}

	@GetMapping("delete/{id}")
	public @ResponseBody String deleteUser(@PathVariable(name = "id") int id) {
		userRepository.deleteById(id);
		return "Deleted";
	}
	
	@GetMapping(path = "/retrieve")
	public @ResponseBody Optional<User> retrieveUser(@RequestParam Integer id) {
		return userRepository.findById(id);
	}
	
	@RequestMapping(path = "/update")
	public @ResponseBody String updateUser(@RequestParam Integer id, String name, @RequestParam String email) {
		User n = retrieveUser(id).get();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return "Updated";
	}
}