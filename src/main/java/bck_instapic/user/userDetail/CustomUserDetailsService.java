package bck_instapic.user.userDetail;

import bck_instapic.authentication.UserDetails.CustomUserDetails;
import bck_instapic.user.service.UserService;
import bck_instapic.user.service.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDTO user = userService.loadByEmail(email);
        if (user != null) {
            return new CustomUserDetails(user);
        }
        throw new UsernameNotFoundException("User not found");
    }
}
