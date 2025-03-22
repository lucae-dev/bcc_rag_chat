package bck_instapic.payment;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.model.*;
import com.stripe.param.*;

import java.util.Optional;

@Service
public class StripeService {
    @Value("${stripe.checkout.return.presignedUrl}")
    private String checkoutReturnUrl;

    public Session createCheckoutSession(String priceId) {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setPrice(priceId)
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                        .setReturnUrl(checkoutReturnUrl + "session_id={CHECKOUT_SESSION_ID}")
                        .build();
        try {
            return Session.create(params);
        } catch (StripeException e) {
            // TODO: create custom exception and manage it
            throw new RuntimeException(e);
        }
    }

}
