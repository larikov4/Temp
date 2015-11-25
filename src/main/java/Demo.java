import com.epam.hw1.dao.impl.UserDaoImpl;
import com.epam.hw1.facade.BookingFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Yevhen_Larikov
 */
public class Demo {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        BookingFacade facade = context.getBean(BookingFacade.class);
        UserDaoImpl dao = context.getBean(UserDaoImpl.class);
        System.out.println(dao.getUserByEmailDB("ivan2@email.com"));
    }
}
