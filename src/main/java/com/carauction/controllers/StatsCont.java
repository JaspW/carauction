package com.carauction.controllers;

import com.carauction.controllers.Main.Main;
import com.carauction.models.enums.AuctionStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatsCont extends Main {

    @GetMapping("/stats")
    public String sales(Model model) {
        model.addAttribute("role", getRole());

        AuctionStatus[] statuses = AuctionStatus.values();

        String[] statusString = new String[statuses.length];
        int[] statusInt = new int[statuses.length];

        for (int i = 0; i < statuses.length; i++) {
            statusString[i] = statuses[i].getName();
            statusInt[i] = auctionRepo.findAllByStatus(statuses[i]).size();
        }

        model.addAttribute("statusString", statusString);
        model.addAttribute("statusInt", statusInt);

        return "stats";
    }
}
