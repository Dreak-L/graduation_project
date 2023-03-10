package com.example.graduation_project.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.Role;
import com.example.graduation_project.Domain.User;
import com.example.graduation_project.Service.RolesService;
import com.example.graduation_project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class RolesController {
    @Autowired
    RolesService rolesService;
    @Autowired
    UserService userService;

    @GetMapping("/getAllRole")
    public String getAllRole(@RequestParam("pn") Integer pn, Model model){
        Page<Role> allRole = rolesService.findAllRole(pn);
        model.addAttribute("allRole",allRole);
        return "allRole";
    }

    @PostMapping("/addRole")
    public String addRole(Role role){
        int i = rolesService.addRole(role);
        if (i>0)
            return "redirect:/getAllRole";
        else {
            System.out.println("插入失败");
            return "index";
        }
    }

    @GetMapping("/findRoleName")
    public String findRoleName(HttpSession session){
        List<Role> roleName = rolesService.findRoleName();
        String[] RoleName = new String[roleName.size()];
        User loginuser = (User) session.getAttribute("loginuser");
        switch (loginuser.getUserRoleid())
        {
            case 1:
                for (int i = 0; i < roleName.size(); i++) {
                    Role role = roleName.get(i);
                    RoleName[i] = role.getRolename();
                }
                break;
            case 2:
            case 3:
                for (int i = 0; i < roleName.size(); i++) {
                    Role role = roleName.get(i);
                    if (role.getId()>3)
                        RoleName[i] = role.getRolename();
                }
                break;
            case 4:
            case 5:
                for (int i = 0; i < roleName.size(); i++) {
                    Role role = roleName.get(i);
                    if (role.getId()>5)
                        RoleName[i] = role.getRolename();
                }
                break;
            case 6:
            case 7:
                for (int i = 0; i < roleName.size(); i++) {
                    Role role = roleName.get(i);
                    if (role.getId()>7)
                        RoleName[i] = role.getRolename();
                }
                break;
            case 8:
                break;
        }
        session.setAttribute("RoleName",RoleName);
        return "redirect:/index.html";
    }
    @GetMapping("/findDepartUserByNameRoleUI")
    public String findDepartUserToRole(@RequestParam("departname") String departname,
                                       @RequestParam(value = "pn") Integer pn, Model model,
                                       @RequestParam(value = "previous",defaultValue = "false") Boolean previous,
                                       @RequestParam(value = "next",defaultValue = "false") Boolean next,
                                       HttpSession session) throws UnsupportedEncodingException {
        User loginuser =(User) session.getAttribute("loginuser");
        Page<User> departUserByNamePage = userService.findDepartUserByName(departname, pn,loginuser);
        String s = new String(departname.getBytes("utf-8"), "iso-8859-1");
        if (previous==true && pn>1){
            pn--;
            return "redirect:/findDepartUserByNameRoleUI?pn="+pn+"&departname="+s;
        }
        if (next==true && pn<departUserByNamePage.getPages())
        {
            int current =(int) departUserByNamePage.getCurrent();
            pn=current+1;
            return "redirect:/findDepartUserByNameRoleUI?pn="+pn+"&departname="+s;
        }
        model.addAttribute("departUserPageToRole",departUserByNamePage);
        model.addAttribute("nowdepartname",departname);
        return "findDepartUserToRole";
    }

    @GetMapping("/updateDoctorBydepartToRoleUI")
    public ModelAndView updateDoctorBydepartToRoleUI(@RequestParam("id") Integer id,
                                                     @RequestParam("mode") String mode, HttpSession session){
        User loginuser = (User) session.getAttribute("loginuser");
        User doctorByid = userService.findDoctorByid(id);
        ModelAndView modelAndView = new ModelAndView();
        List<Role> allRoleList = rolesService.findLoginRoleList(loginuser);
        modelAndView.addObject("allRoleList",allRoleList);
        modelAndView.addObject("doctorByid",doctorByid);
        modelAndView.setViewName("updateDoctorBydepartToRoleUI");
        session.setAttribute("mode",mode);
        return modelAndView;
    }

    @PostMapping("/updateDoctorBydepartToRole")
    public  String updateDoctorBydepartToRole(User user,HttpSession session) throws UnsupportedEncodingException {

        User loginuser = (User) session.getAttribute("loginuser");
        List<Role> loginRoleList = rolesService.findLoginRoleList(loginuser);
        for (Role role : loginRoleList) {
            if (user.getUserRoleid()==role.getId()){
                user.setUserRolename(role.getRolename());
                break;
            }
        }
        int i = userService.updateDoctor(user);
        String mode = (String) session.getAttribute("mode");
        String s = new String(mode.getBytes("utf-8"), "iso-8859-1");
        if (i>0)
            return "redirect:/findDepartUserByNameRoleUI?departname="+s+"&pn=1";
        else return null;
    }
}
