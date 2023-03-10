package com.example.graduation_project.Controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.States;
import com.example.graduation_project.Service.StatesService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StatesController {

    @Autowired
    StatesService statesService;

    @GetMapping("/findPatientAllStates")
    public String findAllStates(@RequestParam(value = "pn") Integer pn, Model model,
                                @RequestParam(value = "previous",defaultValue = "false") Boolean previous,
                                @RequestParam(value = "next",defaultValue = "false") Boolean next){
        Page<States> statesPage = statesService.findAllStates(pn);
        if (previous==true && pn>1){
            pn++;
            return "redirect:/findPatientAllStates?pn="+pn;
        }
        if (next==true && pn<statesPage.getPages()){
            pn--;
            return "redirect:/findPatientAllStates?pn="+pn;
        }
        List<States> records = new ArrayList<States>();
        int i=0;
        for (States record : statesPage.getRecords()) {
            if (record.getPatientstate()!=null){
                i++;
                records.add(record);
            }
        }
        System.out.println(records.size());
        statesPage.setTotal(i);
        model.addAttribute("PatientstatesPage",statesPage);
        model.addAttribute("PatientStateRecord",records);
        return "State-patient";
    }
    /*
    * 添加状态
    * */
    @GetMapping("/addPatientState")
    public String addPatientState(States state){
        String patientstate = state.getPatientstate();
        List<States> allState = statesService.findAllState();
        int i=0;
        for (States st : allState) {
            if (st.getPatientstate()==null){
                st.setPatientstate(patientstate);
                i=statesService.updateStaetByid(st);
            }
        }
        if (i>0){
            return "redirect:/findPatientAllStates";
        }

        else {
             i = statesService.addState(state);
            if (i>0)
                return "redirect:/findPatientAllStates";
            else{
                System.out.println("添加失败");
                return "index";
            }
        }

    }

    @GetMapping("/findDoctorAllStates")
    public String findDoctorAllStates(@RequestParam(value = "pn") Integer pn, Model model,
                                      @RequestParam(value = "previous",defaultValue = "false") Boolean previous,
                                      @RequestParam(value = "next",defaultValue = "false") Boolean next){
        Page<States> statesPage = statesService.findAllStates(pn);
        if (previous==true && pn>1){
            pn++;
            return "redirect:/findDoctorAllStates?pn="+pn;
        }
        if (next==true && pn<statesPage.getPages()){
            pn--;
            return "redirect:/findDoctorAllStates?pn="+pn;
        }
        List<States> records = new ArrayList<States>();
        int i=0;
        for (States record : statesPage.getRecords()) {
            if (record.getStatename()!=null){
                i++;
                records.add(record);
            }
        }
        System.out.println(records.size());
        statesPage.setTotal(i);
        model.addAttribute("DoctorStateRecord",records);
        model.addAttribute("statesPage",statesPage);
        return "State-doctor";
    }
    /*
     * 添加状态
     * */
    @GetMapping("/addDoctorState")
    public String addDoctorState(States state){
        int i = statesService.addState(state);
        if (i>0)
            return "redirect:/findDoctorAllStates";
        else{
            System.out.println("添加失败");
            return "index";
        }
    }

    @GetMapping("/deleteDoctorState")
    public String deleteDoctorStateByName(@RequestParam("id") Integer id) {
        States stateByid = statesService.findStateByid(id);
        int i=0;
        if (stateByid.getPatientstate()==null){
             i = statesService.deleteStateByid(id);
        }else {
             stateByid.setStatename(null);
             i = statesService.updateState(stateByid);
        }
        if (i>0)
            return "redirect:/findDoctorAllStates";
        else {
            System.out.println("删除失败");
            return "index";
        }

    }

    @GetMapping("/deletePatientState")
    public String deletePatientStateByName(@RequestParam("id") Integer id) {
        int i =0;
        States stateByid = statesService.findStateByid(id);
        if (stateByid.getStatename()==null){
            i = statesService.deleteStateByid(id);
        }else {
            stateByid.setPatientstate(null);
            i = statesService.updateState(stateByid);
        }
        if (i>0)
            return "redirect:/findPatientAllStates";
        else {
            System.out.println("删除失败");
            return "index";
        }


    }

    @GetMapping("/findAllPatientAnduserState")
    public String findAllPateientState(HttpSession session){
        List<States> allPatientState = statesService.findAllPatientState();
        String[] pastates =new String[allPatientState.size()];
        String[] dostates = new String[allPatientState.size()];
        for (int i = 0; i < allPatientState.size(); i++) {
            States states = allPatientState.get(i);
            dostates[i] = states.getStatename();
            pastates[i] = states.getPatientstate();
            //System.out.println(pastates[i]);
        }
        session.setAttribute("allPatientState",pastates);
        session.setAttribute("allDoctorState",dostates);
        return "redirect:/findRoleName";
    }
    @GetMapping("/State-add.html")
    public String State_add(){
        return "State-add";
    }
    @GetMapping("/State-doctor.html")
    public String State_Doctor(){
        return "State-doctor";
    }
    @GetMapping("/State-patient.html")
    public String State_Patient(){
        return "State-patient";
    }
    @GetMapping("/updatedoStateUI")
    public String updatedoStateUI(@RequestParam("id") Integer id,Model model){
        States stateByid = statesService.findStateByid(id);
        model.addAttribute("stateByid",stateByid);
        return "updatedoStateUI";
    }
    @PostMapping("updatedoState")
    public String updatedoState(States states){
        int i = statesService.updateStaetByid(states);
        if (i>0)
            return "redirect:/findDoctorAllStates?pn=1";
        else
            return null;
    }
}

