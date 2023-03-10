package com.example.graduation_project.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.MedicalRecord;
import com.example.graduation_project.Domain.Patients;
import com.example.graduation_project.Domain.States;
import com.example.graduation_project.Domain.User;
import com.example.graduation_project.Service.MedicalRocardService;
import com.example.graduation_project.Service.PatientsService;
import com.example.graduation_project.Service.StatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PatientsController {
    @Autowired
    PatientsService patientsService;
    @Autowired
    StatesService statesService;
    @Autowired
    MedicalRocardService medicalRocardService;

    @GetMapping("/getAllPatients")
    public  String getAllPatients(@RequestParam(value = "pn") Integer pn, Model model,
                                  @RequestParam(value = "previous",defaultValue = "false") Boolean previous,
                                  @RequestParam(value = "next",defaultValue = "false") Boolean next){
        Page<Patients> allPatients = patientsService.findAllPatients(pn);
        if (previous==true && pn>1){
            pn--;
            return "redirect:/getAllPatients?pn="+pn;
        }
        if (next==true && pn<allPatients.getPages())
        {
            int current =(int) allPatients.getCurrent();
            pn=current+1;
            return "redirect:/getAllPatients?pn="+pn;
        }
        model.addAttribute("PatientPage",allPatients);
        return "allPatients";
    }

    @PostMapping("/PatientRegister")
    public String PatientRegister(Patients patient,Model model){
        int i = patientsService.patientRegister(patient);
        if (i>0){
            model.addAttribute("msg",1);
            return "redirect:/";
        }else {
            model.addAttribute("msg",0);
            return "redirect:/pages-register.html";
        }

    }
    @GetMapping("/findPatientByState")
    public String findPatientByState(@RequestParam("StateName") String StateName,
                                           @RequestParam(value = "pn") Integer pn,
                                           @RequestParam(value = "previous",defaultValue = "false") Boolean previous,
                                           @RequestParam(value = "next",defaultValue = "false") Boolean next,
                                           Model model,HttpSession session) throws UnsupportedEncodingException {
        User loginuser = (User) session.getAttribute("loginuser");
        Page<MedicalRecord> patientStateByMedicalInRole = medicalRocardService.findPatientStateByMedicalInRole(pn, loginuser, StateName);
        String s = new String(StateName.getBytes("utf-8"), "iso-8859-1");
        if (previous==true && pn>1){
            pn--;
            return "redirect:/findPatientByState?pn="+pn+"&StateName="+s;
        }
        if (next==true && pn<patientStateByMedicalInRole.getPages())
        {
            int current =(int) patientStateByMedicalInRole.getCurrent();
            pn=current+1;
            return "redirect:/findPatientByState?pn="+pn+"&StateName="+s;
        }
        model.addAttribute("patientByState",patientStateByMedicalInRole);
        model.addAttribute("nowPatientState",StateName);
        return "Patient-State";
    }

    @GetMapping("/page-patient-deial.html")
    public String page_patient_deial(){

        return "page-patient-deial";
    }
    @GetMapping("/LogoutPatient")
    public String LogoutPatient(HttpSession session){
        session.removeAttribute("loginpatient");
        return "redirect:/login";
    }

    @GetMapping("/updatePatientUI")
    public ModelAndView updatePatient(@RequestParam("id") Integer id){
        ModelAndView modelAndView = new ModelAndView();
        Patients patientByid = patientsService.findPatientByid(id);
        modelAndView.addObject("patientByid",patientByid);
        modelAndView.setViewName("updatePatientUI");
        return modelAndView;
    }

    @GetMapping("/updatePatientByDoctorUI")
    public String updatePatientByDoctorUI(@RequestParam("id") Integer id,Model model){
        Patients patientByid = patientsService.findPatientByid(id);
        model.addAttribute("patientByid",patientByid);
        return "updatePatientByDoctorUI";
    }

    @PostMapping("/UpdatePatient")
    public String UpdatePatient(Patients patients){
        int i = patientsService.updatePatient(patients);
        if (i>0)
            return "redirect:/getAllPatients?pn=1";
        else
            return null;
    }

    @PostMapping("/updatePatientInfo")
    public String updatePatientInfo(Patients patients,HttpSession session){
        int i = patientsService.updatePatient(patients);
        if (i>0) {

            return "redirect:/page-patient-deial.html";
        }
        else
            return null;
    }

    @GetMapping("/updatePatientStateUI")
    public ModelAndView updatePatientStateUI(@RequestParam("id") Integer id) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        MedicalRecord medicalById = medicalRocardService.findMedicalById(id);
        List<States> allState = statesService.findAllState();
        modelAndView.addObject("medicalById",medicalById);
        modelAndView.addObject("allState",allState);
        modelAndView.setViewName("State-Patient-update");
        return modelAndView;
    }

    @PostMapping("/updatePatientState")
    public String updatePatientState(MedicalRecord medicalRecord) throws UnsupportedEncodingException, ParseException {
        List<States> allState = statesService.findAllState();
        for (States states : allState) {
            if (states.getStateid()==medicalRecord.getCasePatientStateid()){
                medicalRecord.setCasePatientStatename(states.getPatientstate());
                break;
            }
        }
        String s = new String(medicalRecord.getCasePatientStatename().getBytes("utf-8"), "iso-8859-1");
        if (medicalRecord.getCasePatientStatename().equals(new String("未住院"))){
            medicalRecord.setCaseInHospital(null);
            medicalRecord.setCaseLeHospital(null);
        }else if (medicalRecord.getCasePatientStatename().equals(new String("住院"))){
            medicalRecord.setCaseLeHospital(null);
        }
        int i = medicalRocardService.updateMedical(medicalRecord);
        if (i>0)
            return "redirect:/findPatientByState?pn=1&StateName="+s;
        else {
            System.out.println(i);
            return null;
        }
    }

    //
    public MedicalRecord SetPatientState(Patients patients) throws ParseException {
        String patientIdcard = patients.getPatientIdcard();
        List<MedicalRecord> patientMedicalByidcard = medicalRocardService.findPatientMedicalByidcard(patientIdcard);
        if (patientMedicalByidcard==null){
            patients.setPatientStateId(3);
            patients.setPatientStateName("未住院");
            return null;
        }

        int i=0,j=-1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2000-01-01");
        for (i=0;i<patientMedicalByidcard.size();i++)
        {
            if (patientMedicalByidcard.get(i).getCaseTime().compareTo(date)>0){
                date=patientMedicalByidcard.get(i).getCaseTime();
                j=i;
            }
        }
        if (j==-1){
            patients.setPatientStateId(3);
            patients.setPatientStateName("未住院");
            return null;
        }else{
            MedicalRecord medicalRecord = patientMedicalByidcard.get(j);
            patients.setPatientStateId(medicalRecord.getCasePatientStateid());
            patients.setPatientStateName(medicalRecord.getCasePatientStatename());
            patients.setPatientDepart(medicalRecord.getCaseBydepartid());
            patients.setPatientDepartBelong(medicalRecord.getCaseBydepartBelong());
            return medicalRecord;
        }
    }
    //
    public MedicalRecord findAfterMedical(Patients patients) throws ParseException {
        List<MedicalRecord> patientMedicalByidcard = medicalRocardService.findPatientMedicalByidcard(patients.getPatientIdcard());
        int i=0,j=-1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2000-01-01");

        for (i=0;i<patientMedicalByidcard.size();i++)
        {
            if (patientMedicalByidcard.get(i).getCaseTime().compareTo(date)>0){

                date=patientMedicalByidcard.get(i).getCaseTime();
                System.out.println(date);
                j=i;
            }
        }

        if (j==-1){
            return null;
        }else {
            MedicalRecord medicalRecord = patientMedicalByidcard.get(j);
            return medicalRecord;
        }

    }

    @GetMapping("/Patient-deial")
    public String Patient_deial(@RequestParam("id") Integer id,Model model){
        Patients patientByid = patientsService.findPatientByid(id);
        model.addAttribute("patientByid",patientByid);
        return "Patient-deial";
    }

    @GetMapping("/addPatientUI")
    public String addPatientUI(){

        return "addPatientUI";
    }
    @PostMapping("/addPatient")
    public String addPatient(Patients patients){
        patients.setPatientStateId(3);
        patients.setPatientStateName("未住院");
        int i = patientsService.patientRegister(patients);
        if (i>0)
            return "redirect:/getAllPatients?pn=1";
        else return null;
    }

    @ResponseBody
    @PostMapping("/findOneEmail")
    public Boolean findOnlyOneEmail(@RequestParam("emial") String email){
        if (patientsService.findOnlyOneEmail(email)!=null)
            return true;
        else return false;
    }
    @GetMapping("deletepatient")
    public String detelepatient(@RequestParam("id") Integer id){
        int deletepatient = patientsService.deletepatient(id);
        if (deletepatient>0)
            return "redirect:/getAllPatients?pn=1";
        else return null;
    }
}
