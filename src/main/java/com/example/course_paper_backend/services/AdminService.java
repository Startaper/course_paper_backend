package com.example.course_paper_backend.services;

import com.example.course_paper_backend.entities.*;
import com.example.course_paper_backend.enums.AreaType;
import com.example.course_paper_backend.enums.EducationType;
import com.example.course_paper_backend.exceptions.NotFoundException;
import com.example.course_paper_backend.repositories.ApplicantRepo;
import com.example.course_paper_backend.repositories.ResumeRepo;
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
public class AdminService {

    private final ResumeRepo resumeRepo;
    private final ApplicantRepo applicantRepo;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat();

    @Autowired
    public AdminService(ResumeRepo resumeRepo, ApplicantRepo applicantRepo) {
        this.resumeRepo = resumeRepo;
        this.applicantRepo = applicantRepo;
    }

    public void deleteById(UUID id) throws NotFoundException {
        if (!resumeRepo.existsById(id)) {
            throw new NotFoundException("Резюме с указанным id не найден!");
        }
        resumeRepo.deleteById(id);
    }

    public void deleteAll() {
        resumeRepo.deleteAll();
    }

    public List<ResumeEntity> addAll(JSONArray jsonArray) throws JSONException, ParseException {
        List<ResumeEntity> resumes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            resumes.add(add(jsonArray.getJSONObject(i).getJSONObject("resume")));
        }
        return resumes;
    }

    public ResumeEntity add(JSONObject json) throws JSONException, ParseException {
        ResumeEntity resume = new ResumeEntity(json, dateFormat);
        resume.setId(UUID.fromString(json.getString("id")));
        // Проверка существования такого соискателя в базе
        // Если в БД есть схождение, то к "профилю" соискателя добавляется новое резюме
        // Иначе создается новый соискатель в БД.
        ApplicantEntity applicantEntity = applicantRepo.findByFirstNameAndLastNameAndMiddleNameAndBirthDate(json.getString("last_name"),
                json.getString("first_name"), json.getString("middle_name"), dateFormat.parse(json.getString("birth_date")))
                .orElse(getApplicantFromJSONObject(json));
        resume.setApplicant(applicantEntity);

        resume.setSchedules(convertFromJsonArrayToString(json.getJSONArray("schedules")));
        resume.setEmployments(convertFromJsonArrayToString(json.getJSONArray("employments")));
        resume.setHiddenFields(convertFromJsonArrayToString(json.getJSONArray("hidden_fields")));
        resume.setDriverLicenseTypes(convertFromJsonArrayToString(json.getJSONArray("driver_license_types")));
        resume.setPaidServices(convertFromJSONObjectToPaidServicesEntityList(json.getJSONArray("paid_services"), resume));
        resume.setSpecializations(convertFromJSONObjectToSpecializationEntityList(json.getJSONArray("specializations"), resume));
        resume.setRecommendations(convertFromJSONObjectToRecommendationEntityList(json.getJSONArray("recommendations"), resume));
        resume.setExperience(convertFromJSONObjectToExperienceEntityList(json.getJSONArray("experience"), resume, resume.getApplicant()));

        return resumeRepo.save(resume);
    }

    private ApplicantEntity getApplicantFromJSONObject(JSONObject jsonObject) throws JSONException, ParseException {
        ApplicantEntity applicant = new ApplicantEntity(jsonObject, dateFormat);
        applicant.setSite(convertFromJSONObjectToSiteEntityList(jsonObject.getJSONArray("site"), applicant));
        applicant.setLanguages(convertFromJSONObjectToLanguageEntityList(jsonObject.getJSONArray("language"), applicant));
        applicant.setCertificates(convertFromJSONObjectToCertificateEntityList(jsonObject.getJSONArray("certificate"), applicant));
        applicant.setContacts(convertFromJSONObjectToContactEntityList(jsonObject.getJSONArray("contact"), applicant));
        applicant.setCitizenship(convertFromJSONObjectToCitizenshipEntityList(jsonObject.getJSONArray("citizenship"), applicant));
        applicant.setWorkTickets(convertFromJSONObjectToWorkTicketEntityList(jsonObject.getJSONArray("work_ticket"), applicant));

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
        return applicant;
    }

    private String convertFromJsonArrayToString(JSONArray jsonArray) throws JSONException {
        StringBuilder string = new StringBuilder();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            string.append(jsonArray.getJSONObject(i).getString("id"));
        }
        return string.toString();
    }

    private List<PaidServicesEntity> convertFromJSONObjectToPaidServicesEntityList(JSONArray jsonArray, ResumeEntity resume) throws JSONException {
        List<PaidServicesEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new PaidServicesEntity(jsonArray.getJSONObject(i), resume));
        }
        return list;
    }

    private List<SpecializationEntity> convertFromJSONObjectToSpecializationEntityList(JSONArray jsonArray, ResumeEntity resume) throws JSONException {
        List<SpecializationEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new SpecializationEntity(jsonArray.getJSONObject(i), resume));
        }
        return list;
    }

    private List<RecommendationEntity> convertFromJSONObjectToRecommendationEntityList(JSONArray jsonArray, ResumeEntity resume) throws JSONException {
        List<RecommendationEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new RecommendationEntity(jsonArray.getJSONObject(i), resume));
        }
        return list;
    }

    private List<ExperienceEntity> convertFromJSONObjectToExperienceEntityList(JSONArray jsonArray, ResumeEntity resume, ApplicantEntity applicant) throws JSONException, ParseException {
        List<ExperienceEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new ExperienceEntity(jsonArray.getJSONObject(i), resume, applicant, dateFormat));
        }
        return list;
    }

    private List<ContactEntity> convertFromJSONObjectToContactEntityList(JSONArray jsonArray, ApplicantEntity applicant) throws JSONException {
        List<ContactEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new ContactEntity(jsonArray.getJSONObject(i), applicant));
        }
        return list;
    }

    private List<LanguageEntity> convertFromJSONObjectToLanguageEntityList(JSONArray jsonArray, ApplicantEntity applicant) throws JSONException {
        List<LanguageEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new LanguageEntity(jsonArray.getJSONObject(i), applicant));
        }
        return list;
    }

    private List<SiteEntity> convertFromJSONObjectToSiteEntityList(JSONArray jsonArray, ApplicantEntity applicant) throws JSONException {
        List<SiteEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new SiteEntity(jsonArray.getJSONObject(i), applicant));
        }
        return list;
    }

    private List<CertificateEntity> convertFromJSONObjectToCertificateEntityList(JSONArray jsonArray, ApplicantEntity applicant) throws JSONException, ParseException {
        List<CertificateEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new CertificateEntity(jsonArray.getJSONObject(i), dateFormat, applicant));
        }
        return list;
    }

    private List<AreaEntity> convertFromJSONObjectToCitizenshipEntityList(JSONArray jsonArray, ApplicantEntity applicant) throws JSONException {
        List<AreaEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new AreaEntity(jsonArray.getJSONObject(i), AreaType.CITIZENSHIP, applicant));
        }
        return list;
    }

    private List<AreaEntity> convertFromJSONObjectToWorkTicketEntityList(JSONArray jsonArray, ApplicantEntity applicant) throws JSONException {
        List<AreaEntity> list = new ArrayList<>();
        if (jsonArray == null) return null;
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new AreaEntity(jsonArray.getJSONObject(i), AreaType.WORK_TICKET, applicant));
        }
        return list;
    }

    private List<EducationEntity> convertFromJSONObjectToEducationEntityList(JSONArray jsonArray, EducationType type, ApplicantEntity applicant) throws JSONException {
        List<EducationEntity> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(new EducationEntity(jsonArray.getJSONObject(i), type, applicant));
        }
        return list;
    }

}
