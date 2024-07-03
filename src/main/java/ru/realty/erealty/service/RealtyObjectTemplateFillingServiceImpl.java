package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.exception.RealtyObjectNotFoundException;
import ru.realty.erealty.repository.RealtyObjectRepository;
import ru.realty.erealty.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RealtyObjectTemplateFillingServiceImpl implements RealtyObjectTemplateFillingService {
    private final RealtyObjectRepository realtyObjectRepository;
    private final RealtyObjectService realtyObjectService;
    private final UserRepository userRepository;

    @Override
    public void fillRealtyObjectTemplate(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        List<RealtyObject> realtyObjects = realtyObjectRepository.findAllByUser(user);
        model.addAttribute("realtyObjects", realtyObjects);
    }

    @Override
    public void fillBuyRealtyObjectTemplate(Model model, String realtyObjectId) throws RealtyObjectNotFoundException {
        model.addAttribute("realtyObject", realtyObjectService.buyRealtyObject(realtyObjectId));
    }

    @Override
    public void fillDeleteRealtyObjectsTemplate(Model model) {
        model.addAttribute("realtyObjects", realtyObjectRepository.findAll());
    }
}
