
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPersonDetailsForRegistration {

    @SerializedName("PersonNodeId")
    @Expose
    private Integer personNodeId;
    @SerializedName("PersonNodeType")
    @Expose
    private Integer personNodeType;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("ContactNo")
    @Expose
    private String contactNo;
    @SerializedName("DOB")
    @Expose
    private String dOB;
    @SerializedName("Gender")
    @Expose
    private String gender;
    @SerializedName("IsMarried")
    @Expose
    private String isMarried;
    @SerializedName("MarriageDate")
    @Expose
    private String marriageDate;
    @SerializedName("Qualification")
    @Expose
    private String qualification;
    @SerializedName("EmailId")
    @Expose
    private String emailId;
    @SerializedName("BloodGroup")
    @Expose
    private String bloodGroup;
    @SerializedName("PhotoName")
    @Expose
    private String photoName;
    @SerializedName("SelfieName")
    @Expose
    private String selfieName;
    @SerializedName("SignImgName")
    @Expose
    private String signImgName;
    @SerializedName("TimeStampIns")
    @Expose
    private String timeStampIns;
    @SerializedName("TimeStampUpd")
    @Expose
    private String timeStampUpd;

    public Integer getPersonNodeId() {
        return personNodeId;
    }

    public void setPersonNodeId(Integer personNodeId) {
        this.personNodeId = personNodeId;
    }

    public Integer getPersonNodeType() {
        return personNodeType;
    }

    public void setPersonNodeType(Integer personNodeType) {
        this.personNodeType = personNodeType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getDOB() {
        return dOB;
    }

    public void setDOB(String dOB) {
        this.dOB = dOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIsMarried() {
        return isMarried;
    }

    public void setIsMarried(String isMarried) {
        this.isMarried = isMarried;
    }

    public String getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(String marriageDate) {
        this.marriageDate = marriageDate;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getSelfieName() {
        return selfieName;
    }

    public void setSelfieName(String selfieName) {
        this.selfieName = selfieName;
    }

    public String getSignImgName() {
        return signImgName;
    }

    public void setSignImgName(String signImgName) {
        this.signImgName = signImgName;
    }

    public String getTimeStampIns() {
        return timeStampIns;
    }

    public void setTimeStampIns(String timeStampIns) {
        this.timeStampIns = timeStampIns;
    }

    public String getTimeStampUpd() {
        return timeStampUpd;
    }

    public void setTimeStampUpd(String timeStampUpd) {
        this.timeStampUpd = timeStampUpd;
    }

}
