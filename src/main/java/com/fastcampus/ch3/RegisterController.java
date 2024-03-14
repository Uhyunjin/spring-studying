package com.fastcampus.ch3;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller // ctrl+shift+o 자동 import
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserDao userDao;
    int FAIL = 0;

    @InitBinder
    public void toDate(WebDataBinder binder) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
        binder.setValidator(new UserValidator()); // UserValidator를 WebDataBinder의 로컬 validator로 등록
        //	List<Validator> validatorList = binder.getValidators();
        //	System.out.println("validatorList="+validatorList);
    }

    @GetMapping("/add")
    public String register() {
        return "registerForm"; // WEB-INF/views/registerForm.jsp
    }

    @PostMapping("/add")
    public String save(@Valid User user, BindingResult result, Model m) throws Exception {
    // @valid -> bean validation api 추가(maven repository)
        System.out.println("result="+result);
        System.out.println("user="+user);

        if(!result.hasErrors()) {
        // 에러가 없으면 db에 유저 정보를 저장한다
        int rowCnt = userDao.insertUser(user);
        
        // 만약 저장 안되었으면 다시 registerForm을 반환한다
            if (rowCnt ==FAIL){
                return "registerForm";
            }
        }
        
        return "registerInfo";
    }

    private boolean isValid(User user) {
        return true;
    }
}
