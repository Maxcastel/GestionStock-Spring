package org.gestionstockk.web;

import java.io.File;
import java.util.List;

import org.gestionstockk.dao.ProduitRepository;
import org.gestionstockk.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

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
	
	@RequestMapping(path="/form", method=RequestMethod.GET)
	public String formProduit(Model model){
		
		model.addAttribute("produit", new Produit("exemple",0.0,"exemple.jpg"));
		
		return "formProduit";
	}

	@RequestMapping(path="/enregistrerProduit", method=RequestMethod.POST)
	public String enregistrerProduit(@Valid Produit pd, BindingResult bindBindingResult, @RequestParam(name="picture") MultipartFile file) throws Exception {
		
		if(bindBindingResult.hasErrors()) {
			return "formProduit";
		}
		if(!file.isEmpty()) {
			pd.setPhoto(file.getOriginalFilename());
			//file.transferTo(new File(System.getProperty("user.home") + "/stock/" + file.getOriginalFilename()));
		}
		
		produitRepository.save(pd);
		
		return "redirect:index";
	}
}

