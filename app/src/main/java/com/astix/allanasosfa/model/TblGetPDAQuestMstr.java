
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblGetPDAQuestMstr {

    @SerializedName("QuestID")
    @Expose
    private Integer questID;
    @SerializedName("QuestCode")
    @Expose
    private String questCode;
    @SerializedName("QuestDesc")
    @Expose
    private String questDesc;
    @SerializedName("QuestType")
    @Expose
    private Integer questType;
    @SerializedName("AnsControlType")
    @Expose
    private Integer ansControlType;
    @SerializedName("AsnControlInputTypeID")
    @Expose
    private Integer asnControlInputTypeID;
    @SerializedName("AnsControlIntputTypeMinLength")
    @Expose
    private Integer ansControlIntputTypeMinLength;
    @SerializedName("AnsControlInputTypeMaxLength")
    @Expose
    private Integer ansControlInputTypeMaxLength;
    @SerializedName("AnswerHint")
    @Expose
    private String answerHint;
    @SerializedName("AnsMustRequiredFlg")
    @Expose
    private Integer ansMustRequiredFlg;
    @SerializedName("QuestBundleFlg")
    @Expose
    private Integer questBundleFlg;
    @SerializedName("ApplicationTypeID")
    @Expose
    private Integer applicationTypeID;
    @SerializedName("Sequence")
    @Expose
    private Integer sequence;
    @SerializedName("ActiveQuest")
    @Expose
    private Integer activeQuest;
    @SerializedName("LoginID")
    @Expose
    private Object loginID;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("LoginID_Modify")
    @Expose
    private Object loginIDModify;
    @SerializedName("ModifyDate")
    @Expose
    private String modifyDate;
    @SerializedName("AnsSourceTypeID")
    @Expose
    private Integer ansSourceTypeID;
    @SerializedName("AnsSourceNodeType")
    @Expose
    private Integer ansSourceNodeType;
    @SerializedName("AnsSourceOptionDep")
    @Expose
    private Integer ansSourceOptionDep;
    @SerializedName("flgPrvValue")
    @Expose
    private Integer flgPrvValue;
    @SerializedName("flgPrvVisitDependency")
    @Expose
    private Integer flgPrvVisitDependency;
    @SerializedName("flgOptionAreaDependent")
    @Expose
    private Integer flgOptionAreaDependent;
    @SerializedName("OptionAreaSource")
    @Expose
    private Object optionAreaSource;

    public Integer getFlgStoreValidation() {
        return flgStoreValidation;
    }

    public void setFlgStoreValidation(Integer flgStoreValidation) {
        this.flgStoreValidation = flgStoreValidation;
    }

    @SerializedName("flgStoreValidation")
    @Expose
    private Integer flgStoreValidation;


    public Integer getFlgStoreVisitFeedback() {
        return flgStoreVisitFeedback;
    }

    public void setFlgStoreVisitFeedback(Integer flgStoreVisitFeedback) {
        this.flgStoreVisitFeedback = flgStoreVisitFeedback;
    }

    public Integer getFlgDSMOverAllFeedback() {
        return flgDSMOverAllFeedback;
    }

    public void setFlgDSMOverAllFeedback(Integer flgDSMOverAllFeedback) {
        this.flgDSMOverAllFeedback = flgDSMOverAllFeedback;
    }

    public Integer getFlgDSMVisitFeedback() {
        return flgDSMVisitFeedback;
    }

    public void setFlgDSMVisitFeedback(Integer flgDSMVisitFeedback) {
        this.flgDSMVisitFeedback = flgDSMVisitFeedback;
    }

    public Integer getFlgNewStore() {
        return flgNewStore;
    }

    public void setFlgNewStore(Integer flgNewStore) {
        this.flgNewStore = flgNewStore;
    }

    @SerializedName("flgStoreVisitFeedback")
    @Expose
    private Integer flgStoreVisitFeedback=0;

    @SerializedName("flgDSMOverAllFeedback")
    @Expose
    private Integer flgDSMOverAllFeedback=0;

    @SerializedName("flgDSMVisitFeedback")
    @Expose
    private Integer flgDSMVisitFeedback=0;

    @SerializedName("flgNewStore")
    @Expose
    private Integer flgNewStore=0;


    public Integer getQuestID() {
        return questID;
    }

    public void setQuestID(Integer questID) {
        this.questID = questID;
    }

    public String getQuestCode() {
        return questCode;
    }

    public void setQuestCode(String questCode) {
        this.questCode = questCode;
    }

    public String getQuestDesc() {
        return questDesc;
    }

    public void setQuestDesc(String questDesc) {
        this.questDesc = questDesc;
    }

    public Integer getQuestType() {
        return questType;
    }

    public void setQuestType(Integer questType) {
        this.questType = questType;
    }

    public Integer getAnsControlType() {
        return ansControlType;
    }

    public void setAnsControlType(Integer ansControlType) {
        this.ansControlType = ansControlType;
    }

    public Integer getAsnControlInputTypeID() {
        return asnControlInputTypeID;
    }

    public void setAsnControlInputTypeID(Integer asnControlInputTypeID) {
        this.asnControlInputTypeID = asnControlInputTypeID;
    }

    public Integer getAnsControlIntputTypeMinLength() {
        return ansControlIntputTypeMinLength;
    }

    public void setAnsControlIntputTypeMinLength(Integer ansControlIntputTypeMinLength) {
        this.ansControlIntputTypeMinLength = ansControlIntputTypeMinLength;
    }

    public Integer getAnsControlInputTypeMaxLength() {
        return ansControlInputTypeMaxLength;
    }

    public void setAnsControlInputTypeMaxLength(Integer ansControlInputTypeMaxLength) {
        this.ansControlInputTypeMaxLength = ansControlInputTypeMaxLength;
    }

    public String getAnswerHint() {
        return answerHint;
    }

    public void setAnswerHint(String answerHint) {
        this.answerHint = answerHint;
    }

    public Integer getAnsMustRequiredFlg() {
        return ansMustRequiredFlg;
    }

    public void setAnsMustRequiredFlg(Integer ansMustRequiredFlg) {
        this.ansMustRequiredFlg = ansMustRequiredFlg;
    }

    public Integer getQuestBundleFlg() {
        return questBundleFlg;
    }

    public void setQuestBundleFlg(Integer questBundleFlg) {
        this.questBundleFlg = questBundleFlg;
    }

    public Integer getApplicationTypeID() {
        return applicationTypeID;
    }

    public void setApplicationTypeID(Integer applicationTypeID) {
        this.applicationTypeID = applicationTypeID;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getActiveQuest() {
        return activeQuest;
    }

    public void setActiveQuest(Integer activeQuest) {
        this.activeQuest = activeQuest;
    }

    public Object getLoginID() {
        return loginID;
    }

    public void setLoginID(Object loginID) {
        this.loginID = loginID;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Object getLoginIDModify() {
        return loginIDModify;
    }

    public void setLoginIDModify(Object loginIDModify) {
        this.loginIDModify = loginIDModify;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getAnsSourceTypeID() {
        return ansSourceTypeID;
    }

    public void setAnsSourceTypeID(Integer ansSourceTypeID) {
        this.ansSourceTypeID = ansSourceTypeID;
    }

    public Integer getAnsSourceNodeType() {
        return ansSourceNodeType;
    }

    public void setAnsSourceNodeType(Integer ansSourceNodeType) {
        this.ansSourceNodeType = ansSourceNodeType;
    }

    public Integer getAnsSourceOptionDep() {
        return ansSourceOptionDep;
    }

    public void setAnsSourceOptionDep(Integer ansSourceOptionDep) {
        this.ansSourceOptionDep = ansSourceOptionDep;
    }

    public Integer getFlgPrvValue() {
        return flgPrvValue;
    }

    public void setFlgPrvValue(Integer flgPrvValue) {
        this.flgPrvValue = flgPrvValue;
    }

    public Integer getFlgPrvVisitDependency() {
        return flgPrvVisitDependency;
    }

    public void setFlgPrvVisitDependency(Integer flgPrvVisitDependency) {
        this.flgPrvVisitDependency = flgPrvVisitDependency;
    }

    public Integer getFlgOptionAreaDependent() {
        return flgOptionAreaDependent;
    }

    public void setFlgOptionAreaDependent(Integer flgOptionAreaDependent) {
        this.flgOptionAreaDependent = flgOptionAreaDependent;
    }

    public Object getOptionAreaSource() {
        return optionAreaSource;
    }

    public void setOptionAreaSource(Object optionAreaSource) {
        this.optionAreaSource = optionAreaSource;
    }

}
