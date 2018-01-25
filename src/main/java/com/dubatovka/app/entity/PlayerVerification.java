package com.dubatovka.app.entity;

import com.dubatovka.app.manager.ConfigConstant;

public class PlayerVerification {
    
    /**
     * Player verification verificationStatus.
     */
    private VerificationStatus verificationStatus;
    
    /**
     * Player country of citizenship
     */
    private String country;
    /**
     * Player passport id number.
     */
    private String passport;
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getPassport() {
        return passport;
    }
    
    public void setPassport(String passport) {
        this.passport = passport;
    }
    
    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }
    
    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerVerification)) return false;
        
        PlayerVerification that = (PlayerVerification) o;
        
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (passport != null ? !passport.equals(that.passport) : that.passport != null) return false;
        return verificationStatus == that.verificationStatus;
        
    }
    
    @Override
    public int hashCode() {
        int result = country != null ? country.hashCode() : 0;
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        result = 31 * result + (verificationStatus != null ? verificationStatus.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "PlayerVerification{" +
                "verificationStatus=" + verificationStatus +
                ", country='" + country + '\'' +
                ", passport='" + passport + '\'' +
                '}';
    }
    
    public enum VerificationStatus {
        UNVERIFIED(ConfigConstant.UNVERIFIED), REQUEST(ConfigConstant.REQUEST), VERIFIED(ConfigConstant.VERIFIED);
        
        private final String status;
        
        VerificationStatus(String status) {
            this.status = status;
        }
        
        public String getStatus() {
            return status;
        }
    }
}