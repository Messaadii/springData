package com.sip.ams_vermegdata.controllers;

import com.sip.ams_vermegdata.entities.Provider;
import com.sip.ams_vermegdata.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/provider/")
public class ProviderController {

    private final ProviderRepository providerRepository;

    @Autowired // Ioc consist to use Autowired in spring to instance objects that implements interfaces passed in parameters
    public ProviderController(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @GetMapping("list")
    //@ResponseBody
    public String listProviders(Model model) {
        List<Provider> providers = (List<Provider>) providerRepository.findAll();
        model.addAttribute("providers", providers.size() == 0 ? null : providers);
        System.out.println(providerRepository.findAll());
        return "provider/listProviders";
    }

    @GetMapping("add")
    public String showAddProviderForm(Model model) {
        Provider provider = new Provider();// object dont la valeur des attributs par defaut
        model.addAttribute("provider", provider);
        return "provider/addProvider";
    }

    @PostMapping("add")
    public String addProvider(@Valid Provider provider,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "provider/addProvider";
        }
        providerRepository.save(provider);
        return "redirect:list";
    }

    @GetMapping("delete/{id}")
    public String deleteProvider(@PathVariable("id") long id, Model model) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + id));
        providerRepository.delete(provider);
        return "redirect:../list";
    }


    @GetMapping("edit/{id}")
    public String showProviderFormToUpdate(@PathVariable("id") long id, Model model) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + id));
        model.addAttribute("provider", provider);
        return "provider/updateProvider";
    }

    @PostMapping("update")
    public String updateProvider(@Valid Provider provider) {
        providerRepository.save(provider);
        return"redirect:list";
    }
}