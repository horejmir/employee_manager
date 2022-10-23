package cz.horejsi.employee_manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Root context redirection to Swagger documentation.
 *
 * @author Miroslav Horejsi
 * @version 1.0.0
 */

@Controller
public class RootController {

    /**
     * Sets the index page mapping to point to the Swagger UI.
     *
     * @return A redirect to the Swagger UI.
     */
    @RequestMapping("/")
    public RedirectView rootRedirect() {
        return new RedirectView("/swagger-ui/index.html");
    }
}
