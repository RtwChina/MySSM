import com.alibaba.fastjson.JSONObject;
import com.dao.model.Role;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author rtw
 * @since 2019-06-10
 */
@Slf4j
public class JsonStringTest {
    public static void main(String[] args) {
        // 自定义对象
        Role role = new Role();
        role.setNote("rtt");
        role.setRoleName("超级帅");
        String roleString = role.toString();
        String roleJsonString = JSONObject.toJSONString(role);
        System.out.println(roleString);
        System.out.println(roleJsonString);

        // string数组
        List<String> stringList = new ArrayList<>();
        stringList.add("qwe");
        stringList.add("asd");
        stringList.add("zxc");
        String stringListString = stringList.toString();
        String stringListJsonString = JSONObject.toJSONString(stringList);
        System.out.println(stringListString);
        System.out.println(JSONObject.toJSONString(stringListJsonString));
        System.out.println("GOOD");
    }
}
