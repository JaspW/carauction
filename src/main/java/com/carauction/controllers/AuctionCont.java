package com.carauction.controllers;

import com.carauction.controllers.Main.Main;
import com.carauction.models.Auction;
import com.carauction.models.Comments;
import com.carauction.models.enums.AuctionStatus;
import com.carauction.models.enums.Role;
import com.carauction.models.enums.Transmission;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/auctions")
public class AuctionCont extends Main {
    @GetMapping("/{id}")
    public String auction(@PathVariable Long id, Model model) {
        if (!auctionRepo.existsById(id)) return "redirect:/catalog";
        model.addAttribute("auction", auctionRepo.getReferenceById(id));
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
        return "auction";
    }

    @PostMapping("/bet/{id}")
    public String bet(@PathVariable Long id, @RequestParam int price) {
        Auction auction = auctionRepo.getReferenceById(id);
        auction.setOwner(getUser());
        auction.setPrice(price);
        auctionRepo.save(auction);
        return "redirect:/auctions/{id}";
    }

    @GetMapping("/active/{id}")
    public String active(@PathVariable Long id) {
        Auction auction = auctionRepo.getReferenceById(id);
        auction.setStatus(AuctionStatus.ACTIVE);
        auctionRepo.save(auction);
        return "redirect:/auctions/{id}";
    }


    @GetMapping("/end/{id}")
    public String end(@PathVariable Long id) {
        Auction auction = auctionRepo.getReferenceById(id);
        auction.setStatus(AuctionStatus.END);
        auctionRepo.save(auction);
        return "redirect:/auctions/{id}";
    }

    @PostMapping("/comment/add/{id}")
    public String comment(@PathVariable Long id, @RequestParam String date, @RequestParam String comment) {
        commentRepo.save(new Comments(getUser().getUsername(), date, comment, auctionRepo.getReferenceById(id)));
        return "redirect:/auctions/{id}";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("transmissions", Transmission.values());
        return "auction_add";
    }

    @PostMapping("/add")
    public String add(
            Model model, @RequestParam int begin, @RequestParam String name, @RequestParam MultipartFile file,
            @RequestParam MultipartFile poster, @RequestParam MultipartFile[] screenshots, @RequestParam String contact,
            @RequestParam String date, @RequestParam int power, @RequestParam int price, @RequestParam int mileage,
            @RequestParam Transmission transmission, @RequestParam String description,
            @RequestParam String dateAndTimeStart, @RequestParam String dateAndTimeEnd
    ) {
        Auction auction;
        try {
            String result_poster = "";
            if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                result_poster = UUID.randomUUID().toString() + "_" + poster.getOriginalFilename();
                poster.transferTo(new File(uploadPath + "/" + result_poster));
            }

            String[] result_screenshots = new String[0];
            if (screenshots != null && !Objects.requireNonNull(screenshots[0].getOriginalFilename()).isEmpty()) {
                String result_screenshot;
                result_screenshots = new String[screenshots.length];
                for (int i = 0; i < result_screenshots.length; i++) {
                    result_screenshot = UUID.randomUUID().toString() + "_" + screenshots[i].getOriginalFilename();
                    screenshots[i].transferTo(new File(uploadPath + "/" + result_screenshot));
                    result_screenshots[i] = result_screenshot;
                }
            }

            String result_file = "";
            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                result_file = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + result_poster));
            }

            auction = auctionRepo.save(new Auction(name, contact, date, result_poster, result_screenshots, result_file, power, mileage, price, begin, dateAndTimeStart, dateAndTimeEnd, description, transmission));

            if (getUser().getRole() == Role.USER) {
                auction.setStatus(AuctionStatus.WAITING);
            }

        } catch (Exception e) {
            model.addAttribute("role", getRole());
            model.addAttribute("message", "Некорректные данные!");
            model.addAttribute("transmissions", Transmission.values());
            return "auction_add";
        }
        return "redirect:/auctions/" + auction.getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(value = "id") Long id, Model model) {
        if (!auctionRepo.existsById(id)) return "redirect:/catalog";
        model.addAttribute("auction", auctionRepo.getReferenceById(id));
        model.addAttribute("role", getRole());
        model.addAttribute("transmissions", Transmission.values());
        return "auction_edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(
            Model model, @RequestParam int begin, @RequestParam String name, @RequestParam MultipartFile file,
            @RequestParam MultipartFile poster, @RequestParam MultipartFile[] screenshots, @RequestParam String contact,
            @RequestParam String date, @RequestParam int power, @RequestParam int price, @RequestParam int mileage,
            @RequestParam Transmission transmission, @RequestParam String description,
            @RequestParam String dateAndTimeStart, @RequestParam String dateAndTimeEnd, @PathVariable Long id
    ) {
        try {
            Auction auction = auctionRepo.getReferenceById(id);

            auction.setDescription(description);
            auction.setName(name);
            auction.setContact(contact);
            auction.setPower(power);
            auction.setPrice(price);
            auction.setBegin(begin);
            auction.setMileage(mileage);
            auction.setDate(date);
            auction.setTransmission(transmission);
            auction.setDateAndTimeStart(dateAndTimeStart);
            auction.setDateAndTimeEnd(dateAndTimeEnd);

            if (poster != null && !Objects.requireNonNull(poster.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result_poster = UUID.randomUUID().toString() + "_" + poster.getOriginalFilename();
                poster.transferTo(new File(uploadPath + "/" + result_poster));
                auction.setPoster(result_poster);
            }

            if (screenshots != null && !Objects.requireNonNull(screenshots[0].getOriginalFilename()).isEmpty()) {
                String result_screenshot;
                String[] result_screenshots = new String[screenshots.length];
                for (int i = 0; i < result_screenshots.length; i++) {
                    result_screenshot = UUID.randomUUID().toString() + "_" + screenshots[i].getOriginalFilename();
                    screenshots[i].transferTo(new File(uploadPath + "/" + result_screenshot));
                    result_screenshots[i] = result_screenshot;
                }
                auction.setScreenshots(result_screenshots);
            }

            if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result_file = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + result_file));
                auction.setFile(result_file);
            }

            auctionRepo.save(auction);
        } catch (Exception e) {
            model.addAttribute("auction", auctionRepo.getReferenceById(id));
            model.addAttribute("role", getRole());
            model.addAttribute("message", "Некорректные данные!");
            model.addAttribute("transmissions", Transmission.values());
            return "auction_edit";
        }
        return "redirect:/auctions/{id}/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        auctionRepo.deleteById(id);
        return "redirect:/catalog/all";
    }
}
