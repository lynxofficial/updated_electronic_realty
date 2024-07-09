package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
import ru.realty.erealty.exception.RealtyObjectNotFoundException;
import ru.realty.erealty.repository.RealtyObjectRepository;
import ru.realty.erealty.repository.UserRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RealtyObjectServiceImpl implements RealtyObjectService {
    private final RealtyObjectRepository realtyObjectRepository;
    private final UserRepository userRepository;
    private final UserVerificationService userVerificationService;

    @Override
    public List<RealtyObject> findAll() {
        return realtyObjectRepository.findAll();
    }

    @Override
    public RealtyObject buyRealtyObject(final String realtyObjectId) throws RealtyObjectNotFoundException {
        return realtyObjectRepository.findById(Integer.valueOf(realtyObjectId))
                .orElseThrow(() -> new RealtyObjectNotFoundException("Объект недвижимости не найден"));
    }

    @Override
    public void sellRealtyObject(
            final User user,
            final RealtyObject realtyObject,
            final MultipartFile file,
            final @Value("${default.image.path}") String defaultImagePath) throws IOException {
        realtyObject.setUser(user);
        Path fileNameAndPath = Paths.get(defaultImagePath, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        Optional.of(fileNameAndPath)
                .map(Path::toString)
                .ifPresent(realtyObject::setImageUrl);
        if (realtyObject.getPrice() == null) {
            realtyObject.setPrice(BigDecimal.ZERO);
        }
        realtyObjectRepository.save(realtyObject);
    }

    @Override
    public Boolean buyRealtyObjectWithDigitalSignature(final RealtyObject realtyObject)
            throws RealtyObjectNotFoundException {
        Optional<RealtyObject> optionalRealtyObject = realtyObjectRepository.findById(realtyObject.getRealtyObjectId());
        RealtyObject currentRealtyObject = optionalRealtyObject
                .orElseThrow(() -> new RealtyObjectNotFoundException("Объект недвижимости не найден"));
        Optional<User> optionalUser = userRepository.findById(currentRealtyObject.getUser().getUserId());
        User targetUser = optionalUser
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        if (currentUser.getBalance() != null) {
            if (userVerificationService.isNotPositiveBalanceOrExistsDigitalSignature(currentUser, targetUser,
                    currentRealtyObject)) {
                return false;
            }
            currentUser.setBalance(currentUser.getBalance().subtract(currentRealtyObject.getPrice()));
            targetUser.setBalance(targetUser.getBalance().add(currentRealtyObject.getPrice()));
            targetUser.getRealtyObjects().remove(currentRealtyObject);
            currentRealtyObject.setUser(null);
            userRepository.save(currentUser);
            userRepository.save(targetUser);
            realtyObjectRepository.delete(currentRealtyObject);
        }
        return true;
    }

    @Override
    public void deleteRealtyObject(final RealtyObject realtyObject) throws RealtyObjectNotFoundException {
        RealtyObject currentRealtyObject = realtyObjectRepository.findById(realtyObject.getRealtyObjectId())
                .orElseThrow(() -> new RealtyObjectNotFoundException("Объект недвижимости не найден"));
        User user = currentRealtyObject.getUser();
        assert user != null;
        user.getRealtyObjects().remove(currentRealtyObject);
        currentRealtyObject.setUser(null);
        realtyObjectRepository.delete(currentRealtyObject);
    }
}
