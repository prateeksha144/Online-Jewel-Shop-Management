package com.example.ProductManagement.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.ProductManagement.implementation.Productimplementation;
import com.example.ProductManagement.implementation.UserImplementation;
import com.example.ProductManagement.model.Products;
import com.example.ProductManagement.model.User;

@Controller
public class ProductController {

	@Autowired
	private Productimplementation productImplementation;
	@Autowired
	private UserImplementation userImplementation;
	@GetMapping("/home")
	public String getHome(Model model) {
		List<Products> listProducts=productImplementation.getProducts();
		System.out.println(listProducts.get(0));
		model.addAttribute("listProducts", listProducts);
		return "products";
	}
	
	@RequestMapping("/new")
	public String showNewProductForm(Model model) {
		Products p=new Products();
		model.addAttribute("product", p);
		return "new_product";
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String saveNewProduct(@ModelAttribute("product") Products product) {
		productImplementation.saveProducts(product);
		
		return "redirect:/home";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView EditProduct(@PathVariable(name="id") int id) {
		ModelAndView mav=new ModelAndView("edit_product");
		Products product= productImplementation.getProduct(id);
		mav.addObject("product",product);
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String DeleteProduct(@PathVariable(name="id") int id) {
		productImplementation.deleteProductById(id);
		return "redirect:/home";
	}
	
	@GetMapping("/login")
	public ModelAndView Login() {
		ModelAndView mav=new ModelAndView("login");
		mav.addObject("user",new User());
		return mav;
	}
	
	@PostMapping("/login")
	public String Login(@ModelAttribute("user") User user) {
		User auhuser=userImplementation.login(user.getUsername(), user.getPassword());
		System.err.println(auhuser+user.getUsername()+user.getPassword());
		if(Objects.nonNull(auhuser)) {
			return "redirect:/home";
		}else {
			return "redirect:/login";
		}
		//return null;
		
	}
}
