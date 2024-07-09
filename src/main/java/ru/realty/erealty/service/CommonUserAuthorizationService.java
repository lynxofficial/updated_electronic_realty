package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.realty.erealty.entity.User;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonUserAuthorizationService {
    private final UserSearchingService userSearchingService;

    public void setCommonUser(final Principal principal, final Model model) {
        Optional.ofNullable(principal)
                .map(Principal::getName)
                .filter(name -> !name.isEmpty())
                .ifPresent(email -> {
                    User user = userSearchingService.findByEmail(email);
                    model.addAttribute("user", user);
                });
    }
}
