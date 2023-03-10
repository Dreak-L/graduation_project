package com.example.graduation_project.Controller;

import com.example.graduation_project.Domain.MedicalRecord;
import com.example.graduation_project.Domain.Patients;
import com.example.graduation_project.Domain.User;
import com.example.graduation_project.Service.DepartmentService;
import com.example.graduation_project.Service.MedicalRocardService;
import com.example.graduation_project.Service.PatientsService;
import com.example.graduation_project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @Autowired
    PatientsService patientsService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    MedicalRocardService medicalRocardService;
    /*
     * 默认来登录页
     * */
    @GetMapping(value = {"/", "/login"})
    public String gotoLogin() {
        return "pages-login";
    }

    @PostMapping("/login")
    public String main(@RequestParam("userEmail") String userEmail,
                       @RequestParam("password") String password,
                       HttpSession session,
                       org.springframework.ui.Model model
                       ) throws InterruptedException, ParseException {
        User user = userService.findByuserEmail(userEmail);
        Patients patient = patientsService.findByPatientEmail(userEmail);

        if (patient != null) {
            System.out.println(user);
            if (userEmail.equals(patient.getPatientEmail()) && password.equals(patient.getPatientPassword())) {
                System.out.println(patient);
                session.setAttribute("loginpatient", patient);
                PatientsController patientsController = new PatientsController();
                session.setAttribute("MedicalNum",medicalRocardService.findAllMedicalRocardNum().size());
                //patientsController.SetPatientState(patient);
                return "redirect:/patient-index.html";
            } else {
                model.addAttribute("msg", "账号密码错误");
                return "pages-login";
            }
        }else if (user != null) {
            if (userEmail.equals(user.getUserEmail()) && password.equals(user.getPassword())){

                //防止表单重复提交，最好的方法：重定向（redirect）
                session.setAttribute("loginuser", user);
                System.out.println(user.getId());
                return "redirect:/findDepartmentAll";
            }else {
                model.addAttribute("msg", "账号密码错误");
                return "pages-login";
            }
        } else {
            model.addAttribute("msg","此账号未注册！");
//            TimeUnit.SECONDS.sleep(3);//秒
            return "pages-login";
        }
    }

    @GetMapping("patient-index.html")
    public String gotopatient(Model model){

        int allDoctors = userService.getAllDoctorsNum();
        model.addAttribute("yuanzhang",userService.findDoctorRole(1));
        model.addAttribute("zhuren",userService.findDoctorRole(2));
        model.addAttribute("kezhang",userService.findDoctorRole(3));
        int departmentAllNum = departmentService.findDepartmentAllNum();
        model.addAttribute("DoctorNum",allDoctors);
        model.addAttribute("departmentAllNum",departmentAllNum);
        model.addAttribute("PatientNum",patientsService.findAllPatientsNum());

        return "patient-index";
    }

    @GetMapping("/pages-register.html")
    public String gotoregister(){
        return "pages-register";
    }

    @GetMapping("/index.html")
    public String gotoIndex(Model model){

        int allDoctors = userService.getAllDoctorsNum();
        model.addAttribute("yuanzhang",userService.findDoctorRole(1));
        model.addAttribute("zhuren",userService.findDoctorRole(2));
        model.addAttribute("kezhang",userService.findDoctorRole(3));
        int departmentAllNum = departmentService.findDepartmentAllNum();
        model.addAttribute("DoctorNum",allDoctors);
        model.addAttribute("departmentAllNum",departmentAllNum);
        model.addAttribute("PatientNum",patientsService.findAllPatientsNum());
        return "index";
    }



    /*
    * 退出，清session
    * */
    @GetMapping("/Logout")
    public String Logout(HttpSession session){

        session.removeAttribute("loginuser");
        return "redirect:/login";
    }


}
