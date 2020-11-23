package com.zrz.mqtt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "tess4j";
    }

    @GetMapping("/mqtt")
    public String mqtt(){
        return "mqtt";
    }

    @GetMapping("/test1")
    public String test1(@RequestParam String bigUrl, @RequestParam String smallUrl, @RequestParam String caseId,
            @RequestParam String fileName,Model model){
        model.addAttribute("bigUrl", bigUrl);
        model.addAttribute("smallUrl", smallUrl);
        model.addAttribute("caseId", caseId);
        model.addAttribute("fileName", fileName);
        return "test1";
    }

    @GetMapping("/preview")
    public String preview(@RequestParam("filePath") String filePath, Model model){
        model.addAttribute("filePath", filePath);
        return "preview";
    }
}
