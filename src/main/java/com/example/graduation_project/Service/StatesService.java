package com.example.graduation_project.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.States;

import java.util.List;

public interface StatesService {

    int deleteStateByid(Integer id);

    Page<States> findAllStates(Integer pn);

    int addState(States state);

    List<States> findAllPatientState();
    States findStateByid( Integer id);

    int updateState(States stateByid);

    List<States> findAllState();

    int updateStaetByid(States s);

    List<States> finaStateByName(String s);
}
