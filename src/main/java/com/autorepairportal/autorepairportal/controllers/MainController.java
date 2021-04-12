package com.autorepairportal.autorepairportal.controllers;

import com.autorepairportal.autorepairportal.models.days;
import com.autorepairportal.autorepairportal.models.providedservices;
import com.autorepairportal.autorepairportal.models.repairshop;
import com.autorepairportal.autorepairportal.models.workdays;
import com.autorepairportal.autorepairportal.repository.daysRepository;
import com.autorepairportal.autorepairportal.repository.providedservicesRepository;
import com.autorepairportal.autorepairportal.repository.repairshopRepository;
import com.autorepairportal.autorepairportal.repository.workdaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebEngineContext;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private providedservicesRepository servicesRepos;

    @Autowired
    private repairshopRepository repairshopRepos;

    @Autowired
    private workdaysRepository workdaysRepos;

    @Autowired
    private daysRepository daysRepos;

    @GetMapping("/")
    public String home(Model model) {
        List<providedservices> serv = servicesRepos.findAll();
        model.addAttribute("services", serv);
        return "index";
    }

    @GetMapping("service/{ID}")
    public String service(Model model, @PathVariable Long ID) {

        providedservices serv = servicesRepos.getOne(ID);
        repairshop shop = repairshopRepos.getOne(serv.getID_RepairShop());
        List<workdays> work = workdaysRepos.findByIDRepairShop(serv.getID_RepairShop());
        List<days> d = daysRepos.findAll();

        String str = work.stream()
                .map(workdays -> daysRepos.findById(workdays.getID_Days()))
                .map(op -> op.get().getName())
                .collect(Collectors.joining(", ", "", ""));

        model.addAttribute("service", serv);
        model.addAttribute("shop", shop);
        model.addAttribute("str", str);


        return "item";
    }

    @GetMapping("reg")
    public String register(Model model) {
        return "reg";
    }

    @GetMapping("/shinomontash")
    public String shinomontash(Model model) {
        long id = 1;
        List<providedservices> serv = servicesRepos.findByIDServices(id);
        model.addAttribute("services", serv);
        return "index";
    }

    @GetMapping("/zamenamasla")
    public String zamenamasla(Model model) {
        long id = 2;
        List<providedservices> serv = servicesRepos.findByIDServices(id);
        model.addAttribute("services", serv);
        return "index";
    }

    @GetMapping("/remontdvigatelya")
    public String remontdvigatelya(Model model) {
        long id = 3;
        List<providedservices> serv = servicesRepos.findByIDServices(id);
        model.addAttribute("services", serv);
        return "index";
    }

    @PostMapping("/reg")
    public String RegPostAdd(@RequestParam String Name,
                             @RequestParam String Address,
                             @RequestParam String PhoneNumber,
                             @RequestParam (defaultValue = "false") boolean Shino,
                             @RequestParam String CostShino,
                             @RequestParam String NameShino,
                             @RequestParam String DescriptionShino,
                             @RequestParam (defaultValue = "false") boolean Zamena,
                             @RequestParam String CostZamena,
                             @RequestParam String NameZamena,
                             @RequestParam String DescriptionZamena,
                             @RequestParam (defaultValue = "false") boolean Remont,
                             @RequestParam String CostRemont,
                             @RequestParam String NameRemont,
                             @RequestParam String DescriptionRemont,
                             @RequestParam (defaultValue = "false") boolean Monday,
                             @RequestParam (defaultValue = "false") boolean Tuesday,
                             @RequestParam (defaultValue = "false") boolean Wednesday,
                             @RequestParam (defaultValue = "false") boolean Thursday,
                             @RequestParam (defaultValue = "false") boolean Friday,
                             @RequestParam (defaultValue = "false") boolean Saturday,
                             @RequestParam (defaultValue = "false") boolean Sunday,
                             Model model) {

        repairshop shop = new repairshop(Name, Address, PhoneNumber);
        repairshop shop_new = repairshopRepos.save(shop);

        if (Monday)
            workdaysRepos.save(new workdays((long) 1, shop_new.getID()));
        if (Tuesday)
            workdaysRepos.save(new workdays((long) 2, shop_new.getID()));
        if (Wednesday)
            workdaysRepos.save(new workdays((long) 3, shop_new.getID()));
        if (Thursday)
            workdaysRepos.save(new workdays((long) 4, shop_new.getID()));
        if (Friday)
            workdaysRepos.save(new workdays((long) 5, shop_new.getID()));
        if (Saturday)
            workdaysRepos.save(new workdays((long) 6, shop_new.getID()));
        if (Sunday)
            workdaysRepos.save(new workdays((long) 7, shop_new.getID()));

        if (Shino)
            servicesRepos.save(new providedservices(
                    NameShino,
                    CostShino,
                    DescriptionShino,
                    shop_new.getID(),
                    (long) 1));

        if (Zamena)
            servicesRepos.save(new providedservices(
                    NameZamena,
                    CostZamena,
                    DescriptionZamena,
                    shop_new.getID(),
                    (long) 2));

        if (Remont)
            servicesRepos.save(new providedservices(
                    NameRemont,
                    CostRemont,
                    DescriptionRemont,
                    shop_new.getID(),
                    (long) 3));

        return "redirect:/";
    }
}
