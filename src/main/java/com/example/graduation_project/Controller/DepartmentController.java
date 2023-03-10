package com.example.graduation_project.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.DepartBelong;
import com.example.graduation_project.Domain.Department;
import com.example.graduation_project.Domain.User;
import com.example.graduation_project.Service.DepartmentService;
import com.example.graduation_project.Service.MedicalRocardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    MedicalRocardService medicalRocardService;

    @GetMapping("/findDepartmentAll")
    public String findDepartmentall(HttpSession session){
        List<Department> departs = departmentService.findDepartmentAll();
        List<DepartBelong> departBelong = departmentService.findDepartBelong();
//        String[] depart = new String[departs.size()];
        String[] departdelongs = new String[departBelong.size()];
        String[][] Departsinfor  = new String[departBelong.size()][departs.size()];
        for (int i = 0; i <departBelong.size() ; i++) {
            DepartBelong departBelong1 = departBelong.get(i);
            departdelongs[i] = departBelong1.getDepartbelongname();

            for (int j = 0; j < departs.size(); j++) {
                Department department = departs.get(j);
                if (departBelong1.getId() == department.getDepartbelong())
                    Departsinfor[i][j] = department.getDepartname();
            }
        }
        session.setAttribute("Departsinfor",Departsinfor);
        session.setAttribute("departdelongs",departdelongs);
        session.setAttribute("MedicalNum",medicalRocardService.findAllMedicalRocardNum().size());
        return "redirect:/findAllPatientAnduserState";
    }

    @GetMapping("/findalldepartment")
    public String findalldepartment(@RequestParam(value = "pn") Integer pn,
                                    @RequestParam(value = "previous",defaultValue = "false") Boolean previous,
                                    @RequestParam(value = "next",defaultValue = "false") Boolean next,
                                    Model model){
        Page<Department> findalldepartment = departmentService.findalldepartment(pn);
        if (previous==true && pn>1){
            pn--;
            return "redirect:/findalldepartment?pn="+pn;
        }
        if (next==true && pn<findalldepartment.getPages()){
            pn++;
            return "redirect:/findalldepartment?pn="+pn;
        }
        model.addAttribute("alldepartment",findalldepartment);
        return "department-all";
    }

    @GetMapping("/Depart-add.html")
    public ModelAndView Depart_add(){
        List<DepartBelong> departBelong = departmentService.findDepartBelong();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("departBelong",departBelong);
        modelAndView.setViewName("Depart-add");
        return modelAndView;
    }

    @PostMapping("/addDepart")
    public String addDepart(Department department){
        List<DepartBelong> departBelong = departmentService.findDepartBelong();
        for (DepartBelong belong : departBelong) {
            if (department.getDepartbelong()==belong.getId())
            {
                department.setDepartbelongname(belong.getDepartbelongname());
                break;
            }
        }
        int i = departmentService.addDepart(department);
        if (i>0)
            return "redirect:/findDepartmentAll";
        else
            return "index";
    }

    @PostMapping("/updateDepart")
    public String updateDepart(Department department){
        System.out.println("99");
        List<DepartBelong> departBelong = departmentService.findDepartBelong();
        for (DepartBelong belong : departBelong) {
            if (department.getDepartbelong()==belong.getId()) {
                department.setDepartbelongname(belong.getDepartbelongname());
                break;
            }
        }
        int i = departmentService.updateDepart(department);
        if (i>0)
            return "redirect:/findalldepartment?pn="+1;
        else
            return null;
    }

    @GetMapping("/updateDepartUI")
    public ModelAndView Department_updateUI(@RequestParam("id") Integer id){
        Department departByid = departmentService.findDepartByid(id);
        List<DepartBelong> departBelong = departmentService.findDepartBelong();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("departByid",departByid);
        modelAndView.addObject("departBelong",departBelong);
        modelAndView.setViewName("Department-update");
        return modelAndView;
    }

    @GetMapping("/deleteDepart")
    public String deleteDepart(@RequestParam("id") Integer id){
        int i = departmentService.deleteDepartByid(id);
        if (i>0)
            return "redirect:/findalldepartment?pn="+1;
        else
            return null;

    }
}
