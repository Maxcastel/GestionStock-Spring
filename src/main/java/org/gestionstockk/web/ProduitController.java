package org.gestionstockk.web;

import java.util.List;

import org.gestionstockk.dao.ProduitRepository;
import org.gestionstockk.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path="/produit")
public class ProduitController {
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@RequestMapping(path="/index")
	public String Index(Model model, @RequestParam(name="page", defaultValue="0") int p, @RequestParam(name="motCle", defaultValue="") String mc){
		//List<Produit> prods = produitRepository.findAll();
		//model.addAttribute("produits", prods);
		//Page<Produit> pageProduits = produitRepository.chercherProduits(mc, PageRequest.of(p, 2));
		Page<Produit> pageProduits = produitRepository.chercherProduits("%"+mc+"%", PageRequest.of(p, 2));
		
		int pagesCount = pageProduits.getTotalPages();
		int [] pages = new int[pagesCount];
		for(int i=0 ; i<pagesCount ; i++) {
			pages[i] = i;
		}
		
		model.addAttribute("pages", pages);
		model.addAttribute("pageCourante", p);
		model.addAttribute("motCle", mc);
		model.addAttribute("pageProduits", pageProduits);

		//model.addAttribute("p", p);
		//model.addAttribute("test", "aab");
		
		return "produits";
	}

}

