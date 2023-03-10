package com.example.graduation_project.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.Department;
import com.example.graduation_project.Domain.Role;
import com.example.graduation_project.Domain.States;
import com.example.graduation_project.Domain.User;
import com.example.graduation_project.Service.DepartmentService;
import com.example.graduation_project.Service.RolesService;
import com.example.graduation_project.Service.StatesService;
import com.example.graduation_project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.nio.cs.US_ASCII;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    StatesService statesService;
    @Autowired
    RolesService rolesService;

    @PostMapping("/userRegister")
    public String userRegister(User user,
                               @RequestParam("filest") Integer st,
                               @RequestParam("departname") String departname,
                               @RequestParam("file")MultipartFile file, HttpServletRequest request) throws UnsupportedEncodingException {
        user.setUserStateId(2);
        user.setUserStatename("正常");
        List<Department> departmentAll = departmentService.findDepartmentAll();
        for (Department department : departmentAll) {
            if (department.getId()==user.getUserDepart()){
                user.setUserDepartname(department.getDepartname());
                user.setUserDepartbelong(department.getDepartbelong());
                user.setUserDepartbelongname(department.getDepartbelongname());
                break;
            }
        }
        List<Role> roleName = rolesService.findRoleName();
        for (Role role : roleName) {
            if (user.getUserRoleid()==role.getId()){
                user.setUserRolename(role.getRolename());
                break;
            }
        }
        String[] doctorupload=new String[2];
        if (st==1) {
            user.setUserPhoto(null);
            user.setUserPhotoRequest(null);
        }
        else if (st==2){
            doctorupload = new FileUploadController().Doctorupload(file, request);
            user.setUserPhoto(doctorupload[0]);
            user.setUserPhotoRequest(doctorupload[1]);
        }
        int i = userService.userRegister(user);
        String s = new String(departname.getBytes("utf-8"), "iso-8859-1");
        if (i>0)
            return "redirect:/findDepartUserByName?departname="+s+"&pn=1";
        else
            return null;
    }

    @GetMapping("/TouserRegister")
    public String TouserRegister(Model model,HttpSession session){
        User loginuser = (User) session.getAttribute("loginuser");
        List<Department> departmentByRole = departmentService.findDepartmentByRole(loginuser);
        List<Role> loginRoleList = rolesService.findLoginRoleList(loginuser);
        model.addAttribute("departmentByRole",departmentByRole);
        model.addAttribute("loginRoleList",loginRoleList);
        return "TouserRegister";
    }
    @GetMapping("/getAllDoctors")
    public String getAllDoctors(@RequestParam(value = "pn") Integer pn, Model model,
                                @RequestParam(value = "previous",defaultValue = "false") Boolean previous,
                                @RequestParam(value = "next",defaultValue = "false") Boolean next){
        Page<User> userPage = userService.userRecordPage(pn);
        if (previous==true && pn>1){
            pn--;
            return "redirect:/getAllDoctors?pn="+pn;
        }
        if (next==true && pn<userPage.getPages())
        {
            int current =(int) userPage.getCurrent();
            System.out.println(userPage.getCurrent());
            pn=current+1;
            return "redirect:/getAllDoctors?pn="+pn;
        }
        List<User> doctors = userService.getAllDoctors();
//        model.addAttribute("doctors",doctors);

        model.addAttribute("userPage",userPage);
        //获取表中所有信息
        List<User> records = userPage.getRecords();
        //共多少条
        long total = userPage.getTotal();
        //第几页
        long current = userPage.getCurrent();
        //共多少页
        long pages = userPage.getPages();
        return "doctor-table";
    }
    /*
    * 获取某个科室所有医生的信息
    * */
    @GetMapping("/findDepartUserByName")
    public String findDepartUserByName(@RequestParam("departname") String departname,
                                   @RequestParam(value = "pn") Integer pn, Model model,
                                       @RequestParam(value = "previous",defaultValue = "false") Boolean previous,
                                       @RequestParam(value = "next",defaultValue = "false") Boolean next,
                                       HttpSession session) throws UnsupportedEncodingException {
        User loginuser = (User) session.getAttribute("loginuser");
        Page<User> departUserByNamePage = userService.findDepartUserByName(departname, pn, loginuser);
        String s = new String(departname.getBytes("utf-8"), "iso-8859-1");
        if (previous==true && pn>1){
            pn--;
            return "redirect:/findDepartUserByName?pn="+pn+"&departname="+s;
        }
        if (next==true && pn<departUserByNamePage.getPages())
        {
            int current =(int) departUserByNamePage.getCurrent();
            pn=current+1;
            return "redirect:/findDepartUserByName?pn="+pn+"&departname="+s;
        }
        model.addAttribute("departUserPage",departUserByNamePage);
        model.addAttribute("nowdepartname",departname);
        return "depart-table";
    }

    @GetMapping("/findDoctorByState")
    public String findDoctorByState(@RequestParam("StateName") String StateName,
                                           @RequestParam(value = "pn") Integer pn,
                                          @RequestParam(value = "previous",defaultValue = "false") Boolean previous,
                                          @RequestParam(value = "next",defaultValue = "false") Boolean next,
                                          Model model,
                                          HttpSession session
                                           ) throws UnsupportedEncodingException {
        User loginuser = (User) session.getAttribute("loginuser");
        Page<User> doctorByState = userService.findDoctorByState(StateName,loginuser, pn);
        String s = new String(StateName.getBytes("utf-8"), "iso-8859-1");
        if (previous==true && pn>1){
            pn--;
            return "redirect:/findDoctorByState?pn="+pn+"&StateName="+s;
        }
        if (next==true && pn<doctorByState.getPages())
        {
            int current =(int) doctorByState.getCurrent();
            pn=current+1;
            return "redirect:/findDoctorByState?pn="+pn+"&StateName="+s;
        }
        model.addAttribute("doctorByState",doctorByState);
        model.addAttribute("nowDoctorState",StateName);
        return "Doctor-State";
    }

    @GetMapping("/updateDoctorBydepartUI")
    public ModelAndView updateDoctorBydepartUI(@RequestParam("id") Integer id,
                                               @RequestParam("mode") String mode,HttpSession
                                               session){
        ModelAndView modelAndView = new ModelAndView();
        List<Department> department = departmentService.findDepartmentAll();
        User doctorByid = userService.findDoctorByid(id);
        modelAndView.addObject("doctorByid",doctorByid);
        modelAndView.addObject("department",department);
        if (doctorByid.getUserPhotoRequest()!=null)
            session.setAttribute("filepath",doctorByid.getUserPhotoRequest());
        else if (doctorByid.getUserPhotoRequest()==null) {
            session.setAttribute("filepath", 1);
        }
        session.setAttribute("mode",mode);
        modelAndView.setViewName("Doctor-update");
        return modelAndView;
    }


    @PostMapping("/updateDoctorBydepart")
    public String updateDoctorBydepart(User user,HttpServletRequest request,
                                       @RequestParam("file") MultipartFile file,HttpSession session) throws UnsupportedEncodingException {
        List<Department> departmentAll = departmentService.findDepartmentAll();
        List<States> allState = statesService.findAllState();
        for (Department department : departmentAll) {
            if (user.getUserDepart()==department.getId()){
                user.setUserDepartname(department.getDepartname());
                user.setUserDepartbelong(department.getDepartbelong());
                break;
            }
        }
        for (States states : allState) {
            if (user.getUserStateId()==states.getStateid()){
                user.setUserStatename(states.getStatename());
                break;
            }
        }
        String[] doctorfilepaths= new String[2];
        if (file!=null){
            doctorfilepaths=new FileUploadController().Doctorupload(file,request);
            user.setUserPhoto(doctorfilepaths[0]);
            user.setUserPhotoRequest(doctorfilepaths[1]);
        }
        String mode = (String) session.getAttribute("mode");
        String s = new String(mode.getBytes("utf-8"), "iso-8859-1");
        int i = userService.updateDoctorBydepartway(user);
        if (i>0)
            return "redirect:/findDepartUserByName?pn="+1+"&departname="+s;
        else
            return null;
    }

    @GetMapping("/pages-profile.html")
    public String pages_profile(){
        return "pages-profile";
    }

    @GetMapping("/updateDoctorUI")
    public ModelAndView updateDoctorUI(@RequestParam("id") Integer id,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        User doctorByid = userService.findDoctorByid(id);
        User loginuser = (User) session.getAttribute("loginuser");
        List<Department> departmentAll = departmentService.findDepartmentByRole(loginuser);
        List<Role> loginRoleList = rolesService.findLoginRoleList(loginuser);
        if (doctorByid.getUserPhotoRequest()!=null)
            session.setAttribute("filepath",doctorByid.getUserPhotoRequest());
        else if (doctorByid.getUserPhotoRequest()==null) {
            session.setAttribute("filepath", 1);
        }
        modelAndView.addObject("loginRoleList",loginRoleList);
        modelAndView.addObject("doctorByid",doctorByid);
        modelAndView.addObject("department",departmentAll);
        modelAndView.setViewName("Doctor-update");
        return modelAndView;
    }

    @PostMapping("/updateDoctor")
    public String updateDoctor(User user,
                               @RequestParam("file") MultipartFile file,
                               @RequestParam("st") Integer st,
                               HttpServletRequest request,
                               @RequestParam("mode") String departname) throws UnsupportedEncodingException {
        System.out.println(st);
        List<Department> departmentAll = departmentService.findDepartmentAll();
        for (Department department : departmentAll) {
            if (user.getUserDepart()==department.getId()){
                user.setUserDepartname(department.getDepartname());
                user.setUserDepartbelong(department.getDepartbelong());
                break;
            }
        }
        String[] doctorupload =new String[2];
        if(st==2)
        {
            doctorupload = new FileUploadController().Doctorupload(file, request);
            user.setUserPhoto(doctorupload[0]);
            user.setUserPhotoRequest(doctorupload[1]);
        }
        int i = userService.updateDoctor(user);
        String s = new String(departname.getBytes("utf-8"), "iso-8859-1");
        if (i>0)
            return "redirect:/findDepartUserByName?pn=1"+"&departname="+s;
        else
            return null;
    }
    @GetMapping("/Doctor-deial")
    public ModelAndView Doctor_deial(@RequestParam("id") Integer id,HttpSession session){
        User doctorByid = userService.findDoctorByid(id);
        ModelAndView modelAndView = new ModelAndView();
        if (doctorByid.getUserPhotoRequest()!=null)
            session.setAttribute("filepath",doctorByid.getUserPhotoRequest());
        if (doctorByid.getUserPhotoRequest()==null)
            session.setAttribute("filepath",1);
        modelAndView.addObject("doctorByid",doctorByid);
        modelAndView.setViewName("Doctor-deial");
        return modelAndView;
    }
    @GetMapping("updateDoctorStateUI")
    public ModelAndView updateDoctorStateUI(@RequestParam("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        User doctorByid = userService.findDoctorByid(id);
        List<States> allState = statesService.findAllState();
        modelAndView.addObject("states",allState);
        modelAndView.addObject("doctorByid",doctorByid);
        modelAndView.setViewName("DoctorState-update");
        return modelAndView;
    }
    @PostMapping("/updateDoctorState")
    public String updateDoctorState(User user) throws UnsupportedEncodingException {
        List<States> allState = statesService.findAllState();
        for (States states : allState) {
            if (user.getUserStateId()==states.getStateid()){
                user.setUserStatename(states.getStatename());
                break;
            }
        }
        String userStatename = user.getUserStatename();
        String s = new String(userStatename.getBytes("utf-8"), "iso-8859-1");
        int i = userService.updateDoctor(user);
        if (i>0)
            return "redirect:/findDoctorByState?StateName="+s+"&pn=1";
        else return null;
    }

    @GetMapping("/updatSelfDeialUI")
    public String updatSelfDeial(@RequestParam("id") Integer id,Model model){
        User doctorByid = userService.findDoctorByid(id);
        model.addAttribute("doctorByid",doctorByid);
        return "DoctorSelf-deial";
    }

    @PostMapping("/updatSelfDeial")
    public String updatSelfDeial(User user,HttpSession session){
        int i = userService.updateDoctor(user);
        if (i>0) {
            User user1 = userService.findDoctorByid(user.getId());
            session.setAttribute("loginuser",user1);
            return "redirect:/pages-profile.html";
        }
        else return null;
    }
    @GetMapping("/DoctorSelf-deial.html")
    public String DoctorSelf(){
        return "DoctorSelf-deial";
    }

    @GetMapping("/deleteDoctrInDepart")
    public String deleteDoctrInDepart(@RequestParam("mode") String departname,
                                      @RequestParam("id") Integer id) throws UnsupportedEncodingException {
        int i = userService.deleteDoctor(id);
        String s = new String(departname.getBytes("utf-8"), "iso-8859-1");
        if(i>0)
            return "redirect:/findDepartUserByName?pn="+1+"&departname="+s;
        else return null;
    }

    @GetMapping("/deleteDoctor")
    public String deleteDoctor(@RequestParam("id") Integer id) {
        int i = userService.deleteDoctor(id);
        if(i>0)
            return "redirect:/getAllDoctors?pn=1";
        else return null;
    }
}
