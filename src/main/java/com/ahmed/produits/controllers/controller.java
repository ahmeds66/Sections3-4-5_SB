package com.ahmed.produits.controllers;

import com.ahmed.produits.entities.Produit;
import com.ahmed.produits.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class controller {
    @Autowired
    private ProduitService produitService;

    @RequestMapping("/showCreate")
    public String showCreate() {
        return "createProduit";
    }

    @RequestMapping("/saveProduit")
    public String saveProduit(@ModelAttribute("produit") Produit produit, @RequestParam("date") String date, ModelMap modelMap)
            throws ParseException {
        //conversion de la date
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCreation = dateformat.parse(date);
        produit.setDateCreation(dateCreation);

        //enregistrement du produit
        Produit saveProduit = produitService.saveProduit(produit);
        String msg = "Produit enregistré avec Id " + saveProduit.getIdProduit();
        modelMap.addAttribute("msg", msg);
        return "createProduit";
    }

    @RequestMapping("/ListeProduits")
    public String listeProduits(ModelMap modelMap)
    {
        List<Produit> prods = produitService.getAllProduits();
        modelMap.addAttribute("produits", prods);
        return "listeProduits";
    }

    @RequestMapping("/supprimerProduit")
    public String supprimerProduit(@RequestParam("id") Long id,
                                   ModelMap modelMap)
    {
        produitService.deleteProduitById(id);
        List<Produit> prods = produitService.getAllProduits();
        modelMap.addAttribute("produits", prods);
        return "listeProduits";
    }

    @RequestMapping("/modifierProduit")
    public String editerProduit(@RequestParam("id") Long id,ModelMap modelMap)
    {
        Produit p= produitService.getProduit(id);
        modelMap.addAttribute("produit", p);
        return "editerProduit";
    }
    @RequestMapping("/updateProduit")
    public String updateProduit(@ModelAttribute("produit") Produit produit,
                                @RequestParam("date") String date,ModelMap modelMap) throws ParseException
    {
//conversion de la date
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCreation = dateformat.parse(String.valueOf(date));
        produit.setDateCreation(dateCreation);
        produitService.updateProduit(produit);
        List<Produit> prods = produitService.getAllProduits();
        modelMap.addAttribute("produits", prods);
        return "listeProduits";
    }
}
