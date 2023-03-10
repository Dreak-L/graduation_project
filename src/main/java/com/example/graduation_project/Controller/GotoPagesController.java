package com.example.graduation_project.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GotoPagesController {

    @GetMapping("/email-compose.html")
    public String email_compose(){

        return "email-compose";
    }

    @GetMapping("/email-inbox.html")
    public String email_inbox(){
        return "email-inbox";
    }

    @GetMapping("/email-read.html")
    public String email_read(){
        return "email-read";
    }

    @GetMapping("/forms-advanced.html")
    public String forms_advanced(){
        return "forms-advanced";
    }

    @GetMapping("/forms-basic.html")
    public String forms_basic(){
        return "Medical-add";
    }

    @GetMapping("/forms-editor.html")
    public String forms_editor(){
        return "forms-editor";
    }

    @GetMapping("/forms-file-uploads.html")
    public String forms_file_uploads(){
        return "forms-file-uploads";
    }

    @GetMapping("/forms-validation.html")
    public String forms_validation(){

        return "forms-validation";
    }

    @GetMapping("/forms-wizard.html")
    public String forms_wizard(){
        return "forms-wizard";
    }
    @GetMapping("/layouts-boxed.html")
    public String layouts_boxed(){
        return "layouts-boxed";
    }
    @GetMapping("/layouts-condensed.html")
    public String layouts_condensed(){
        return "layouts-condensed";
    }
    @GetMapping("/layouts-dark.html")
    public String layouts_dark(){
        return "layouts-dark";
    }

    @GetMapping("/layouts-dark-sidebar.html")
    public String layouts_dark_sidebar(){
        return "layouts-dark-sidebar";
    }

    @GetMapping("/layouts-horizontal.html")
    public String layouts_horizontal(){
        return "layouts-horizontal";}
}
