package org.example.service;

import org.example.entity.Staff;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.vo.AdminVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author example.demo
 * @since 2023-11-04
 */
public interface IStaffService extends IService<Staff> {

    String upload(MultipartFile file);

    String updateAvatar(String id,String avatarPath);

    void download(String flag, String play, HttpServletResponse response);

    AdminVO findById(String id);

    void updateAdmin(AdminVO adminVO);
}
