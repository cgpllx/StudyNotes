package com.mh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mh.Service.DeptService;
import com.mh.pojo.Dept;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
public class DeptController {

    @Resource
    private DeptService deptService;

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping("/getAll")
    public @ResponseBody String getAllInfo(){
        System.out.println("进入方法。。。");
        List<Dept> depts = deptService.queryAllInfo();
        String s = null;
        int length = s.length();
        try {
            s = objectMapper.writeValueAsString(depts);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("cuowu");
        }
        return s;
    }

    @GetMapping("/get")
    public ModelAndView get(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        List<Dept> depts = deptService.queryAllInfo();
        log.info("获取到的部门信息："+depts);
        modelAndView.addObject("list", depts);
        modelAndView.setViewName("helloWorld");
        session.setAttribute("boolean", false);
        modelAndView.addObject(session);
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView add(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("add");
        return mv;
    }

    @PostMapping("/addInfo")
    public String addInfo(Dept dept){
        log.info(dept+"");
        return "helloWorld";
    }

    @GetMapping("/del")
    public String delInfo(Integer deptno){
        System.out.println("获取到的deptno是："+deptno);
        return "helloWorld";
    }

    @GetMapping("/pageGetAll")
    public ModelAndView pageGetAll(Integer pageNum){
        ModelAndView mv = new ModelAndView();
        if (pageNum==null){
            PageHelper.startPage(1, 2);
        }else {
            PageHelper.startPage(pageNum, 2);
        }

        PageInfo<Dept> depts = new PageInfo<>(deptService.queryAllInfo());
        mv.setViewName("pageShow.html");
        mv.addObject("pageInfo",depts);
        return mv;

    }
}
