package com.example.course_paper_backend.services.impl;

import com.example.course_paper_backend.entities.*;
import com.example.course_paper_backend.enums.AreaType;
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
    private final SpecializationRepo specializationRepo;
    private final ExperienceRepo experienceRepo;
    private final LanguageRepo languageRepo;
    private final AreaCitiRepo areaCitiRepo;
    private final SiteRepo siteRepo;
    private final EducationRepo educationRepo;
    private final RecommendationRepo recommendationRepo;
    private final CertificateRepo certificateRepo;
    private final ContactRepo contactRepo;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public AdminServiceImpl(ResumeRepo resumeRepo, ApplicantRepo applicantRepo, PaidServicesRepo paidServicesRepo,
                            SpecializationRepo specializationRepo, ExperienceRepo experienceRepo,
                            LanguageRepo languageRepo, AreaCitiRepo areaCitiRepo, SiteRepo siteRepo,
                            EducationRepo educationRepo, RecommendationRepo recommendationRepo,
                            CertificateRepo certificateRepo, ContactRepo contactRepo) {
        this.resumeRepo = resumeRepo;
        this.applicantRepo = applicantRepo;
        this.paidServicesRepo = paidServicesRepo;
        this.specializationRepo = specializationRepo;
        this.experienceRepo = experienceRepo;
        this.languageRepo = languageRepo;
        this.areaCitiRepo = areaCitiRepo;
        this.siteRepo = siteRepo;
        this.educationRepo = educationRepo;
        this.recommendationRepo = recommendationRepo;
        this.certificateRepo = certificateRepo;
        this.contactRepo = contactRepo;
    }

    public void deleteById(UUID id) throws NotFoundException {
        if (!resumeRepo.existsById(id)) {
            throw new NotFoundException("Резюме с указанным id не найден!");
        }
        resumeRepo.deleteById(id);
    }

    public void deleteAll() {
        resumeRepo.deleteAll();
        applicantRepo.deleteAll();
    }

    public List<ResumeEntity> addAll(JSONArray jsonArray) throws JSONException, ParseException, AlreadyExistsException {
        List<ResumeEntity> resumes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            resumes.add(add(jsonArray.getJSONObject(i).getJSONObject("resume")));
        }
        return resumes;
    }

    public ResumeEntity add(JSONObject json) throws JSONException, ParseException, AlreadyExistsException {
        ResumeEntity resume = new ResumeEntity(json, dateFormat);

        if (resumeRepo.existsByExternalId(json.getString("id"))) {
            throw new AlreadyExistsException("Такое резюме уже существует в БД!");
        }

//        resume.setId(UUID.fromString(json.getString("id")));
        resume.setId(UUID.randomUUID());
        resume.setExternalId(json.getString("id"));

        // Проверка существования такого соискателя в базе
        // Если в БД есть схождение, то к "профилю" соискателя добавляется новое резюме
        // Иначе создается новый соискатель в БД.
        ApplicantEntity applicantEntity = applicantRepo.findByExternalId(getExternalIdByJson(json));

        if (applicantEntity == null) {
            applicantEntity = getApplicantFromJSONObject(json);
        }
        resume.setApplicant(applicantEntity);

        resume.setSchedules(convertFromJsonArrayToString(json.getJSONArray("schedules")));
        resume.setEmployments(convertFromJsonArrayToString(json.getJSONArray("employments")));
        resume.setHiddenFields(convertFromJsonArrayToString(json.getJSONArray("hidden_fields")));
        resume.setDriverLicenseTypes(convertFromJsonArrayToString(json.getJSONArray("driver_license_types")));
        resume.setStatus(ResumeStatus.NEW);

        resume = resumeRepo.save(resume);

        resume.setPaidServices(convertFromJSONObjectToPaidServicesEntityList(json.getJSONArray("paid_services"), resume));
        resume.setSpecializations(convertFromJSONObjectToSpecializationEntityList(json.getJSONArray("specialization"), resume));
        resume.setRecommendations(convertFromJSONObjectToRecommendationEntityList(json.getJSONArray("recommendation"), resume));
        resume.setExperience(convertFromJSONObjectToExperienceEntityList(json.getJSONArray("experience"), resume, resume.getApplicant()));

        return resumeRepo.save(resume);
    }

    private String getExternalIdByJson(JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt("age") + "_" +
                jsonObject.getString("last_name") +
                jsonObject.getString("first_name") +
                jsonObject.getString("middle_name");
    }

    private ApplicantEntity getApplicantFromJSONObject(JSONObject jsonObject) throws JSONException, ParseException {
        ApplicantEntity applicant = new ApplicantEntity(jsonObject, dateFormat);
        applicant.setId(UUID.randomUUID());
        applicant.setExternalId(getExternalIdByJson(jsonObject));
        applicant = applicantRepo.save(applicant);

        applicant.setSite(convertFromJSONObjectToSiteEntityList(jsonObject.getJSONArray("site"), applicant));
        applicant.setLanguages(convertFromJSONObjectToLanguageEntityList(jsonObject.getJSONArray("language"), applicant));
        applicant.setCertificates(convertFromJSONObjectToCertificateEntityList(jsonObject.getJSONArray("certificate"), applicant));
        applicant.setContacts(convertFromJSONObjectToContactEntityList(jsonObject.getJSONArray("contact"), applicant));
        applicant.setCitizenship(convertFromJSONObjectToAreaCitiEntityList(jsonObject.getJSONArray("citizenship"), AreaType.CITIZENSHIP, applicant));
        applicant.setWorkTickets(convertFromJSONObjectToAreaCitiEntityList(jsonObject.getJSONArray("work_ticket"), AreaType.WORK_TICKET, applicant));

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

    private String convertFromJsonArrayToString(JSONArray jsonArray) throws JSONException {
        StringBuilder string = new StringBuilder();
        if (jsonArray == null || jsonArray.length() == 0) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            string.append(jsonArray.getJSONObject(i).getString("id").toUpperCase());
        }
        return string.toString();
    }

    private List<PaidServicesEntity> convertFromJSONObjectToPaidServicesEntityList(JSONArray jsonArray, ResumeEntity resume) throws JSONException {
        List<PaidServicesEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(paidServicesRepo.save(new PaidServicesEntity(jsonArray.getJSONObject(i), resume)));
        }
        return list;
    }

    private List<SpecializationEntity> convertFromJSONObjectToSpecializationEntityList(JSONArray jsonArray, ResumeEntity resume) throws JSONException {
        List<SpecializationEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(specializationRepo.save(new SpecializationEntity(jsonArray.getJSONObject(i), resume)));
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

    private List<ExperienceEntity> convertFromJSONObjectToExperienceEntityList(JSONArray jsonArray, ResumeEntity resume, ApplicantEntity applicant) throws JSONException, ParseException {
        List<ExperienceEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(experienceRepo.save(new ExperienceEntity(jsonArray.getJSONObject(i), resume, applicant, dateFormat)));
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

    private List<AreaCitiEntity> convertFromJSONObjectToAreaCitiEntityList(JSONArray jsonArray, AreaType type, ApplicantEntity applicant) throws JSONException {
        List<AreaCitiEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(areaCitiRepo.save(new AreaCitiEntity(jsonArray.getJSONObject(i), type, applicant)));
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
