package com.example.course_paper_backend.services.impl;

import com.example.course_paper_backend.entities.*;
import com.example.course_paper_backend.enums.EducationType;
import com.example.course_paper_backend.enums.ResumeStatus;
import com.example.course_paper_backend.exceptions.AlreadyExistsException;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.repositories.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl {

    private final ResumeRepo resumeRepo;
    private final ApplicantRepo applicantRepo;
    private final PaidServicesRepo paidServicesRepo;
    private final ExperienceRepo experienceRepo;
    private final LanguageRepo languageRepo;
    private final SiteRepo siteRepo;
    private final EducationRepo educationRepo;
    private final RecommendationRepo recommendationRepo;
    private final CertificateRepo certificateRepo;
    private final ContactRepo contactRepo;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public AdminServiceImpl(ResumeRepo resumeRepo, ApplicantRepo applicantRepo, PaidServicesRepo paidServicesRepo,
                            ExperienceRepo experienceRepo, LanguageRepo languageRepo, SiteRepo siteRepo,
                            EducationRepo educationRepo, RecommendationRepo recommendationRepo,
                            CertificateRepo certificateRepo, ContactRepo contactRepo) {
        this.resumeRepo = resumeRepo;
        this.applicantRepo = applicantRepo;
        this.paidServicesRepo = paidServicesRepo;
        this.experienceRepo = experienceRepo;
        this.languageRepo = languageRepo;
        this.siteRepo = siteRepo;
        this.educationRepo = educationRepo;
        this.recommendationRepo = recommendationRepo;
        this.certificateRepo = certificateRepo;
        this.contactRepo = contactRepo;
    }

    /**
     * Метод передает запрос на следующий слой сервиса для удаления объекта из БД по id
     *
     * @param id UUID
     * @throws NotFoundException если не удалось найти в БД объект по указанному id
     */
    public void deleteById(UUID id) throws NotFoundException {
        if (!resumeRepo.existsById(id)) {
            throw new NotFoundException("Резюме с указанным id - '" + id + "' не найден!");
        }
        resumeRepo.deleteById(id);
    }

    /**
     * Метод передает запрос на следующий слой сервиса для удаления всех объектов из БД
     */
    public void deleteAll() {
        resumeRepo.deleteAll();
        applicantRepo.deleteAll();
    }

    /**
     * Метод получает список объектов от контролера, проверяет их и сохраняет в БД.
     * Метод возвращает список сохраненных в БД объектов
     *
     * @param jsonArray String
     * @return List<ResumeEntity>
     * @throws JSONException          если при парсинге json возникает ошибка
     * @throws ParseException         если при парсинге дат возникает ошибка
     * @throws AlreadyExistsException если при сохранении одного и то же объекта более 1 раза возникает ошибка
     */
    public List<ResumeEntity> addAll(JSONArray jsonArray) throws JSONException, ParseException, AlreadyExistsException {
        List<ResumeEntity> resumes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            resumes.add(add(jsonArray.getJSONObject(i).getJSONObject("resume"), jsonArray.getJSONObject(i).getString("rating")));
        }
        return resumes;
    }

    /**
     * Метод получает объект, проверяет его на корректность, преобразует необходимые данные и сохраняет его в БД.
     * Метод возвращает сохраненный объект.
     *
     * @param json JSONObject
     * @return ResumeEntity
     * @throws JSONException          если при парсинге json возникает ошибка
     * @throws ParseException         если при парсинге дат возникает ошибка
     * @throws AlreadyExistsException если при сохранении одного и то же объекта более 1 раза возникает ошибка
     */
    public ResumeEntity add(JSONObject json, String rating) throws JSONException, ParseException, AlreadyExistsException {
        ResumeEntity resume = new ResumeEntity(json, dateFormat);

        if (resumeRepo.existsByExternalId(json.getString("id"))) {
            throw new AlreadyExistsException("Резюме с id - '" + json.getString("id") + "' уже существует в БД!");
        }

//        resume.setId(UUID.fromString(json.getString("id")));
        resume.setId(UUID.randomUUID());
        resume.setExternalId(json.getString("id"));
        resume.setRating(Float.parseFloat(rating));

        // Проверка существования такого соискателя в базе
        // Если в БД есть схождение, то к "профилю" соискателя добавляется новое резюме
        // Иначе создается новый соискатель в БД.
        ApplicantEntity applicantEntity = applicantRepo.findByExternalId(getExternalIdByJson(json));

        if (applicantEntity == null) {
            applicantEntity = getApplicantFromJSONObject(json);
        }
        resume.setApplicant(applicantEntity);

        resume.setSchedules(convertFromJsonArrayToString(json.getJSONArray("schedules"), "name", true));
        resume.setEmployments(convertFromJsonArrayToString(json.getJSONArray("employments"), "name", true));
        resume.setHiddenFields(convertFromJsonArrayToString(json.getJSONArray("hidden_fields"), "name", true));
        resume.setDriverLicenseTypes(convertFromJsonArrayToString(json.getJSONArray("driver_license_types"), "id", true));
        resume.setStatus(ResumeStatus.NEW);

        resume = resumeRepo.save(resume);

        resume.setPaidServices(convertFromJSONObjectToPaidServicesEntityList(json.getJSONArray("paid_services"), resume));
        resume.setRecommendations(convertFromJSONObjectToRecommendationEntityList(json.getJSONArray("recommendation"), resume));
        resume.setExperience(convertFromJSONObjectToExperienceEntityList(json.getJSONArray("experience"), resume));

        return resumeRepo.save(resume);
    }

    /**
     * Метод генерирует externalId для объекта Applicant.
     *
     * @param jsonObject JSONObject
     * @return String
     * @throws JSONException если при парсинге json возникает ошибка
     */
    private String getExternalIdByJson(JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt("age") + "_" +
                jsonObject.getString("last_name") +
                jsonObject.getString("first_name") +
                jsonObject.getString("middle_name");
    }

    /**
     * Метод создает экземпляр сущности Applicant, и в случае успешного создания сохраняет его в БД.
     * Метод возвращает сохраненный объект
     *
     * @param jsonObject JSONObject
     * @return ApplicantEntity
     * @throws JSONException  если при парсинге json возникает ошибка
     * @throws ParseException если при парсинге дат возникает ошибка
     */
    private ApplicantEntity getApplicantFromJSONObject(JSONObject jsonObject) throws JSONException, ParseException {
        ApplicantEntity applicant = new ApplicantEntity(jsonObject, dateFormat);
        applicant.setId(UUID.randomUUID());
        applicant.setExternalId(getExternalIdByJson(jsonObject));
        applicant = applicantRepo.save(applicant);

        applicant.setSite(convertFromJSONObjectToSiteEntityList(jsonObject.getJSONArray("site"), applicant));
        applicant.setLanguages(convertFromJSONObjectToLanguageEntityList(jsonObject.getJSONArray("language"), applicant));
        applicant.setCertificates(convertFromJSONObjectToCertificateEntityList(jsonObject.getJSONArray("certificate"), applicant));
        applicant.setContacts(convertFromJSONObjectToContactEntityList(jsonObject.getJSONArray("contact"), applicant));
        applicant.setCitizenship(convertFromJsonArrayToString(jsonObject.getJSONArray("citizenship"), "name", false));
        applicant.setWorkTickets(convertFromJsonArrayToString(jsonObject.getJSONArray("citizenship"), "name", false));

        List<EducationEntity> educations = new ArrayList<>();
        JSONArray jsonArray;
        // PRIMARY
        jsonArray = jsonObject.getJSONObject("education").getJSONArray("primary");
        if (jsonArray != null && jsonArray.length() != 0) {
            educations.addAll(convertFromJSONObjectToEducationEntityList(jsonArray, EducationType.PRIMARY, applicant));
        }
        // ADDITIONAL
        jsonArray = jsonObject.getJSONObject("education").getJSONArray("additional");
        if (jsonArray != null && jsonArray.length() != 0) {
            educations.addAll(convertFromJSONObjectToEducationEntityList(jsonArray, EducationType.ADDITIONAL, applicant));
        }
        // ATTESTATION
        jsonArray = jsonObject.getJSONObject("education").getJSONArray("attestation");
        if (jsonArray != null && jsonArray.length() != 0) {
            educations.addAll(convertFromJSONObjectToEducationEntityList(jsonArray, EducationType.ATTESTATION, applicant));
        }
        // ELEMENTARY
        jsonArray = jsonObject.getJSONObject("education").getJSONArray("elementary");
        if (jsonArray != null && jsonArray.length() != 0) {
            educations.addAll(convertFromJSONObjectToEducationEntityList(jsonArray, EducationType.ELEMENTARY, applicant));
        }
        applicant.setEducations(educations);
        return applicantRepo.save(applicant);
    }

    /**
     * Метод конвертирует массив в строку
     *
     * @param jsonArray JSONArray
     * @return String
     * @throws JSONException если при парсинге json возникает ошибка
     */
    private String convertFromJsonArrayToString(JSONArray jsonArray, String param, boolean upperCase) throws JSONException {
        StringBuilder string = new StringBuilder();
        if (jsonArray == null || jsonArray.length() == 0) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            if (upperCase) {
                string.append(jsonArray.getJSONObject(i).getString(param).toUpperCase()).append(", ");
            } else {
                string.append(jsonArray.getJSONObject(i).getString(param)).append(", ");
            }
        }
        return string.substring(0, string.length() - 2);
    }

    private List<PaidServicesEntity> convertFromJSONObjectToPaidServicesEntityList(JSONArray jsonArray, ResumeEntity resume) throws JSONException {
        List<PaidServicesEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(paidServicesRepo.save(new PaidServicesEntity(jsonArray.getJSONObject(i), resume)));
        }
        return list;
    }

    private List<RecommendationEntity> convertFromJSONObjectToRecommendationEntityList(JSONArray jsonArray, ResumeEntity resume) throws JSONException {
        List<RecommendationEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(recommendationRepo.save(new RecommendationEntity(jsonArray.getJSONObject(i), resume)));
        }
        return list;
    }

    private List<ExperienceEntity> convertFromJSONObjectToExperienceEntityList(JSONArray jsonArray, ResumeEntity resume) throws JSONException, ParseException {
        List<ExperienceEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(experienceRepo.save(new ExperienceEntity(jsonArray.getJSONObject(i), resume, dateFormat)));
        }
        return list;
    }

    private List<ContactEntity> convertFromJSONObjectToContactEntityList(JSONArray jsonArray, ApplicantEntity applicant) throws JSONException {
        List<ContactEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(contactRepo.save(new ContactEntity(jsonArray.getJSONObject(i), applicant)));
        }
        return list;
    }

    private List<LanguageEntity> convertFromJSONObjectToLanguageEntityList(JSONArray jsonArray, ApplicantEntity applicant) throws JSONException {
        List<LanguageEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(languageRepo.save(new LanguageEntity(jsonArray.getJSONObject(i), applicant)));
        }
        return list;
    }

    private List<SiteEntity> convertFromJSONObjectToSiteEntityList(JSONArray jsonArray, ApplicantEntity applicant) throws JSONException {
        List<SiteEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(siteRepo.save(new SiteEntity(jsonArray.getJSONObject(i), applicant)));
        }
        return list;
    }

    private List<CertificateEntity> convertFromJSONObjectToCertificateEntityList(JSONArray jsonArray, ApplicantEntity applicant) throws JSONException, ParseException {
        List<CertificateEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(certificateRepo.save(new CertificateEntity(jsonArray.getJSONObject(i), dateFormat, applicant)));
        }
        return list;
    }

    private List<EducationEntity> convertFromJSONObjectToEducationEntityList(JSONArray jsonArray, EducationType type, ApplicantEntity applicant) throws JSONException {
        List<EducationEntity> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(educationRepo.save(new EducationEntity(jsonArray.getJSONObject(i), type, applicant)));
        }
        return list;
    }

}
