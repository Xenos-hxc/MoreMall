package org.example.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.example.dao.AdminDAO;
import org.example.entity.Admin;
import org.example.entity.Staff;
import org.example.mapper.StaffMapper;
import org.example.service.IStaffService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.vo.AdminVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author example.demo
 * @since 2023-11-04
 */
@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {



    final String savePath = System.getProperty("user.dir") + File.separator +
            "upload" + File.separator +
            "images/";

    @Override
    public String upload(MultipartFile file) {
        System.out.println(file);
        //获取文件的名称（全名称，包括后缀）
        String filename = file.getOriginalFilename();
        long flag = System.currentTimeMillis();
        String filePath = savePath + flag + "-" + filename;
        System.out.println("+++++++++++"+filePath+"---------");
        //上传
        File saveFile = new File(filePath);
        try {
            FileUtil.mkParentDirs(filePath);
            file.transferTo(saveFile);
            String url = "http://localhost:9088/staff/download/" + flag;

            if (filename.endsWith("png") || filename.endsWith("jpg") || filename.endsWith("jpeg")) {
                url += "?&play=1";
            }
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String updateAvatar(String id,String avatar) {
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.updateAvatar(id,avatar);
        return "success";
    }

    @Override
    public void download(String flag, String play, HttpServletResponse response) {
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(savePath);
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                String realName = fileName.substring(fileName.indexOf("-") + 1);
                if ("1".equals(play)) {
                    response.addHeader("Content-Disposition", "inline;filename" + URLEncoder.encode(realName, "UTF-8"));
                } else {
                    response.addHeader("Content-Disposition", "attachment;filename" + URLEncoder.encode(realName, "UTF-8"));
                }
                byte[] bytes = FileUtil.readBytes(savePath + fileName);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public AdminVO findById(String id) {
        AdminVO adminVO = new AdminVO();
        AdminDAO adminDAO = new AdminDAO();
        List<Admin> admins = adminDAO.findById(id);
        if(admins.size()!=0){
            adminVO.setId(admins.get(0).getId().toString());
            adminVO.setName(admins.get(0).getName());
            adminVO.setEmail(admins.get(0).getEmail());
            adminVO.setAvatar(admins.get(0).getAvatar());
            adminVO.setPassword(admins.get(0).getPassword());
            adminVO.setMobile(admins.get(0).getMobile());
            return adminVO;
        }
        else {
            return null;
        }


    }

    @Override
    public void updateAdmin(AdminVO adminVO) {
        AdminDAO adminDAO = new AdminDAO();
        adminDAO.updateAdmin(adminVO);
    }
}
