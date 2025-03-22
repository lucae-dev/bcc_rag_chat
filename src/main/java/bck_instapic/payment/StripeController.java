package bck_instapic.payment;

import com.stripe.model.checkout.Session;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/stripe")
public class StripeController {

    private final StripeService stripeService;

    public StripeController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout/create-session")
    public String createCheckoutSession(@RequestBody String priceId) {
        return Optional.of(priceId)
                .map(stripeService::createCheckoutSession)
                .map(Session::getId)
                .orElseThrow();
    }
}
