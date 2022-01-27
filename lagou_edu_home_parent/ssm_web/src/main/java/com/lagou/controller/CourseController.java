package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /*
        多条件课程查询
     */
    @RequestMapping("findCourseByCondition")
    private ResponseResult findCourseByCondition(@RequestBody CourseVO courseVO){   //@RequestBody,将前台的JSON字符串转为对象
        List<Course> list = courseService.findCourseByCondition(courseVO);
        return new ResponseResult(true,200,"相应成功",list);
    }

    /*
        课程图片上传
     */
    @RequestMapping("courseUpload")
    public ResponseResult courseUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //1.判断接收到的文件是否为空
        if (file.isEmpty()){
            throw new RuntimeException();
        }

        //2.获取项目部署路径
        //F:\Tomcat\install_space\webapps\ssm_web\
        String realPath = request.getServletContext().getRealPath("/");
        //F:\Tomcat\install_space\webapps\
        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));

        //3.获取原文件名
        String originalFilename = file.getOriginalFilename();

        //4.生成新文件名
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));

        //5.文件上传
        String uploadPath = substring+"upload_test\\";
        File filePath = new File(uploadPath, newFileName);
        if (!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录"+filePath);
        }
        //图片进行了真正的上传
        file.transferTo(filePath);

        //将文件名和文件路径进行返回，响应
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName",newFileName);
        map.put("filePath","http://localhost:8080/upload_test/"+newFileName);

        //返回ResponseResult
        return new ResponseResult(true,200,"图片上传成功",map);
    }


    /*
        新增课程以及讲师信息
     */
    @RequestMapping("saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {
        if (courseVO.getId() == null){
            courseService.saveCourseOrTeacher(courseVO);
            return new ResponseResult(true,200,"新增成功",null);
        }else {
            courseService.updateCourseOrTeacher(courseVO);
            return new ResponseResult(true,200,"修改成功",null);
        }

    }

    /*
        回显课程信息(根据id查询课程信息，以及关联的讲师信息)
     */
    @RequestMapping("findCourseById")
    public ResponseResult findCourseById(Integer id){

        CourseVO course = courseService.findCourseById(id);
        return new ResponseResult(true,200,"根据id查询课程信息成功",course);

    }

    /*
        课程状态管理
     */
    @RequestMapping("updateCourseStatus")
    public ResponseResult updateCourseStatus(int id,int status){
        //调用service，传递参数
        courseService.updateCourseStatus(id,status);

        //响应数据
        HashMap<Object, Object> map = new HashMap<>();
        map.put("status",status);
        return new ResponseResult(true,200,"课程状态改变成功",map);
    }
}
