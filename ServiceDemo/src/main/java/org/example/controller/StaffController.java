package org.example.controller;


import org.example.dao.AdminDAO;
import org.example.entity.Admin;
import org.example.entity.Staff;
import org.example.mapper.StaffMapper;
import org.example.otherEntity.ResponseResult;
import org.example.service.*;
import org.example.vo.AdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author example.demo
 * @since 2023-11-04
 */
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private IStaffService iStaffService;



    @RequestMapping("/login")
    public ResponseResult login(@RequestParam("id") String id, @RequestParam("password") String password) throws UnknownHostException {
        AdminDAO adminDAO=new AdminDAO();
        Admin byNameAndPwd = adminDAO.findByNameAndPwd(id, password);
        if (byNameAndPwd == null) {
            return ResponseResult.fail();
        }
        AdminVO adminVO = new AdminVO();
        adminVO.setId(byNameAndPwd.getId().toString());
        adminVO.setName(byNameAndPwd.getName());
        adminVO.setPassword(byNameAndPwd.getPassword());
        adminVO.setMobile(byNameAndPwd.getMobile());
        adminVO.setEmail(byNameAndPwd.getEmail());
        adminVO.setAvatar(byNameAndPwd.getAvatar());


        if (byNameAndPwd==null) {
            return ResponseResult.fail();
        }
            return ResponseResult.success(adminVO);

    }

    @PostMapping("/upload")
    public ResponseResult uploadAvatar(MultipartFile file){
        System.out.println(file);
        return ResponseResult.success(iStaffService.upload(file));
    }

    @RequestMapping("/updateAvatar")
    public ResponseResult updateAvatar(@RequestParam("id") String id,@RequestParam("avatar") String avatar){
        String s = iStaffService.updateAvatar(id, avatar);
        if (s.equals("success")){
            return ResponseResult.success();
        }else {
            return ResponseResult.fail();
        }
    }

    @GetMapping("download/{flag}")
    public void downloadFile(@PathVariable String flag, @RequestParam(required = true) String play, HttpServletResponse response) {
        iStaffService.download(flag, play, response);
    }

    @RequestMapping("/getAdminById")
    public ResponseResult getAdminById(@RequestParam("id") String id){
        AdminVO adminVO = iStaffService.findById(id);
        if(adminVO!=null){
            return ResponseResult.success(adminVO);
        }else {
            return ResponseResult.fail();
        }
    }

    @RequestMapping("/updateAdmin")
    public ResponseResult updateAdmin(@RequestBody AdminVO adminVO){
        iStaffService.updateAdmin(adminVO);
        return ResponseResult.success();
    }

}
