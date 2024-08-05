package ru.realty.erealty.service.template.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import ru.realty.erealty.mapper.UserMapper;
import ru.realty.erealty.repository.UserRepository;
import ru.realty.erealty.service.template.user.UserTemplateFillingService;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "UserTemplateFillingServiceImpl")
public class UserTemplateFillingServiceImpl implements UserTemplateFillingService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public void fillDeleteUserTemplate(final Model model) {
        model.addAttribute("users", userMapper.toUserResponseList(userRepository.findAll()));
    }
}
