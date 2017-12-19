package com.dubatovka.app.entity;

public class Player extends User {
    
    private PlayerProfile profile;
    private PlayerAccount account;
    private PlayerVerification verification;
    
    public Player() {
    }
    
    public Player(User user) {
        setId(user.getId());
        setEmail(user.getEmail());
        setRole(UserRole.PLAYER);
        setRegistrationDate(user.getRegistrationDate());
    }
    
    public PlayerProfile getProfile() {
        return profile;
    }
    
    public void setProfile(PlayerProfile profile) {
        this.profile = profile;
    }
    
    public PlayerAccount getAccount() {
        return account;
    }
    
    public void setAccount(PlayerAccount account) {
        this.account = account;
    }
    
    public PlayerVerification getVerification() {
        return verification;
    }
    
    public void setVerification(PlayerVerification verification) {
        this.verification = verification;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        if (!super.equals(o)) return false;
        
        Player player = (Player) o;
        
        if (profile != null ? !profile.equals(player.profile) : player.profile != null) return false;
        if (account != null ? !account.equals(player.account) : player.account != null) return false;
        return verification != null ? verification.equals(player.verification) : player.verification == null;
        
    }
    
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (profile != null ? profile.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (verification != null ? verification.hashCode() : 0);
        return result;
    }
    
    @Override
    public String toString() {
        return "Player{" +
                "profile=" + profile +
                ", account=" + account +
                ", verification=" + verification +
                '}';
    }
}