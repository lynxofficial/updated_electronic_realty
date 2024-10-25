package ru.realty.erealty.service.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.mapper.UserMapper;
import ru.realty.erealty.service.user.UserSearchingService;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommonUserAuthorizationService {
    private final UserSearchingService userSearchingService;
    private final UserMapper userMapper;

    public void setCommonUser(final Principal principal, final Model model) {
        Optional.ofNullable(principal)
                .map(Principal::getName)
                .filter(name -> !name.isEmpty())
                .ifPresent(email -> {
                    User user = userSearchingService.findByEmail(email);
                    model.addAttribute("user", userMapper.toUserResponse(user));
                });
    }
}
