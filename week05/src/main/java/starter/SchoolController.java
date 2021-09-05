package starter;

import com.zclau.spring.boot.bean.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SchoolController {

    @Autowired
    private School school;

    @RequestMapping("/mySchool")
    public Object mySchool() {
        return school;
    }
}
