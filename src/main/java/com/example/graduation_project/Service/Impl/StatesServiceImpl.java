package com.example.graduation_project.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.graduation_project.Domain.States;
import com.example.graduation_project.Mapper.StatesMapper;
import com.example.graduation_project.Service.StatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatesServiceImpl implements StatesService {

    @Autowired
    StatesMapper statesMapper;

    @Override
    public int deleteStateByid(Integer id) {
        int i = statesMapper.deleteById(id);
        return i;
    }

    @Override
    public Page<States> findAllStates(Integer pn) {
        Page<States> statesPage = new Page<>(pn,10);
        Page<States> statesPage1 = statesMapper.selectPage(statesPage, null);
        return statesPage1;
    }

    @Override
    public int addState(States state) {
        int insert = statesMapper.insert(state);
        return insert;
    }

    @Override
    public List<States> findAllPatientState() {
        List<States> states = statesMapper.selectList(null);
        return states;
    }

    @Override
    public States findStateByid(Integer id) {
        States states = statesMapper.selectById(id);
        return states;
    }

    @Override
    public int updateState(States stateByid) {
        int update = statesMapper.update(stateByid, null);
        return update;
    }

    @Override
    public List<States> findAllState() {
        List<States> states = statesMapper.selectList(null);
        return states;
    }

    @Override
    public int updateStaetByid(States s) {
        int i = statesMapper.updateById(s);
        return i;
    }

    @Override
    public List<States> finaStateByName(String s) {
        QueryWrapper<States> patientstate = new QueryWrapper<States>().eq("patientstate", s);
        List<States> states = statesMapper.selectList(patientstate);
        return states;
    }
}
