package ru.realty.erealty.service.template.realty.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.exception.RealtyObjectNotFoundException;
import ru.realty.erealty.mapper.RealtyObjectMapper;
import ru.realty.erealty.repository.RealtyObjectRepository;
import ru.realty.erealty.repository.UserRepository;
import ru.realty.erealty.service.realty.RealtyObjectService;
import ru.realty.erealty.service.template.realty.RealtyObjectTemplateFillingService;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "RealtyObjectTemplateFillingServiceImplCache")
public class RealtyObjectTemplateFillingServiceImpl implements RealtyObjectTemplateFillingService {
    private final RealtyObjectRepository realtyObjectRepository;
    private final RealtyObjectService realtyObjectService;
    private final UserRepository userRepository;
    private final RealtyObjectMapper realtyObjectMapper;

    @Override
    @Cacheable
    public void fillRealtyObjectTemplate(final Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        List<RealtyObject> realtyObjects = realtyObjectRepository.findAllByUser(user);
        model.addAttribute("realtyObjects", realtyObjectMapper.toRealtyObjectResponseList(realtyObjects));
    }

    @Override
    @Cacheable
    public void fillBuyRealtyObjectTemplate(final Model model, final String realtyObjectId)
            throws RealtyObjectNotFoundException {
        model.addAttribute("realtyObject", realtyObjectMapper
                .toRealtyObjectResponse(realtyObjectService.buyRealtyObject(realtyObjectId)));
    }

    @Override
    @Cacheable
    public void fillDeleteRealtyObjectsTemplate(final Model model) {
        model.addAttribute("realtyObjects", realtyObjectMapper
                .toRealtyObjectResponseList(realtyObjectRepository.findAll()));
    }
}
