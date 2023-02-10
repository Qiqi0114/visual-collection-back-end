package com.example.collectiontableadministration.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.io.resource.ClassPathResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.collectiontableadministration.domain.UserDepartmentVo;
import com.example.collectiontableadministration.domain.dto.ImportUser;
import com.example.collectiontableadministration.domain.dto.UserPage;
import com.example.collectiontableadministration.domain.pojo.Department;
import com.example.collectiontableadministration.domain.pojo.User;
import com.example.collectiontableadministration.domain.request.PageBean;
import com.example.collectiontableadministration.domain.request.dto.UserPageDto;
import com.example.collectiontableadministration.domain.response.vo.UserAndRole;
import com.example.collectiontableadministration.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author 杨子涵
 * 账号管理
 */
@RequestMapping("/userService/user/")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;


    @GetMapping("/downloadUserExcel")
    public void downloadUserExcel() throws IOException {
        response.reset();
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader(
                "Content-disposition",
                "attachment; filename=账户导入模板.xlsx");
        try(

                BufferedInputStream bis = new BufferedInputStream( new ClassPathResource("file/账户导入模板.xlsx").getStream());
                // 输出流
                BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        ){
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = bis.read(buff)) > 0) {
                bos.write(buff, 0, len);
             }
        }

    }

    @PostMapping("/importUserExcel")
    private void importUserExcel(@RequestParam("file") MultipartFile file) throws Exception {
        ImportParams importParams = new ImportParams();
        //标题行设置为1行，默认是0，可以不设置；依实际情况设置。
      //  importParams.setTitleRows(1);
        // 表头设置为1行
        importParams.setHeadRows(1);

            //读取excel
            List<ImportUser> users = ExcelImportUtil.importExcel(file.getInputStream(), ImportUser.class, importParams);
            //开始封装批量数据
            //系名称
            userService.importUserExcel(users);
    }

    @PostMapping("/getUserPageList")
    public IPage<UserDepartmentVo> getUserPageList(@RequestBody UserPage userPage){
       return userService.getUserPageList(userPage);
    }
    @GetMapping("/getUserById")
    public User  getUserById(@RequestParam("id") String id) throws MessagingException {
        return userService.getUserById(id);
    }

    @PutMapping()
    private void putUser(@RequestBody User user){
        userService.putUser(user);
    }

    @PostMapping
    private void innerUser(@RequestBody User user){
        userService.innerUser(user);
    }

    @GetMapping("/departmentList")
    public List<Department> departmentList(){
        return userService.departmentList();
    }
    @GetMapping("/passWordCZ")
    public void passWordCz(@RequestParam("id") String id) throws Exception {
        userService.passWordCz(id);
    }
    @DeleteMapping("/")
    public void deleteUser(@RequestParam("id")String id){
        userService.deleteUser(id);
    }
}
