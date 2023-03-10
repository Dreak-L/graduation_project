package com.example.graduation_project.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.Department;
import com.example.graduation_project.Domain.MedicalRecord;
import com.example.graduation_project.Domain.States;
import com.example.graduation_project.Domain.User;
import com.example.graduation_project.Service.DepartmentService;
import com.example.graduation_project.Service.MedicalRocardService;
import com.example.graduation_project.Service.StatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MedicalRocardController {
    @Autowired
    MedicalRocardService medicalRocardService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    StatesService statesService;


    @GetMapping("/findAllMedicalRocard")
    public String findAllMedicalRocard(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        Page<MedicalRecord> allMedicalRocard = medicalRocardService.findAllMedicalRocard(pn);
        model.addAttribute("allMedicalRocard", allMedicalRocard);
        return "allMedicalRocard";
    }

    //根据科室寻找病历
    @GetMapping("/findMedicalRocardBydepart")
    public String findMedicalRocardBydepart(@RequestParam("pn") Integer pn,
                                                  @RequestParam("departname") String departname, Model model,
                                                  @RequestParam(value = "previous",defaultValue = "false") Boolean previous,
                                                  @RequestParam(value = "next",defaultValue = "false") Boolean next,HttpSession session) throws UnsupportedEncodingException {
        User loginuser = (User) session.getAttribute("loginuser");
        Page<MedicalRecord> medicalRocardBydepart = medicalRocardService.findMedicalRocardBydepart(departname, pn,loginuser);
        model.addAttribute("nowdepartname", departname);
        String s = new String(departname.getBytes("utf-8"), "iso-8859-1");
        if (previous==true && pn>1){
            pn--;
            return "redirect:/findMedicalRocardBydepart?pn="+pn+"&departname="+s;
        }
        if (next==true && pn<medicalRocardBydepart.getPages())
        {
            int current =(int) medicalRocardBydepart.getCurrent();
            pn=current+1;
            return "redirect:/findMedicalRocardBydepart?pn="+pn+"&departname="+s;
        }
        model.addAttribute("medicalRocardBydepart",medicalRocardBydepart);
        return "MedicalRocard";
    }

    //查找自己接诊病历
    @GetMapping("/findMedicalBySelf")
    public String findMedicalBySelf(@RequestParam(value = "pn") Integer pn,
                                    @RequestParam("MedicalByid") Integer MedicalByid,
                                    Model model,
                                    @RequestParam(value = "previous",defaultValue = "false") Boolean previous,
                                    @RequestParam(value = "next",defaultValue = "false") Boolean next) {
        Page<MedicalRecord> medicalBySelf = medicalRocardService.findMedicalBySelf(pn, MedicalByid);
        if (previous==true && pn>1){
            pn--;
            return "redirect:/findMedicalBySelf?pn="+pn+"&MedicalByid="+MedicalByid;
        }
        if (next==true && pn<medicalBySelf.getPages())
        {
            int current =(int) medicalBySelf.getCurrent();
            pn=current+1;
            return "redirect:/findMedicalBySelf?pn="+pn+"&MedicalByid="+MedicalByid;
        }
        model.addAttribute("MedicalBySelf", medicalBySelf);
        return "MedicalRocard-Self";
    }

    @GetMapping("/Medical-add.html")
    public String Medical_add(Model model) {
        List<States> allState = statesService.findAllState();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("allState",allState);
        model.addAttribute("nowDate",dateFormat.format(date));
        return "Medical-add";
    }

    //添加病历
    @PostMapping("/addMedicalRecord")
    public String addMedicalRocard(MedicalRecord medicalRecord, @RequestParam("st") Integer st,
                                   HttpSession session, @RequestParam("file") MultipartFile file,
                                   HttpServletRequest request) throws IOException {
        if (st==1){
            medicalRecord.setCaseFile(null);
        }
        if (st==2){
            String filepath = new FileUploadController().Patientupload(file,request);
            medicalRecord.setCaseFile(filepath);
        }
        List<States> states = statesService.findAllState();
        for (States state : states) {
            if (state.getStateid()==medicalRecord.getCasePatientStateid()) {
                medicalRecord.setCasePatientStatename(state.getPatientstate());
                if (medicalRecord.getCasePatientStateid()>=3){
                    medicalRecord.setCaseInHospital(null);
                }
                break;
            }
        }
        User loginuser = (User) session.getAttribute("loginuser");
        medicalRecord.setCaseBydepartid(loginuser.getUserDepart());
        medicalRecord.setCaseBydepartBelong(loginuser.getUserDepartbelong());
        int i = medicalRocardService.addMedicalRocard(medicalRecord);
        if (i > 0)
            return "redirect:/findMedicalBySelf?pn="+1+"&MedicalByid="+loginuser.getId();
        else
            return null;
    }

    //删除病历
    @GetMapping("/deleteMedicalByid")
    public String deleteMedicalByid(@RequestParam("Medicalid") Integer id,
                                    @RequestParam("departname") String departname) throws UnsupportedEncodingException {
        int i = medicalRocardService.deleteMedicalByid(id);
        String s = new String(departname.getBytes("utf-8"), "iso-8859-1");
        if (i > 0)
            return "redirect:/findMedicalRocardBydepart?departname=" + s+"&pn=1";
        else
            return null;
    }

    @GetMapping("/updateMedicalUI")
    public ModelAndView updateMedicalUI(@RequestParam("id") Integer id,
                                        @RequestParam("mode") String mode,
                                        HttpSession session) {
        System.out.println(mode);
        ModelAndView modelAndView = new ModelAndView();
        MedicalRecord medicalById = medicalRocardService.findMedicalById(id);
        List<States> allState = statesService.findAllState();
        modelAndView.addObject("upMedical", medicalById);
        modelAndView.addObject("allState",allState);
        modelAndView.setViewName("Medical-update");
        session.setAttribute("upMedicalmode", mode);
        if (medicalById.getCaseFile()==null||medicalById.getCaseFile()==" ")
            session.setAttribute("filepath",1);
        else
            session.setAttribute("filepath",medicalById.getCaseFile());
        return modelAndView;
    }

    @PostMapping("/updateMedical")
    public String updateMedical(MedicalRecord medicalRecord,
                                @RequestParam("st") Integer st,@RequestParam("file") MultipartFile file,HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {

        List<States> allState = statesService.findAllState();
        for (States states : allState) {
            if (states.getStateid()==medicalRecord.getCasePatientStateid()){
                medicalRecord.setCasePatientStatename(states.getPatientstate());
                break;
            }
        }
        if (medicalRecord.getCasePatientStateid()==3){
            medicalRecord.setCaseInHospital(null);
            medicalRecord.setCaseLeHospital(null);
        }
        String patientupload=null;
        if (st==1)
        {
            medicalRecord.setCaseFile(patientupload);
        }else if (st==2){
            patientupload = new FileUploadController().Patientupload(file, request);
            medicalRecord.setCaseFile(patientupload);
        }
        int i = medicalRocardService.updateMedical(medicalRecord);
        String mode = (String) session.getAttribute("upMedicalmode");
        System.out.println(mode);
        if (i > 0){
            if (mode.equals("self")) {
                User loginuser = (User) session.getAttribute("loginuser");
                Integer id = loginuser.getId();
                return "redirect:/findMedicalBySelf?MedicalByid="+id+"&pn="+1;
            }
            else{
                String s = new String(mode.getBytes("utf-8"), "iso-8859-1");
                return "redirect:/findMedicalRocardBydepart?departname=" + s+"&pn=1";
            }

        }
        else
            return null;
    }

    @GetMapping("/MedicalDetail")
    public ModelAndView MedicalDetail(@RequestParam("id") Integer id,HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        MedicalRecord medicalById = medicalRocardService.findMedicalById(id);
        modelAndView.addObject("Medicaldetail",medicalById);
        session.setAttribute("filepath",medicalById.getCaseFile());
        modelAndView.setViewName("MedicalRocard-detail");
        return modelAndView;
    }

    @GetMapping("/findPatientSelfMeical")
    public String findPatientSelfMeical(@RequestParam("idcard") String idcard,
                                        @RequestParam("pn") Integer pn,Model model,
                                        @RequestParam(value = "previous",defaultValue = "false") Boolean previous,
                                        @RequestParam(value = "next",defaultValue = "false") Boolean next){
        Page<MedicalRecord> patientMedical = medicalRocardService.findPatientMedical(pn, idcard);
        if (previous==true && pn>1){
            pn--;
            return "redirect:/findPatientSelfMeical?pn="+pn+"&idcard="+idcard;
        }
        if (next==true && pn<patientMedical.getPages())
        {
            int current =(int) patientMedical.getCurrent();
            pn=current+1;
            return "redirect:/findMedicalBySelf?pn="+pn+"&idcard="+idcard;
        }
        model.addAttribute("patientMedical",patientMedical);
        model.addAttribute("idcard",idcard);
        return "Medical-patient";
    }

    @GetMapping("/PatientMedical_detail")
    public ModelAndView PatientMedical_detail(@RequestParam("id") Integer id){
        MedicalRecord medicalById = medicalRocardService.findMedicalById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("medicalById",medicalById);
        modelAndView.setViewName("PatientMedical-detail");
        return modelAndView;
    }
}
