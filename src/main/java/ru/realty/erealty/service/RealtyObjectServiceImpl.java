package ru.realty.erealty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.realty.erealty.constants.FilePath;
import ru.realty.erealty.entity.RealtyObject;
import ru.realty.erealty.entity.User;
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

    @Override
    public List<RealtyObject> findAll() {
        return realtyObjectRepository.findAll();
    }

    @Override
    public List<RealtyObject> findAllByUser(User user) {
        return realtyObjectRepository.findAllByUser(user);
    }

    @Override
    public RealtyObject buyRealtyObject(String realtyObjectId) {
        RealtyObject realtyObject = null;
        Optional<RealtyObject> optionalRealtyObject = realtyObjectRepository.findById(Integer.valueOf(realtyObjectId));
        if (optionalRealtyObject.isPresent()) {
            realtyObject = optionalRealtyObject.get();
        }
        return realtyObject;
    }

    @Override
    public void sellRealtyObject(User user, RealtyObject realtyObject, MultipartFile file) throws IOException {
        realtyObject.setUser(user);
        Path fileNameAndPath = Paths.get(FilePath.UPLOAD_DIRECTORY, file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        realtyObject.setImageUrl(fileNameAndPath.getFileName().toString());
        if (realtyObject.getPrice() == null) {
            realtyObject.setPrice(BigDecimal.ZERO);
        }
        realtyObjectRepository.save(realtyObject);
    }

    @Override
    public Boolean buyRealtyObjectWithDigitalSignature(RealtyObject realtyObject) {
        Optional<RealtyObject> optionalRealtyObject = realtyObjectRepository.findById(realtyObject.getRealtyObjectId());
        RealtyObject currentRealtyObject = null;
        if (optionalRealtyObject.isPresent()) {
            currentRealtyObject = optionalRealtyObject.get();
        }
        assert currentRealtyObject != null;
        Optional<User> optionalUser = userRepository.findById(currentRealtyObject.getUser().getUserId());
        User targetUser = null;
        if (optionalUser.isPresent()) {
            targetUser = optionalUser.get();
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User currentUser = userRepository.findByEmail(email);
        if (currentUser.getBalance() != null) {
            assert targetUser != null;
            if (currentUser.getBalance().subtract(currentRealtyObject.getPrice()).compareTo(BigDecimal.ZERO) < 0
                    || currentUser.getDigitalSignature() == null
                    || currentUser.getUserId().equals(targetUser.getUserId())) {
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
    public void deleteRealtyObject(RealtyObject realtyObject) {
        Optional<RealtyObject> optionalRealtyObject = realtyObjectRepository.findById(realtyObject.getRealtyObjectId());
        RealtyObject currentRealtyObject = null;
        if (optionalRealtyObject.isPresent()) {
            currentRealtyObject = optionalRealtyObject.get();
        }
        assert currentRealtyObject != null;
        User user = currentRealtyObject.getUser();
        assert user != null;
        user.getRealtyObjects().remove(currentRealtyObject);
        currentRealtyObject.setUser(null);
        realtyObjectRepository.delete(currentRealtyObject);
    }
}
