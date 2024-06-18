package com.carauction.controllers;

import com.carauction.controllers.Main.Main;
import com.carauction.models.enums.Transmission;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CatalogCont extends Main {
    @GetMapping("/catalog")
    public String catalog(Model model) {
        model.addAttribute("auctions", auctionRepo.findAll());
        model.addAttribute("transmissions", Transmission.values());
        model.addAttribute("role", getRole());
        return "auctions";
    }

    @GetMapping("/catalog/all")
    public String catalog_main(Model model) {
        model.addAttribute("auctions", auctionRepo.findAll());
        model.addAttribute("transmissions", Transmission.values());
        model.addAttribute("role", getRole());
        return "auctions";
    }

    @PostMapping("/catalog/search")
    public String search(@RequestParam Transmission transmission, Model model) {
        model.addAttribute("auctions", auctionRepo.findAllByTransmission(transmission));
        model.addAttribute("transmissions", Transmission.values());
        model.addAttribute("role", getRole());
        return "auctions";
    }

    @GetMapping("/catalog/field/{transmission}")
    public String transmission(@PathVariable Transmission transmission, Model model) {
        model.addAttribute("auctions", auctionRepo.findAllByTransmission(transmission));
        model.addAttribute("transmissions", Transmission.values());
        model.addAttribute("role", getRole());
        return "auctions";
    }


    @PostMapping("/catalog/search/name")
    public String search(@RequestParam String search, Model model) {
        model.addAttribute("auctions", auctionRepo.findAllByNameContaining(search));
        model.addAttribute("transmissions", Transmission.values());
        model.addAttribute("role", getRole());
        return "auctions";
    }
}
