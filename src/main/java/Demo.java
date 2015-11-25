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
        System.out.println(facade);
    }
}
