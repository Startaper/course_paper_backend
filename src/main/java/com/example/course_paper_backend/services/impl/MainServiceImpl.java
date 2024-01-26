package com.example.course_paper_backend.services.impl;

import com.example.course_paper_backend.entities.ResumeEntity;
import com.example.course_paper_backend.enums.*;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.model.Resume;
import com.example.course_paper_backend.repositories.ResumeRepo;
import com.example.course_paper_backend.services.BasicService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс описывающий логику работы основного сервиса.
 */
@Service
public class MainServiceImpl implements BasicService<Resume, ResumeEntity> {

    private final ResumeRepo resumeRepo;

    @Autowired
    public MainServiceImpl(ResumeRepo resumeRepo) {
        this.resumeRepo = resumeRepo;
    }

    /**
     * Метод возвращает объект по запрашиваемому id
     *
     * @param resumeId UUID
     * @return ResumeEntity
     * @throws NotFoundException если не удалось найти в БД объект по указанному id
     */
    @Override
    public ResumeEntity get(UUID resumeId) throws NotFoundException {
        return resumeRepo.findById(resumeId)
                .orElseThrow(() -> new NotFoundException("Резюме с указанным id не найден!"));
    }

    /**
     * Метод возвращает список объектов по запрошенным фильтрам.
     * Если не указанны фильтры, то возвращает все объекты из БД
     *
     * @param jsonString JSONObject
     * @return List<ResumeEntity>
     * @throws JSONException если при парсинге json возникает ошибка
     */
    public List<ResumeEntity> getAllByFilter(String jsonString) throws JSONException {
        List<ResumeEntity> result = new ArrayList<>();
        resumeRepo.findAll().forEach(result::add);

        if (jsonString == null || jsonString.isBlank()) {
            return result;
        }
        JSONObject jsonObject = new JSONObject(jsonString);

        String status = jsonObject.optString("status");
        String gender = jsonObject.optString("gender");
        String travelTime = jsonObject.optString("travelTime");
        String businessTripReadiness = jsonObject.optString("businessTripReadiness");
        String educationLevel = jsonObject.optString("educationLevel");
        String areaName = jsonObject.optString("areaName");
        int ageStart = jsonObject.optInt("ageStart", -1);
        int ageEnd = jsonObject.optInt("ageEnd", -1);
        int salaryStart = jsonObject.optInt("salaryStart", -1);
        int salaryEnd = jsonObject.optInt("salaryEnd", -1);

        if (!areaName.isBlank()) {
            result.retainAll(resumeRepo.findAllByApplicant_Area(areaName));
        }
        if (!status.isBlank()) {
            result.retainAll(resumeRepo.findAllByStatus(ResumeStatus.valueOf(status)));
        }
        if (!gender.isBlank()) {
            result.retainAll(resumeRepo.findAllByApplicant_Gender(Gender.valueOf(gender)));
        }
        if (!travelTime.isBlank()) {
            result.retainAll(resumeRepo.findAllByTravelTime(TravelTimeType.valueOf(travelTime)));
        }
        if (!educationLevel.isBlank()) {
            result.retainAll(resumeRepo.findAllByApplicant_EducationLevel(EducationLevel.valueOf(educationLevel)));
        }
        if (!businessTripReadiness.isBlank()) {
            result.retainAll(resumeRepo.findAllByBusinessTripReadiness(BusinessTripReadinessType.valueOf(businessTripReadiness)));
        }
        int start = 0;
        int end = Integer.MAX_VALUE;

        // Age between start, end
        if (ageStart != -1) {
            start = ageStart;
        }
        if (ageEnd != -1) {
            end = ageEnd;
        }
        result.retainAll(resumeRepo.findAllByApplicant_AgeBetween(start, end));

        start = 0;
        end = Integer.MAX_VALUE;

        // Salary between start, end
        if (salaryStart != -1) {
            start = salaryStart;
        }
        if (salaryEnd != -1) {
            end = salaryEnd;
        }
        result.retainAll(resumeRepo.findAllBySalaryBetween(start, end));

        return result;
    }

    /**
     * Метод возвращает объект с обновленным статусом
     *
     * @param resumeId UUID
     * @param status   ResumeStatus
     * @return ResumeEntity
     * @throws NotFoundException если не удалось найти в БД объект по указанному id
     */
    @Override
    public ResumeEntity updateStatus(UUID resumeId, ResumeStatus status) throws NotFoundException {
        ResumeEntity resumeEntity = resumeRepo.findById(resumeId)
                .orElseThrow(() -> new NotFoundException("Резюме с указанным id не найден!"));
        resumeEntity.setStatus(status);
        return resumeRepo.save(resumeEntity);
    }

    /**
     * Метод имплементирован из BasicService, но в данном классе не используется
     *
     * @param model Resume
     * @return ResumeEntity
     * @deprecated
     */
    @Override
    @Deprecated
    public ResumeEntity add(Resume model) {
        return null;
    }

}
