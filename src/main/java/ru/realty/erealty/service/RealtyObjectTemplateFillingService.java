package ru.realty.erealty.service;

import org.springframework.ui.Model;
import ru.realty.erealty.exception.RealtyObjectNotFoundException;

public interface RealtyObjectTemplateFillingService {

    void fillRealtyObjectTemplate(Model model);

    void fillBuyRealtyObjectTemplate(Model model, String realtyObjectId) throws RealtyObjectNotFoundException;

    void fillDeleteRealtyObjectsTemplate(Model model);
}
