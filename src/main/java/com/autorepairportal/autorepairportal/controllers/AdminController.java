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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private providedservicesRepository servicesRepos;

    @Autowired
    private repairshopRepository repairshopRepos;

    @Autowired
    private workdaysRepository workdaysRepos;

    @Autowired
    private daysRepository daysRepos;

    @GetMapping
    public String adminIndex(Model model)
    {
        Iterable<providedservices> serv = servicesRepos.findAll();
        model.addAttribute("services", serv);
        return "admin";
    }

    @GetMapping("/shinomontash")
    public String shinomontash(Model model) {
        long id = 1;
        List<providedservices> serv = servicesRepos.findByIDServices(id);
        model.addAttribute("services", serv);
        return "admin";
    }

    @GetMapping("/zamenamasla")
    public String zamenamasla(Model model) {
        long id = 2;
        List<providedservices> serv = servicesRepos.findByIDServices(id);
        model.addAttribute("services", serv);
        return "admin";
    }

    @GetMapping("/remontdvigatelya")
    public String remontdvigatelya(Model model) {
        long id = 3;
        List<providedservices> serv = servicesRepos.findByIDServices(id);
        model.addAttribute("services", serv);
        return "admin";
    }

    @GetMapping("/reg")
    public String register(Model model) {
        return "adminreg";
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

        return "redirect:/admin";
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


        return "adminitem";
    }

    @GetMapping("remove/{ID}")
    public String remove(Model model, @PathVariable Long ID) {

        servicesRepos.deleteById(ID);

        return "redirect:/admin";
    }

    @GetMapping("/edit/{ID}")
    public String edit(Model model, @PathVariable Long ID) {

        repairshop shop = repairshopRepos.findById(ID).get();

        model.addAttribute("shop", shop);
        List<providedservices> serv = servicesRepos.findByIDShop(ID);

        List<providedservices> serv1 = serv.stream().filter(providedservices -> providedservices.getID_Services() == 1).collect(Collectors.toList());
        List<providedservices> serv2 = serv.stream().filter(providedservices -> providedservices.getID_Services() == 2).collect(Collectors.toList());
        List<providedservices> serv3 = serv.stream().filter(providedservices -> providedservices.getID_Services() == 3).collect(Collectors.toList());

        if (!serv1.isEmpty())
        {
            model.addAttribute("shino", "true");
            model.addAttribute("shinoserv", serv1.get(0));
        }
        else
        {
            model.addAttribute("shino", "false");
            model.addAttribute("shinoserv", new providedservices("", "", "", (long) 0, (long) 0));
        }

        if (!serv2.isEmpty())
        {
            model.addAttribute("zamena", "true");
            model.addAttribute("zamenaserv", serv2.get(0));
        }
        else
        {
            model.addAttribute("zamena", "false");
            model.addAttribute("zamenaserv", new providedservices("", "", "", (long) 0, (long) 0));
        }

        if (!serv3.isEmpty())
        {
            model.addAttribute("dvig", "true");
            model.addAttribute("dvigserv", serv3.get(0));
        }
        else
        {
            model.addAttribute("dvig", "false");
            model.addAttribute("dvigserv", new providedservices("", "", "", (long) 0, (long) 0));
        }

        List<workdays> work = workdaysRepos.findByIDRepairShop(ID);

        for (int i = 0; i < work.size(); i++)
        {
            model.addAttribute("day" + work.get(i).getID_Days().toString(), "true");
        }

        return "edit";
    }

    @PostMapping("/edit/{ID}")
    public String editaccept(@RequestParam String Name,
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
                             Model model,
                             @PathVariable Long ID) {

        repairshop shop = repairshopRepos.findById(ID).get();
        shop.setName(Name);
        shop.setAddress(Address);
        shop.setPhoneNumber(PhoneNumber);

        repairshopRepos.save(shop);

        List<workdays> work = workdaysRepos.findByIDRepairShop(shop.getID());

        List<workdays> day = work.stream().filter(workdays -> workdays.getID_Days() == 1).collect(Collectors.toList());

        if (day.isEmpty())
        {
            if (Monday) {
                workdaysRepos.save(new workdays((long) 1, shop.getID()));
            }
        }
        else
        if (!Monday) {
            workdaysRepos.deleteById(day.get(0).getID());
        }

        day = work.stream().filter(workdays -> workdays.getID_Days() == 2).collect(Collectors.toList());

        if (day.isEmpty())
        {
            if (Tuesday) {
                workdaysRepos.save(new workdays((long) 2, shop.getID()));
            }
        }
        else
        if (!Tuesday) {
            workdaysRepos.deleteById(day.get(0).getID());
        }

        day = work.stream().filter(workdays -> workdays.getID_Days() == 3).collect(Collectors.toList());

        if (day.isEmpty())
        {
            if (Wednesday) {
                workdaysRepos.save(new workdays((long) 3, shop.getID()));
            }
        }
        else
        if (!Wednesday) {
            workdaysRepos.deleteById(day.get(0).getID());
        }

        day = work.stream().filter(workdays -> workdays.getID_Days() == 4).collect(Collectors.toList());

        if (day.isEmpty())
        {
            if (Thursday) {
                workdaysRepos.save(new workdays((long) 4, shop.getID()));
            }
        }
        else
        if (!Thursday) {
            workdaysRepos.deleteById(day.get(0).getID());
        }

        day = work.stream().filter(workdays -> workdays.getID_Days() == 5).collect(Collectors.toList());

        if (day.isEmpty())
        {
            if (Friday) {
                workdaysRepos.save(new workdays((long) 5, shop.getID()));
            }
        }
        else
        if (!Friday) {
            workdaysRepos.deleteById(day.get(0).getID());
        }

        day = work.stream().filter(workdays -> workdays.getID_Days() == 6).collect(Collectors.toList());

        if (day.isEmpty())
        {
            if (Saturday) {
                workdaysRepos.save(new workdays((long) 6, shop.getID()));
            }
        }
        else
        if (!Saturday) {
            workdaysRepos.deleteById(day.get(0).getID());
        }

        day = work.stream().filter(workdays -> workdays.getID_Days() == 7).collect(Collectors.toList());

        if (day.isEmpty())
        {
            if (Sunday) {
                workdaysRepos.save(new workdays((long) 7, shop.getID()));
            }
        }
        else
        if (!Sunday) {
            workdaysRepos.deleteById(day.get(0).getID());
        }

        List<providedservices> serv = servicesRepos.findByIDShop(ID);

        List<providedservices> otserv = serv.stream().filter(providedservices -> providedservices.getID_Services() == 1).collect(Collectors.toList());

        if (otserv.isEmpty()) {
            if (Shino)
                servicesRepos.save(new providedservices(
                        NameShino,
                        CostShino,
                        DescriptionShino,
                        shop.getID(),
                        (long) 1));
        }
        else
            if (Shino)
            {
                providedservices shinoserv = otserv.get(0);
                shinoserv.setCost(CostShino);
                shinoserv.setDescription(DescriptionShino);
                shinoserv.setName(NameShino);
                servicesRepos.save(shinoserv);
            }
            else
            {
                servicesRepos.deleteById(otserv.get(0).getID());
            }

        otserv = serv.stream().filter(providedservices -> providedservices.getID_Services() == 2).collect(Collectors.toList());

        if (otserv.isEmpty()) {
            if (Zamena)
                servicesRepos.save(new providedservices(
                        NameZamena,
                        CostZamena,
                        DescriptionZamena,
                        shop.getID(),
                        (long) 2));
        }
        else
        if (Zamena)
        {
            providedservices zamenaserv = otserv.get(0);
            zamenaserv.setCost(CostZamena);
            zamenaserv.setDescription(DescriptionZamena);
            zamenaserv.setName(NameZamena);
            servicesRepos.save(zamenaserv);
        }
        else
        {
            servicesRepos.deleteById(otserv.get(0).getID());
        }

        otserv = serv.stream().filter(providedservices -> providedservices.getID_Services() == 3).collect(Collectors.toList());

        if (otserv.isEmpty()) {
            if (Remont)
                servicesRepos.save(new providedservices(
                        NameRemont,
                        CostRemont,
                        DescriptionRemont,
                        shop.getID(),
                        (long) 3));
        }
        else
        if (Remont)
        {
            providedservices remontserv = otserv.get(0);
            remontserv.setCost(CostRemont);
            remontserv.setDescription(DescriptionRemont);
            remontserv.setName(NameRemont);
            servicesRepos.save(remontserv);
        }
        else
        {
            servicesRepos.deleteById(otserv.get(0).getID());
        }

        return "redirect:/admin";
    }

}
