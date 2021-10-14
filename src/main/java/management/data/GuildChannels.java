package management.data;

public class GuildChannels {


    public long getVerificationChannel() {
        return verificationChannel;
    }

    public void setVerificationChannel(long verificationChannel) {
        this.verificationChannel = verificationChannel;
    }

    private long verificationChannel;

    public long getVerificationManagementChannel() {
        return verificationManagementChannel;
    }

    public void setVerificationManagementChannel(long verificationChannel) {
        this.verificationManagementChannel = verificationChannel;
    }

    private long verificationManagementChannel;
}
