package com.example.graduation_project.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class FileUploadController {
    /*SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    public String upload(MultipartFile file, HttpServletRequest request) {
        //1.定义上传文件目录
        if (file==null)
            return null;
        String format = sdf.format(new Date());
        String path = request.getServletContext().getRealPath("/img") + format;
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        //2.获取文件信息
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        try {
            file.transferTo(new File(folder, newName));

            //3.返回上传文件的url
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/img" + format + newName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/
    //绑定文件上传路径到uploadPath

    /*@Autowired
    FileUpload  fileUpload;*/
    private String patientuploadPath="/file/Patient/";
    private String doctoruploadPath="/file/Doctor/";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    public String Patientupload(MultipartFile uploadFile,
                         HttpServletRequest request) {
        /*String uploadPath = fileUpload.getUpload_path();*/
        // 在 uploadPath 文件夹中通过日期对上传的文件归类保存
        // 比如：/2019/06/06/cf13891e-4b95-4000-81eb-b6d70ae44930.png
        String format = sdf.format(new Date());
        System.out.println(patientuploadPath);
        // 对上传的文件重命名，避免文件重名
        if (uploadFile!=null){
            String oldName = uploadFile.getOriginalFilename();
            String newName = UUID.randomUUID().toString()
                    + oldName.substring(oldName.lastIndexOf("."));
            File folder = new File(patientuploadPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            try {
                // 文件保存
                uploadFile.transferTo(new File(folder, newName));
                // 返回上传文件的访问路径
                String filePath = request.getScheme() + "://" + request.getServerName()
                        + ":" + request.getServerPort() + "/file/" + "Patient/" + newName;
                return filePath;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "error";
    }
    public String[] Doctorupload(MultipartFile uploadFile,
                                 HttpServletRequest request) {
        /*String uploadPath = fileUpload.getUpload_path();*/
        // 在 uploadPath 文件夹中通过日期对上传的文件归类保存
        // 比如：/2019/06/06/cf13891e-4b95-4000-81eb-b6d70ae44930.png
        String format = sdf.format(new Date());
        System.out.println(doctoruploadPath);
        String[] doctorpaths=new String[2];
        // 对上传的文件重命名，避免文件重名
        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString()
                + oldName.substring(oldName.lastIndexOf("."));
        File folder = new File(doctoruploadPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            // 文件保存
            uploadFile.transferTo(new File(folder,newName));
            // 返回上传文件的访问路径
            String filePath = "E:/Data"+doctoruploadPath+ newName;
            doctorpaths[0]=filePath;
            doctorpaths[1] = request.getScheme() + "://" + request.getServerName()
                    + ":" + request.getServerPort()+doctoruploadPath+ newName;
            return doctorpaths;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
