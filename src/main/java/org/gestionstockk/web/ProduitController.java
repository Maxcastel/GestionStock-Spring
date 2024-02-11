package org.gestionstockk.web;

import java.util.List;

import org.gestionstockk.dao.ProduitRepository;
import org.gestionstockk.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/produit")
public class ProduitController {
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@RequestMapping(path="/index")
	public String Index(Model model) {
		List<Produit> prods = produitRepository.findAll();
		model.addAttribute("produits", prods);
		
		return "produits";
	}

}

